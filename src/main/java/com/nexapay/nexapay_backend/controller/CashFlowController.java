package com.nexapay.nexapay_backend.controller;

import com.nexapay.dto.request.CashFlowRequest;
import com.nexapay.dto.response.CashFlowResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.nexapay_backend.service.CashFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashflow")
public class CashFlowController implements CashFlowControllerInterface{
    private static final Logger logger = LoggerFactory.getLogger(CashFlowService.class);

    @Autowired
    CashFlowService cashFlowService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<Response<CashFlowResponse>> createCashFlow(@RequestBody  CashFlowRequest request) {
        logger.info("create cash flow");
        Response<CashFlowResponse> response = cashFlowService.getAccountAndSaveCashFlow(request);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Response<List<CashFlowResponse>>> getCashFlows() {
        logger.info("get cash flows");
        Response<List<CashFlowResponse>> response = cashFlowService.getCashFlows();
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @Override
    @GetMapping("/get-by-account-no")
    public ResponseEntity<Response<List<CashFlowResponse>>> getCashFlowsByAccount(@RequestParam String accountNo) {
        logger.info("get cash flows");
        Response<List<CashFlowResponse>> response = cashFlowService.getCashFlowsByAccountNo(accountNo);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
