package com.musdon.thejavaacadmeybank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";

    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created";

    public static final String ACCOUNT_CREATED_SUCCESS = "002";

    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been created";

    public static final String ACCOUNT_NOT_EXIST_CODE = "003";

    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "This user does not have an account created";

    public static final String ACCOUNT_FOUND_CODE = "004";

    public static final String ACCOUNT_FOUND_MESSAGE = "User account found";

    public static final String ACCOUNT_CREDITED_CODE = "005";

    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account has been credited";

    public static final String INSUFFICIENT_BALANCE_CODE = "006";

    public static final String INSUFFICIENT_BALANCE_MESSAGE = "INSUFFICIENT BALANCE";

    public static final String ACCOUNT_DEBITED_CODE = "007";

    public static final String ACCOUNT_DEBITED_MESSAGE = "Amount Debited Successfully";

    public static final String TRANSFER_SUCCESSFUL_CODE = "008";

    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Amount transferred Successfully";







    public static String generateAccountNumber() {

        /**
         * 2023 + randomSixDigits
         */
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //generates a random number between min and max
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        //convert the current and randomNUmber to string, then concatenate

        String year = String.valueOf(currentYear);
        String randomNumber =  String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNumber).toString();

    }

}
