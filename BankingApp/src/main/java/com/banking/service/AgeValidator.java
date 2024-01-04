package com.banking.service;


import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AgeValidator {
	
	@SuppressWarnings("deprecation")
	public void AgeValidation(Date date) {
		
		@SuppressWarnings("deprecation")
        LocalDate birthDate = LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
		
		LocalDate today = LocalDate.now();
		
		int year = date.getYear()+1900;
		int month = date.getMonth()+1;
		int day = date.getDate();

		int years = Period.between(birthDate, today).getYears();
		int days = Period.between(birthDate, today).getDays();
		int months = Period.between(birthDate, today).getMonths();

		if (years < 18 || years > 60) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Age should be in between 18 and 60. Current age is " + years + " years " + months + " months & " + days + " days old ");
		}
	}

}
