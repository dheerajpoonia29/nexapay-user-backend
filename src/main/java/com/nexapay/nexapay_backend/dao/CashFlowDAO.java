package com.nexapay.nexapay_backend.dao;

import com.nexapay.helper.CashFlowStatus;
import com.nexapay.model.AccountEntity;
import com.nexapay.model.CashFlowEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CashFlowDAO {
    private static final Logger logger = LoggerFactory.getLogger(CashFlowDAO.class);

    @Autowired
    CashFlowRepository cashFlowRepository;

    public void createCashFlow(CashFlowEntity cashFlowEntity) {
        logger.info("cash flow dao saving entity");
        cashFlowEntity.setCashFlowStatus(CashFlowStatus.PENDING);
        cashFlowEntity.setCashFlowStatusMsg("submitted request for "+cashFlowEntity.getCashFlowType());
        cashFlowRepository.save(cashFlowEntity);
    }

    public List<CashFlowEntity> getCashFlows() {
        logger.info("cash flows dao get entity");
        return cashFlowRepository.findAll();
    }

    public List<CashFlowEntity> getCashFlowsByAccountNo(String accountNo) {
        logger.info("cash flows dao get entity");
        return cashFlowRepository.findByAccountNo(accountNo);
    }
}
