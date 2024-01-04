package com.banking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,
			HttpStatus status,WebRequest request){
		
		Map<String,Object> body=new HashMap<String,Object>();
		
		List<Object> errors=ex.getBindingResult().getFieldErrors().stream()
				.map(x->x.getDefaultMessage()).collect(Collectors.toList());
		body.put("errors", errors);
		
		return new ResponseEntity<>(body,headers,status);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> hadleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<String>(ex.getReason(),
                HttpStatus.BAD_REQUEST);
    } 
	
	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<String>("Account Number does not exist, please enter valid account number", HttpStatus.NOT_FOUND);
    } 
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityDataIntegrityViolationException(DataIntegrityViolationException ex) {
		return new ResponseEntity<String>("Try diffrent account number,the account number you have entered is alaready exist ",HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return new ResponseEntity<Object>("please change http request method",HttpStatus.NOT_FOUND);
	}

}
