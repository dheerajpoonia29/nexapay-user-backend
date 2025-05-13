package com.nexapay.nexapay_backend.helper;

import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.UserResponse;
import com.nexapay.nexapay_backend.model.AccountEntity;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.springframework.http.HttpStatus;

public class CreateUserResponse {
    public static Response<UserResponse> createResponse(HttpStatus httpStatus, String responseMsg,
                                                           UserEntity userEntity) {
        if(userEntity!=null) {
            if(userEntity.getAccountEntity()!=null) {
                AccountEntity accountEntity = userEntity.getAccountEntity();
                return Response.<UserResponse>builder()
                        .responseStatus(httpStatus)
                        .responseStatusInt(httpStatus.value())
                        .responseMsg(responseMsg)
                        .responseData(
                                UserResponse.builder()
                                        .name(userEntity.getName())
                                        .email(userEntity.getEmail())
                                        .accountData(
                                                AccountResponse.builder()
                                                        .accountNo(accountEntity.getAccountNo())
                                                        .balance(accountEntity.getBalance())
                                                        .userId(accountEntity.getId()).build()).build()).build();
            }
            return Response.<UserResponse>builder()
                    .responseStatus(httpStatus)
                    .responseStatusInt(httpStatus.value())
                    .responseMsg(responseMsg)
                    .responseData(
                            UserResponse.builder()
                                    .name(userEntity.getName())
                                    .email(userEntity.getEmail())
                                    .accountData(null).build()).build();
        }
        return Response.<UserResponse>builder()
                .responseStatus(httpStatus)
                .responseStatusInt(httpStatus.value())
                .responseMsg(responseMsg)
                .responseData(null).build();
    }
}
