package com.banking.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatement {
	
	private double currentBalance;
	List<Transactions> transactionHistory;

}
