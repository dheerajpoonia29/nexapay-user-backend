package com.nexapay.nexapay_backend.service;

import com.nexapay.nexapay_backend.dto.AccountRequest;
import com.nexapay.nexapay_backend.dto.AccountResponse;
import com.nexapay.nexapay_backend.dto.Response;
import com.nexapay.nexapay_backend.dto.UserRequest;

public interface AccountServiceInterface {
    Response<AccountResponse> createAccount(AccountRequest accountRequest);

    Response<AccountResponse> getAccountByUserEmail(String email);

    Response<AccountResponse> getAccountByAccountNo(String accountNo);
}