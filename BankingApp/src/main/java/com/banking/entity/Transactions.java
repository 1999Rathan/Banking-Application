package com.banking.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Component
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionId;
	
	private String accountNumber;
	
	private double transactionAmount;
	
	private Timestamp transactionDateTime;
	
	private String transactionType;
	
	private String message;

	public Transactions(String accountNumber, double transactionAmount, Timestamp transactionDateTime,
			String transactionType, String message) {
		super();
		this.accountNumber = accountNumber;
		this.transactionAmount = transactionAmount;
		this.transactionDateTime = transactionDateTime;
		this.transactionType = transactionType;
		this.message = message;
	}
	
	

}
