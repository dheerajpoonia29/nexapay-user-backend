package com.nexapay.nexapay_backend.helper;

public class AccountNumberGenerator {
    public static String generateAccountNumber() {
        long min = 100000000000L;  // Smallest 12-digit number
        long max = 999999999999L;  // Largest 12-digit number

        long randomNum = min + (long) (Math.random() * (max - min + 1));
        return String.valueOf(randomNum);
    }
}
