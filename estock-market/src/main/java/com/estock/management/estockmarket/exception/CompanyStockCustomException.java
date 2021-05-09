package com.estock.management.estockmarket.exception;

public class CompanyStockCustomException extends RuntimeException {

	private Integer statusCode;
	private String message;
	
	public CompanyStockCustomException(Integer statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	
}
