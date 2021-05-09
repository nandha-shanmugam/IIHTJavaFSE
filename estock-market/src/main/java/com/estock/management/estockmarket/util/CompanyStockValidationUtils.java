package com.estock.management.estockmarket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.estock.management.estockmarket.entity.Company;
import com.estock.management.estockmarket.exception.CompanyStockCustomException;

public class CompanyStockValidationUtils {

	private static final Logger LOG=LoggerFactory.getLogger(CompanyStockValidationUtils.class);
	
	public static void companyRegisterationValidation(Company company) {
		if(company==null) {
			LOG.error(Constants.INVALID_COMPANY_INPUT);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_INPUT);
		} else {
			String companyName = company.getCompanyName();
			String companyCeo = company.getCompanyCeo();
			String companyWebsite = company.getCompanyWebsite();
			String companyCode = company.getCompanyCode();
			Float companyTurnOver = company.getCompanyTurnover();
			if(StringUtils.isEmpty(companyName) || companyName.length()<=2) {
				LOG.error(Constants.INVALID_COMPANY_NAME);
				throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_NAME);
			}  if(StringUtils.isEmpty(companyCeo) || companyCeo.length()<=2) {
				LOG.error(Constants.INVALID_COMPANY_CEO_NAME);
				throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_CEO_NAME);
			}  if(StringUtils.isEmpty(companyWebsite) || !companyWebsite.contains("http")) {
				LOG.error(Constants.INVALID_COMPANY_WEBSITE);
				throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_WEBSITE);
			}  if(StringUtils.isEmpty(companyCode) || companyCode.length()<=2) {
				LOG.error(Constants.INVALID_COMPANY_WEBSITE);
				throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_WEBSITE);
			}  if(StringUtils.isEmpty(companyTurnOver) || companyTurnOver<10.00) {
				LOG.error(Constants.INVALID_COMPANY_TURNOVER);
				throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_TURNOVER);
			}
		}
	}
}
