package com.nexapay.nexapay_backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BankClient {
    private static final Logger logger = LoggerFactory.getLogger(BankClient.class);

    private final WebClient webClient;

    public BankClient(WebClient.Builder builder, @Value("${bank.api.host}") String userAccountApiHost) {
        logger.info("user api host: {}", userAccountApiHost);
        this.webClient = builder.baseUrl(userAccountApiHost).build();
    }

    public BankResponse getBank(Integer bankId) {
        logger.info("get bank, id: {}", bankId);

        return webClient.get()
                .uri("/bank/get?bankId={bankId}", bankId)
                .exchangeToMono(response -> {
                    HttpStatus status = (HttpStatus) response.statusCode();

                    if (status.is2xxSuccessful()) {
                        logger.info("received 2xx response");
                        return response.bodyToMono(String.class)
                                .doOnNext(body -> logger.info("raw response body: {}", body))
                                .flatMap(body -> {
                                    try {
                                        ObjectMapper mapper = new ObjectMapper();
                                        Response bank = mapper.readValue(body, Response.class);
                                        BankResponse accountResponse = mapper.convertValue(bank.getResponseData(), BankResponse.class);
                                        return Mono.just(accountResponse);
                                    } catch (JsonProcessingException e) {
                                        logger.error("failed to parse response", e);
                                        return Mono.error(new RuntimeException("parsing failed"));
                                    }
                                });
                    } else {
                        logger.error("failed to get account, error: {}", status);
                        return response.bodyToMono(String.class)
                                .doOnNext(errorBody -> logger.error("error body: {}", errorBody))
                                .then(Mono.empty());
                    }
                })
                .block();
    }
}
