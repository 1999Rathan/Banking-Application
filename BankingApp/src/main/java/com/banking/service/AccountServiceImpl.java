package com.banking.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.banking.controller.TransferBalance;
import com.banking.entity.AccountStatement;
import com.banking.entity.Accounts;
import com.banking.entity.Transactions;
import com.banking.repository.AccountsRepository;
import com.banking.repository.TransactionsRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountsRepository accountRepository;
	
	@Autowired
	private TransactionsRepository transactionRepository;
	
	@Autowired
	private AgeValidator ageValidator;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public Accounts save(Accounts account) throws Exception {
	
		ageValidator.AgeValidation(account.getDob());
		
		return accountRepository.save(account);
	}

	@Override
	public List<Accounts> findAll() {
		
		return accountRepository.findAll();
	}

	@Override
	public Accounts findByAccountNumber(String accountNumber) {
		
		List<Accounts> allAccounts = new ArrayList<Accounts>();
		allAccounts=accountRepository.findAll();
		
		List<String> accountNumbers=new ArrayList<String>();
		for(Accounts account:allAccounts) {
		accountNumbers.add(account.getAccountNumber());
		}
		
		if(accountNumbers.contains(accountNumber)) {
			return accountRepository.findByAccountNumber(accountNumber);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Accounts updateAccount(Accounts account) {
		
		Accounts updateAccount= accountRepository.findById(account.getAccountId()).orElse(null);
		
		updateAccount.setName(account.getName());
		
		return accountRepository.save(updateAccount);
	}

	@Override
	public Transactions sendMoney(TransferBalance transferBalance) {
		
		String fromAccountNumber=transferBalance.getFromAccountNumber();
		String toAccountNumber=transferBalance.getToAccountNumber();
		double amount=transferBalance.getAmount();
		
		if(amount>0) {
			Accounts fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
			Accounts toAccount = accountRepository.findByAccountNumber(toAccountNumber);
			
			if(fromAccount.getCurrentBalance()>amount) {
				fromAccount.setCurrentBalance(fromAccount.getCurrentBalance()-amount);
				accountRepository.save(fromAccount);
				
				toAccount.setCurrentBalance(toAccount.getCurrentBalance()+amount);
				accountRepository.save(toAccount);
				
				Transactions transactionDebited = transactionRepository.save(new Transactions(fromAccountNumber,amount,new Timestamp(System.currentTimeMillis()),"Debited","successful"));
				Transactions transactionCredited = transactionRepository.save(new Transactions(toAccountNumber, amount, new Timestamp(System.currentTimeMillis()), "credited", "successful"));
				return transactionDebited;
			} else {
				
				Transactions failedTransaction=new Transactions();
				failedTransaction.setAccountNumber(transferBalance.getFromAccountNumber());
				failedTransaction.setTransactionAmount(transferBalance.getAmount());
				failedTransaction.setTransactionDateTime(new Timestamp(System.currentTimeMillis()));
				failedTransaction.setTransactionType("failed Transaction");
				failedTransaction.setMessage("Insufficient Balance");
				
				return failedTransaction;
			}
		}
		
		else {
			Transactions failedTransaction = new Transactions();
			failedTransaction.setAccountNumber(transferBalance.getFromAccountNumber());
			failedTransaction.setTransactionAmount(transferBalance.getAmount());
			failedTransaction.setTransactionDateTime(new Timestamp(System.currentTimeMillis()));
			failedTransaction.setTransactionType("Failed Transaction");
			failedTransaction.setMessage("Enter valid Amount");
			
			return failedTransaction;
		}
			
	}

	@Override
	public AccountStatement getStatement(String accountNumber) {

		List<Accounts> allAccounts=new ArrayList<>();
		allAccounts=accountRepository.findAll();
		
		List<String> accountNumbers=new ArrayList<>();
		for(Accounts account:allAccounts) {
			accountNumbers.add(account.getAccountNumber());
		}
		
		if(accountNumbers.contains(accountNumber)) {
			Accounts account=accountRepository.findByAccountNumber(accountNumber);
			
			return new AccountStatement(account.getCurrentBalance(), transactionRepository.findByAccountNumber(accountNumber));
		} else {
			throw new NoSuchElementException();
		}
		
	}

	@Override
	public List<Accounts> getAccountsForIm() {
		
		List<Accounts> allAccounts=accountRepository.findAll();
		List<Accounts> validAccForIm=new ArrayList<>();
		
		for(Accounts account:allAccounts) {
			if((account.getPhoneNo()!=null)&&(account.getMonAvrBal()>1000)&&(account.getCurrentBalance()>0)&&(account.getAccountStatus().equalsIgnoreCase("active"))) {
				validAccForIm.add(account);
			}	
		}
		
		return validAccForIm;
	}

	@Override
	public String getMab(String accountNumber) {
		
		List<Accounts> allAccounts=accountRepository.findAll();
		List<String> accountNumbers=new ArrayList<>();
		
		for(Accounts account:allAccounts) {
			accountNumbers.add(account.getAccountNumber());
		}
		
		if(accountNumbers.contains(accountNumber)) {
			
			Accounts account=accountRepository.findByAccountNumber(accountNumber);
			return "Your monthly average balance is "+account.getMonAvrBal();
			
		} else {
			throw new NoSuchElementException();
		}
		
	}

	@Override
	public String sendMail() {
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setFrom("eBank@gmail.com");
		
		List<Accounts> validAccounts=getAccountsForIm();
		List<String> emails=new ArrayList<>();
		
		for(Accounts account:validAccounts) {
			emails.add(account.getEmail());
		}
		
		String[] emailArray=emails.toArray(new String[0]);
		mailMessage.setTo(emailArray);
		
		mailMessage.setSubject("This mail is regarding the promotional offers by eBank bank");
		mailMessage.setSubject("This mail is regarding the promotional offers by ADFC bank");
		mailMessage.setText("Dear sir,\nGreetings from ADFC bank,\n\n As an esteemed adfc bank customer we want to bring to your notice that we have an ongoing NFO which is "
				+ "ADFC flexicap fund managed by Mr david.\nIf interested write back to us at adfcbank@gmail.com.\n\nRegards,\nADFC Bank.");
		javaMailSender.send(mailMessage);
		
		return "Mail sent successfully to "+emails;
	}

}
