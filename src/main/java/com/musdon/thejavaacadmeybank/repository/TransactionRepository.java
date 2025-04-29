package com.musdon.thejavaacadmeybank.repository;

import com.musdon.thejavaacadmeybank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
