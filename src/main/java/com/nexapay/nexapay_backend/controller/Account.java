package com.nexapay.nexapay_backend.controller;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_backend.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class Account implements AccountInterface {
    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    @Autowired
    AccountService accountService;

    @Override
    @GetMapping("/health")
    public ResponseEntity<Response<Object>> apiHealth() {
        logger.info("account api");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.builder()
                        .responseStatus(HttpStatus.OK)
                        .responseStatusInt(HttpStatus.OK.value())
                        .responseMsg("account api is up!!")
                        .responseData(null).build());
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Response<AccountResponse>> openAccount(@RequestBody AccountRequest accountRequest) {
        logger.info("create account");
        Response<AccountResponse> response = accountService.createAccount(accountRequest);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @GetMapping("/search-by-email")
    public ResponseEntity<Response<AccountResponse>> searchAccountByUserEmail(@RequestParam("email") String email) {
        logger.info("search account by user email");
        Response<AccountResponse> response = accountService.getAccountByUserEmail(email);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @GetMapping("/search-by-account-no")
    public ResponseEntity<Response<AccountResponse>> searchAccountByAccountNo(@RequestParam("accountNo") String accountNo) {
        logger.info("search account by user account no");
        Response<AccountResponse> response = accountService.getAccountByAccountNo(accountNo);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @PostMapping("/update-account")
    public ResponseEntity<Response<AccountResponse>> updateAccount(AccountRequest accountRequest) {
        logger.info("update user account");
        Response<AccountResponse> response = accountService.updateAccount(accountRequest);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
