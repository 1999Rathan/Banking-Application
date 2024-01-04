package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.entity.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
	
	Accounts findByAccountNumber(String accountNumber);
}
