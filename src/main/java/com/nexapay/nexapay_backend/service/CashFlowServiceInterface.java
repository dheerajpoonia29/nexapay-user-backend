package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.CashFlowRequest;
import com.nexapay.dto.response.CashFlowResponse;
import com.nexapay.dto.response.Response;

public interface CashFlowServiceInterface {
    Response<CashFlowResponse> saveCashDepositWithdrawal(CashFlowRequest cashFlowRequest);

    Response<CashFlowResponse> getCashDepositWithdrawal(CashFlowRequest cashFlowRequest);
}
