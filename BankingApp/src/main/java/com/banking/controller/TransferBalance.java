package com.banking.controller;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferBalance {
	
	private String fromAccountNumber;
	private String toAccountNumber;
	
	@NotNull(message="Enter valid amount to be debited")
	private double amount;

}
