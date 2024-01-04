package com.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

	List<Transactions> findByAccountNumber(String accountNumber);
}
