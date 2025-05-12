package com.nexapay.nexapay_backend.helper;

import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthentication {
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthentication.class);

    public static boolean verifyPassword(String userPass, String dbPass) {
        logger.info("verifying password");
        return userPass.equals(dbPass);
    }
}
