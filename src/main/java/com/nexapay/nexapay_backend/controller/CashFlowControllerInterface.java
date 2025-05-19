package com.nexapay.nexapay_backend.controller;

import com.nexapay.dto.request.CashFlowRequest;
import com.nexapay.dto.response.CashFlowResponse;
import com.nexapay.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CashFlowControllerInterface {
    ResponseEntity<Response<CashFlowResponse>> createCashFlow(@RequestBody CashFlowRequest request);

    ResponseEntity<Response<List<CashFlowResponse>>> getCashFlows();

    ResponseEntity<Response<List<CashFlowResponse>>> getCashFlowsByAccount(@RequestParam String accountNo);
}
