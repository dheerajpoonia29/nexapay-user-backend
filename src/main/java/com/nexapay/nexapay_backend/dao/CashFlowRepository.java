package com.nexapay.nexapay_backend.dao;

import com.nexapay.model.AccountEntity;
import com.nexapay.model.CashFlowEntity;
import com.nexapay.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlowEntity, Integer> {
    List<CashFlowEntity> findByAccountNo(String accountNo);
}
