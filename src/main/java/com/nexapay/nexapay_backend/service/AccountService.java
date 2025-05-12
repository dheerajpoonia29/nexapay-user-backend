package com.nexapay.nexapay_backend.service;

import com.nexapay.nexapay_backend.dao.AccountDAO;
import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.*;
import com.nexapay.nexapay_backend.helper.AccountServiceHelper;
import com.nexapay.nexapay_backend.model.AccountEntity;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.nexapay.nexapay_backend.helper.AccountNumberGenerator.generateAccountNumber;

@Service
public class AccountService implements AccountServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    UserDAO userDAO;

    UserEntity userEntity;

    @Autowired
    AccountServiceHelper accountServiceHelper;

    @Override
    public Response<AccountResponse> createAccount(AccountRequest accountRequest) {
        logger.info("check user exist");
        UserRequest userRequest = accountRequest.getUserRequest();
        UserEntity userEntity = userDAO.readUserByEmail(userRequest.getEmail());
        if (userEntity==null) {
            return Response.<AccountResponse>builder()
                    .responseStatus(HttpStatus.UNAUTHORIZED)
                    .responseStatusInt(HttpStatus.UNAUTHORIZED.value())
                    .responseMsg("user not exist")
                    .responseData(null).build();
        }

        logger.info("check account exist already");
        if (userEntity.getAccountEntity()!=null) {
            return Response.<AccountResponse>builder()
                    .responseStatus(HttpStatus.CONFLICT)
                    .responseStatusInt(HttpStatus.CONFLICT.value())
                    .responseMsg("account already exist")
                    .responseData(null).build();
        }

        logger.info("create account");
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNo(generateAccountNumber())
                .balance(0L)
                .userEntity(userEntity).build();

        logger.info("persist account");
        boolean status  = accountDAO.saveAccount(accountEntity);

        logger.info("return response");
        if(status) {
            return Response.<AccountResponse>builder()
                    .responseStatus(HttpStatus.CREATED)
                    .responseStatusInt(HttpStatus.CREATED.value())
                    .responseMsg("created")
                    .responseData(accountEntity.toResponse()).build();
        }
        return Response.<AccountResponse>builder()
                .responseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .responseStatusInt(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseMsg("something went wrong")
                .responseData(null).build();
    }

    @Override
    public Response<AccountResponse> getAccountByUserEmail(String email) {
        UserEntity userEntity = userDAO.readUserByEmail(email);
        if(userEntity==null || userEntity.getAccountEntity()==null) {
            return Response.<AccountResponse>builder()
                    .responseStatus(HttpStatus.NOT_FOUND)
                    .responseStatusInt(HttpStatus.NOT_FOUND.value())
                    .responseMsg(userEntity==null?"user not exist":"account not present")
                    .responseData(null).build();
        }
        AccountEntity accountEntity = userEntity.getAccountEntity();
        return Response.<AccountResponse>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("account found")
                .responseData(accountEntity.toResponse()).build();
    }

    @Override
    public Response<AccountResponse> getAccountByAccountNo(String accountNo) {
        AccountEntity accountEntity = accountDAO.readByAccountNo(accountNo);
        if(accountEntity==null) {
            return Response.<AccountResponse>builder()
                    .responseStatus(HttpStatus.NOT_FOUND)
                    .responseStatusInt(HttpStatus.NOT_FOUND.value())
                    .responseMsg("account not present")
                    .responseData(null).build();
        }
        return Response.<AccountResponse>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("account found")
                .responseData(accountEntity.toResponse()).build();
    }
}
