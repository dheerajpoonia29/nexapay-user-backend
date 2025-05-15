package com.nexapay.nexapay_backend.helper;

import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.model.AccountEntity;
import org.springframework.http.HttpStatus;

public class CreateAccountResponse {
    public static Response<AccountResponse> createResponse(HttpStatus httpStatus, String responseMsg,
                                                           AccountEntity accountEntity) {
        if(accountEntity!=null) {
            return Response.<AccountResponse>builder()
                    .responseStatus(httpStatus)
                    .responseStatusInt(httpStatus.value())
                    .responseMsg(responseMsg)
                    .responseData(
                            AccountResponse.builder()
                                    .accountNo(accountEntity.getAccountNo())
                                    .balance(accountEntity.getBalance())
                                    .userId(accountEntity.getId())
                                    .build()).build();
        }
        return Response.<AccountResponse>builder()
                .responseStatus(httpStatus)
                .responseStatusInt(httpStatus.value())
                .responseMsg(responseMsg)
                .responseData(null).build();
    }
}
