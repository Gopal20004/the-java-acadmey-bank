package com.musdon.thejavaacadmeybank.service.impl;

import com.musdon.thejavaacadmeybank.dto.TransactionDto;
import com.musdon.thejavaacadmeybank.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
