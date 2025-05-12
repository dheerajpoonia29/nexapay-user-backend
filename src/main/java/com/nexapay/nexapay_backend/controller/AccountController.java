package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dto.AccountRequest;
import com.nexapay.nexapay_backend.dto.AccountResponse;
import com.nexapay.nexapay_backend.dto.Response;
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
public class AccountController implements AccountControllerInterface {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @GetMapping("/health")
    ResponseEntity<Response> loginHealth() {
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
        Response<AccountResponse> response = accountService.getAccountByUserEmail(email);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @GetMapping("/search-by-account-no")
    public ResponseEntity<Response<AccountResponse>> searchAccountByAccountNo(@RequestParam("accountNo") String accountNo) {
        Response<AccountResponse> response = accountService.getAccountByAccountNo(accountNo);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
