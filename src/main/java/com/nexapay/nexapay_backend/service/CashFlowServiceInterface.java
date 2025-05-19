package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.CashFlowRequest;
import com.nexapay.dto.response.CashFlowResponse;
import com.nexapay.dto.response.Response;

import java.util.List;

public interface CashFlowServiceInterface {
    Response<CashFlowResponse> getAccountAndSaveCashFlow(CashFlowRequest cashFlowRequest);

    Response<List<CashFlowResponse>> getCashFlows();

    Response<List<CashFlowResponse>> getCashFlowsByAccountNo(String accountNo);
}
