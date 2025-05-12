package com.nexapay.nexapay_backend.helper;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.AccountRequest;
import com.nexapay.nexapay_backend.dto.AccountResponse;
import com.nexapay.nexapay_backend.dto.Response;
import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.model.UserEntity;
import com.nexapay.nexapay_backend.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceHelper {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceHelper.class);
}
