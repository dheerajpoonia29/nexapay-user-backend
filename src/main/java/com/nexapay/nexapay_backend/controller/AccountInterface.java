package com.nexapay.nexapay_backend.controller;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AccountInterface {
    ResponseEntity<Response<Object>> apiHealth();

    ResponseEntity<Response<AccountResponse>> openAccount(@RequestBody AccountRequest accountRequest);

    ResponseEntity<Response<AccountResponse>> searchAccountByUserEmail(@RequestParam String email);

    ResponseEntity<Response<AccountResponse>> searchAccountByAccountNo(@RequestParam String accountNo);

    ResponseEntity<Response<AccountResponse>> updateAccount(@RequestBody AccountRequest accountRequest);

    ResponseEntity<Response<AccountResponse>> deleteAccount(@RequestParam("accountNo") String accountNo);
}
