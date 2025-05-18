package com.nexapay.nexapay_backend.controller;

import com.nexapay.dto.request.CashFlowRequest;
import com.nexapay.dto.response.CashFlowResponse;
import com.nexapay.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface CashFlowControllerInterface {
    ResponseEntity<Response<CashFlowResponse>> createCashFlow(@RequestBody CashFlowRequest request);
}
