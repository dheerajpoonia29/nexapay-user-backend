package com.nexapay.nexapay_backend.dao;

import com.nexapay.model.AccountEntity;
import com.nexapay.model.UserEntity;
import jakarta.transaction.Transactional;
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

    @Transactional
    public boolean deleteAccount(AccountEntity accountEntity) {
        logger.info("detach reference");
        UserEntity user = accountEntity.getUserEntity();
        if (user != null) {
            user.setAccountEntity(null);
        }

        logger.info("make user null");
        accountEntity.setUserEntity(null);

        logger.info("dao deleting");
        accountRepository.delete(accountEntity);
        return true;
    }
}
