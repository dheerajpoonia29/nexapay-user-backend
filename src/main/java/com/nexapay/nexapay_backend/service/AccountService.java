package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.request.BankRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.request.UserRequest;
import com.nexapay.dto.response.BankResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.helper.BankBranch;
import com.nexapay.model.BankEntity;
import com.nexapay.nexapay_backend.client.BankClient;
import com.nexapay.nexapay_backend.dao.AccountDAO;
import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.model.AccountEntity;
import com.nexapay.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nexapay.nexapay_backend.helper.AccountNumberGenerator.generateAccountNumber;
import static com.nexapay.nexapay_backend.helper.CreateAccountResponse.createResponse;

@Service
public class AccountService implements AccountServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    BankClient bankClient;

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
            return createResponse(HttpStatus.CONFLICT, "account already exist", userEntity.getAccountEntity());
        }

        logger.info("check bank exist");
        BankRequest bankRequest = accountRequest.getBankRequest();
        BankResponse bankResponse = bankClient.getBank(bankRequest.getId()!=null?bankRequest.getId():1); // todo not make 1 default
        if (bankResponse==null) {
            return createResponse(HttpStatus.NOT_FOUND, "bank not found", null);
        }


        logger.info("check branch exist");
        String ifscCode = accountRequest.getIfscCode();
        List<BankBranch> bankBranchList = bankResponse.getBranches();
        BankBranch bankBranch = null;
        for (BankBranch obj: bankBranchList) {
            if(obj.getIfscCode().equals(ifscCode)) {
                bankBranch = obj;
                break;
            }
        }

        if (bankBranch==null) {
            logger.info("branch not found");
            return createResponse(HttpStatus.NOT_FOUND, "branch not found", null);
        }

        logger.info("create account");
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNo(generateAccountNumber())
                .balance(0L)
                .ifscCode(ifscCode)
                .bank(BankEntity.builder()   // todo this is vulnerable, just sending for reference only
                        .id(bankResponse.getId())
                        .name(bankResponse.getName())
                        .branches(bankResponse.getBranches())
                        .build())
                .user(userEntity).build();

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

    @Override
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

    @Override
    public Response<AccountResponse> findAndDeleteAccount(String accountNo) {
        logger.info("find account");
        AccountEntity accountEntity = accountDAO.readByAccountNo(accountNo);
        if(accountEntity==null) {
            return createResponse(HttpStatus.NOT_FOUND, "account not present", null);
        }

        logger.info("delete account");
        boolean status = accountDAO.deleteAccount(accountEntity);

        logger.info("return response");
        if(status) return createResponse(HttpStatus.CREATED, "account deleted successfully", null);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error", null);
    }
}
