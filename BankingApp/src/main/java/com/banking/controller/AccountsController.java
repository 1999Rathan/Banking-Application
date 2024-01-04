package com.banking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.AccountStatement;
import com.banking.entity.Accounts;
import com.banking.entity.Transactions;
import com.banking.service.AccountServiceImpl;

@RestController
@RequestMapping("/account")
public class AccountsController {
	
	@Autowired
	private AccountServiceImpl accountService;
	
	@PostMapping("/create")
	public Accounts addAccount(@RequestBody @Valid Accounts account) throws Exception {
		
		return accountService.save(account);
	}
	
	@GetMapping("/findAllAccounts")
	public List<Accounts> getAccounts() {
		
		return accountService.findAll();
	}
	
	@GetMapping("/findByAccountNumber/{accountNumber}")
	public Accounts getAccount(@PathVariable String accountNumber) {
		
		return accountService.findByAccountNumber(accountNumber);
	}
	
	@PutMapping("/updateMapping")
	public Accounts updateAccount(Accounts account) {
		
		return accountService.updateAccount(account);
	}
	
	@PostMapping("/sendMoney")
	public Transactions sendMoney(@RequestBody @Valid TransferBalance transferBalance) {
		
		return accountService.sendMoney(transferBalance);
	}
	
	@GetMapping("/accountStatement/{accountNumber}")
	public AccountStatement getAccountStatement(@PathVariable String accountNumber) {
		
		return accountService.getStatement(accountNumber);
	}
	
	@GetMapping("/sendEmail")
	public String sendEmail() {
		
		return accountService.sendMail();
	}
}
