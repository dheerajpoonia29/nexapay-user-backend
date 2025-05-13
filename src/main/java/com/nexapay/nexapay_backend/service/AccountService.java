package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.request.UserRequest;
import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_backend.dao.AccountDAO;
import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.helper.AccountServiceHelper;
import com.nexapay.nexapay_backend.model.AccountEntity;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.nexapay.nexapay_backend.helper.AccountNumberGenerator.generateAccountNumber;
import static com.nexapay.nexapay_backend.helper.CreateAccountResponse.createResponse;

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
            return createResponse(HttpStatus.UNAUTHORIZED, "user not exist", null);
        }

        logger.info("check account exist already");
        if (userEntity.getAccountEntity()!=null) {
            return createResponse(HttpStatus.CONFLICT, "account already exist", null);
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
            return createResponse(HttpStatus.CREATED, "account created", accountEntity);
        }
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error", null);
    }

    @Override
    public Response<AccountResponse> getAccountByUserEmail(String email) {
        UserEntity userEntity = userDAO.readUserByEmail(email);
        if(userEntity==null || userEntity.getAccountEntity()==null) {
            return createResponse(HttpStatus.NOT_FOUND, "account not present", null);
        }
        AccountEntity accountEntity = userEntity.getAccountEntity();
        return createResponse(HttpStatus.OK, "account found", accountEntity);
    }

    @Override
    public Response<AccountResponse> getAccountByAccountNo(String accountNo) {
        AccountEntity accountEntity = accountDAO.readByAccountNo(accountNo);
        if(accountEntity==null) {
            return createResponse(HttpStatus.NOT_FOUND, "account not present", null);
        }
        return createResponse(HttpStatus.OK, "account found", accountEntity);
    }

    public Response<AccountResponse> updateAccount(AccountRequest accountRequest) {
        logger.info("read account");
        AccountEntity accountEntity = accountDAO.readByAccountNo(accountRequest.getAccountNo());

        logger.info("validate account");
        if(accountEntity==null) {
            logger.error("account not found");
            return createResponse(HttpStatus.NOT_FOUND, "account not present", null);
        }

        logger.info("update balance");
        accountEntity.setBalance(accountRequest.getBalance());

        logger.info("persist account");
        boolean status = accountDAO.saveAccount(accountEntity);

        logger.info("return response");
        if(status) return createResponse(HttpStatus.OK, "account updated", accountEntity);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error", null);
    }
}
