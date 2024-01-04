package com.banking.entity;

//import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;

@Entity
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {

//	public Accounts() {
//		super();
//	}
	
//	public Accounts(int accountId,
//			@NotBlank(message = "Account Number Required") @Size(min = 10, max = 10, message = "Account number should be 10 digits") String accountNumber,
//			@NotNull String name, String taxId, String accountType, String accountStatus,
//			@Past(message = "Enter valid date") Date dob,
//			@NotNull @Size(min = 10, max = 10, message = "Phone Number should contain 10 digits") @Pattern(regexp = "(0|91)?[6-9][0-9]{9}", message = "Enter valid mobile number") String phoneNo,
//			@NotNull @Email(message = "Enter valid email Id") String email,
//			@NotNull @Min(value = 1, message = "Enter valid Account Balance") int currentBalance,
//			@NotNull @Min(value = 1000, message = "Enter amount Greater than 1000") int monAvrBal) {
//		super();
//		this.accountId = accountId;
//		this.accountNumber = accountNumber;
//		this.name = name;
//		this.taxId = taxId;
//		this.accountType = accountType;
//		this.accountStatus = accountStatus;
//		this.dob = dob;
//		this.phoneNo = phoneNo;
//		this.email = email;
//		this.currentBalance = currentBalance;
//		this.monAvrBal = monAvrBal;
//	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int accountId;
	
	@Column(unique=true)
	@NotBlank(message="Account Number Required")
	@Size(min=10, max=10, message="Account number should be 10 digits")
	private String accountNumber;
	
	@NotNull
	private String name;
	
	@Column(unique=true)
	private String panId;
	
	private String accountType;
	
	private String accountStatus;
	
	//@DateTimeFormat(pattern = "MM/dd/yyyy")
	@JsonFormat(pattern="MM/dd/yyyy")
	@Past(message="Enter Valid Date")
	private Date dob;
	
	@NotNull
	@Size(min=10,max=10, message="Phone Number should contain 10 digits")
	@Pattern(regexp="(0|91)?[6-9][0-9]{9}",message="Enter valid mobile number")
	private String phoneNo;
	
	
	@NotNull
	@Email(message="Enter valid email Id")
	private String email;
	
	@NotNull
	@Min(value=1,message="Enter valid Account Balance")
	private double currentBalance;
	
	@NotNull
	@Min(value=1000,message = "Enter amount Greater than 1000")
	private double monAvrBal;
	
}
