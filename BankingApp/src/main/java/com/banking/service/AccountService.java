package com.banking.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.banking.controller.TransferBalance;
import com.banking.entity.AccountStatement;
import com.banking.entity.Accounts;
import com.banking.entity.Transactions;

@Component
public interface AccountService {

	public Accounts save(Accounts account) throws Exception;
	
	public List<Accounts> findAll();
	
	public Accounts findByAccountNumber(String accountNumber);
	
	public Accounts updateAccount(Accounts account);
	
	public Transactions sendMoney(TransferBalance transferBalance);
	
	public AccountStatement getStatement(String accountNumber);
	
	public List<Accounts> getAccountsForIm();
	
	public String getMab(String accountNumber);
	
	public String sendMail();
	
}
