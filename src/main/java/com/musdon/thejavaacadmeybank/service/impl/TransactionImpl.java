package com.musdon.thejavaacadmeybank.service.impl;

import com.musdon.thejavaacadmeybank.dto.TransactionDto;
import com.musdon.thejavaacadmeybank.entity.Transaction;
import com.musdon.thejavaacadmeybank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
@Component
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("Success")
                .build();
        transactionRepository.save(transaction);
        System.out.println("Transaction saved Successfully");


    }
}
