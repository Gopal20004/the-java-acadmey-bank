package com.musdon.thejavaacadmeybank.service.impl;

import com.musdon.thejavaacadmeybank.dto.*;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse creditAccount(CreditDebitRequest request );

    BankResponse debitAccount(CreditDebitRequest request);

    BankResponse transfer(TransferRequest request);
}
