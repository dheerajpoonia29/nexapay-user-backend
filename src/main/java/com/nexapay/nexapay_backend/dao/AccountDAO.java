package com.nexapay.nexapay_backend.dao;

import com.nexapay.nexapay_backend.model.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);
    @Autowired
    AccountRepository accountRepository;

    public boolean saveAccount(AccountEntity accountEntity) {
        logger.info("saving account");
        accountRepository.save(accountEntity);
        return true;
    }

    public AccountEntity readByAccountNo(String accountNo) {
        logger.info("read by account no");
        return accountRepository.findByAccountNo(accountNo).orElse(null);
    }
}
