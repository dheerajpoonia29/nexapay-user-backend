package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dto.AccountRequest;
import com.nexapay.nexapay_backend.dto.AccountResponse;
import com.nexapay.nexapay_backend.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AccountControllerInterface {
    ResponseEntity<Response<AccountResponse>> openAccount(@RequestBody AccountRequest accountRequest);

    ResponseEntity<Response<AccountResponse>> updateAccount(@RequestBody AccountRequest accountRequest);

    ResponseEntity<Response<AccountResponse>> searchAccountByUserEmail(@RequestParam String email);

    ResponseEntity<Response<AccountResponse>> searchAccountByAccountNo(@RequestParam String accountNo);
}
