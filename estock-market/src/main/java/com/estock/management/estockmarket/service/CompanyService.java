package com.estock.management.estockmarket.service;

import java.util.List;

import com.estock.management.estockmarket.entity.Company;
import com.estock.management.estockmarket.entity.CompanyDTO;
import com.estock.management.estockmarket.entity.CompanyStock;
import com.estock.management.estockmarket.entity.StockExchange;

public interface CompanyService {

	/**
	 * Find all companies from database
	 * 
	 * @return List<CompanyDTO>
	 */
	List<CompanyDTO> findAllCompanies();

	/**
	 * register new company
	 * @param newCompany
	 * @return registerStatus string
	 */
	String registerNewCompany(Company newCompany);

	/**
	 * find company by company code
	 * @param companyCode
	 * @return Company
	 */
	Company findCompanyByCode(String companyCode);

	/**
	 * delete company by company code
	 * @param companyCode
	 * @return deleted status string
	 */
	String deleteCompanyByCode(String companyCode);

	/**
	 * get company stock details by stock created date range
	 * @param companyCode
	 * @param startdate
	 * @param enddate
	 * @return Company
	 */
	CompanyStock getCompanyStockExchangeByStockDateRange(String companyCode, String startdate, String enddate);

	/**
	 * Add existing company new stock info
	 * @param stockExchanges
	 * @return added status string
	 */
	String addExistingCompanyNewStock(String companyCode,List<StockExchange> stockExchanges);
}
