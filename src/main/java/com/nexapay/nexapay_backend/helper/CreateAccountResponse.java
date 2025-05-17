package com.nexapay.nexapay_backend.helper;

import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.model.AccountEntity;
import com.nexapay.model.BankEntity;
import org.springframework.http.HttpStatus;

public class CreateAccountResponse {
    public static Response<AccountResponse> createResponse(HttpStatus httpStatus, String responseMsg,
                                                           AccountEntity accountEntity) {
        if(accountEntity!=null) {
            if(accountEntity.getBank()!=null) {
                BankEntity bankEntity = accountEntity.getBank();
                return Response.<AccountResponse>builder()
                        .responseStatus(httpStatus)
                        .responseStatusInt(httpStatus.value())
                        .responseMsg(responseMsg)
                        .responseData(
                                AccountResponse.builder()
                                        .accountNo(accountEntity.getAccountNo())
                                        .balance(accountEntity.getBalance())
                                        .ifscCode(accountEntity.getIfscCode())
                                        .bankData(
                                                BankResponse.builder()
                                                        .id(bankEntity.getId())
                                                        .name(bankEntity.getName())
                                                        .branches(bankEntity.getBranches())
                                                        .branches(bankEntity.getBranches()).build()
                                        )
                                        .build()).build();
            }
            return Response.<AccountResponse>builder()
                    .responseStatus(httpStatus)
                    .responseStatusInt(httpStatus.value())
                    .responseMsg(responseMsg)
                    .responseData(
                            AccountResponse.builder()
                                    .accountNo(accountEntity.getAccountNo())
                                    .balance(accountEntity.getBalance())
                                    .build()).build();
        }
        return Response.<AccountResponse>builder()
                .responseStatus(httpStatus)
                .responseStatusInt(httpStatus.value())
                .responseMsg(responseMsg)
                .responseData(null).build();
    }
}
