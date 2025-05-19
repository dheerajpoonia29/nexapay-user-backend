package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.CashFlowRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.dto.response.CashFlowResponse;
import com.nexapay.dto.response.Response;
import com.nexapay.helper.CashFlowStatus;
import com.nexapay.helper.HelperUtil;
import com.nexapay.model.AccountEntity;
import com.nexapay.model.CashFlowEntity;
import com.nexapay.nexapay_backend.client.BankClient;
import com.nexapay.nexapay_backend.dao.AccountDAO;
import com.nexapay.nexapay_backend.dao.CashFlowDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CashFlowService implements CashFlowServiceInterface{
    private static final Logger logger = LoggerFactory.getLogger(CashFlowService.class);

    @Autowired
    CashFlowDAO cashFlowDAO;

    @Autowired
    AccountDAO accountDAO;

    @Override
    public Response<CashFlowResponse> getAccountAndSaveCashFlow(CashFlowRequest cashFlowRequest) {
        logger.info("get user account, accountNo: {}", cashFlowRequest.getAccountNo());
        AccountEntity accountEntity = accountDAO.readByAccountNo(cashFlowRequest.getAccountNo());
        if (accountEntity==null) {
            logger.error("account not found");
            return Response.<CashFlowResponse>builder()
                    .responseStatus(HttpStatus.NOT_FOUND)
                    .responseStatusInt(HttpStatus.NOT_FOUND.value())
                    .responseMsg("account not found")
                    .responseData(null).build();
        }

        logger.info("create cash flow entity");
        CashFlowEntity cashFlowEntity = CashFlowEntity.builder()
                .transactionId(HelperUtil.generateTransactionId())
                .amount(cashFlowRequest.getAmount())
                .cashFlowType(cashFlowRequest.getCashFlowType())
                .cashFlowStatus(CashFlowStatus.PENDING)
                .cashFlowStatusMsg("request to "+cashFlowRequest.getCashFlowType()+" from account "+cashFlowRequest.getAccountNo())
                .userEmail(cashFlowRequest.getUserEmail())
                .accountNo(cashFlowRequest.getAccountNo())
                .account(accountEntity)
                .bank(accountEntity.getBank()).build();

        logger.info("saving cash flow entity");
        cashFlowDAO.createCashFlow(cashFlowEntity);

        logger.info("return response");
        return Response.<CashFlowResponse>builder()
                .responseStatus(HttpStatus.CREATED)
                .responseStatusInt(HttpStatus.CREATED.value())
                .responseMsg("request to "+cashFlowRequest.getCashFlowType()+" from account "+cashFlowRequest.getAccountNo())
                // todo send cash flow entity if needed
                .responseData(null).build();
    }


    public Response<List<CashFlowResponse>> getCashFlows() {
        logger.info("get cash flows entity and transform into cash flow response");
        List<CashFlowEntity> cashFlowEntityList = cashFlowDAO.getCashFlows();

        logger.info("convert cashFlowEntityList to cashFlowEntityResponse");
        List<CashFlowResponse> cashFlowResponseList = new ArrayList<>();
        for(CashFlowEntity cashFlowEntity: cashFlowEntityList) {
            cashFlowResponseList.add(cashFlowEntity.toResponse());
        }

        logger.info("return response");
        return Response.<List<CashFlowResponse>>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("cash flows found")
                .responseData(cashFlowResponseList).build();
    }

    public Response<List<CashFlowResponse>> getCashFlowsByAccountNo(String accountNo) {
        logger.info("get cash flows by account no: {}", accountNo);
        List<CashFlowEntity> cashFlowEntityList = cashFlowDAO.getCashFlows();

        logger.info("convert cashFlowEntityList to cashFlowEntityResponse");
        List<CashFlowResponse> cashFlowResponseList = new ArrayList<>();
        for(CashFlowEntity cashFlowEntity: cashFlowEntityList) {
            cashFlowResponseList.add(cashFlowEntity.toResponse());
        }

        logger.info("return response");
        return Response.<List<CashFlowResponse>>builder()
                .responseStatus(HttpStatus.OK)
                .responseStatusInt(HttpStatus.OK.value())
                .responseMsg("cash flows found")
                .responseData(cashFlowResponseList).build();
    }
}
