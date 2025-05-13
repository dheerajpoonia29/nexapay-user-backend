package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.AccountRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.Response;

public interface AccountServiceInterface {
    Response<AccountResponse> createAccount(AccountRequest accountRequest);

    Response<AccountResponse> getAccountByUserEmail(String email);

    Response<AccountResponse> getAccountByAccountNo(String accountNo);

    Response<AccountResponse> updateAccount(AccountRequest accountRequest);

    Response<AccountResponse> findAndDeleteAccount(String accountNo);
}