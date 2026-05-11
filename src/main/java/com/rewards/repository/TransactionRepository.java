package com.rewards.repository;

import com.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Transaction repository.
 */
@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
}