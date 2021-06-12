package com.estock.management.estockmarket.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estock.management.estockmarket.entity.Company;
import com.estock.management.estockmarket.entity.CompanyDTO;
import com.estock.management.estockmarket.entity.CompanyStock;
import com.estock.management.estockmarket.entity.StockExchange;
import com.estock.management.estockmarket.service.CompanyService;
import com.estock.management.estockmarket.util.CompanyStockValidationUtils;

@RestController
@RequestMapping(value = "/market")
public class CompanyController {
	
	private static final Logger LOG=LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping(value = "/company/getAll")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies(){
		LOG.info("Find all companies company controller entry.");
		return new ResponseEntity<>(companyService.findAllCompanies(),HttpStatus.OK);
	}
	
	@PostMapping(value="/company/register")
	public ResponseEntity<String> registerNewCompany(@RequestBody Company newCompany){
		LOG.info("Register new company controller entry.");
		CompanyStockValidationUtils.companyRegisterationValidation(newCompany);
		String companyRegisterStatus = companyService.registerNewCompany(newCompany);
		LOG.info("Company register status : {}",companyRegisterStatus);
		return new ResponseEntity<>(companyRegisterStatus,HttpStatus.OK);
	}
	
	@GetMapping(value = "/company/info/{companyCode}")
	public ResponseEntity<Company> getCompanyByCode(@PathVariable("companyCode")String companyCode){
		LOG.info("Find company by code controller entry.");
		return new ResponseEntity<>(companyService.findCompanyByCode(companyCode),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/company/delete/{companyCode}")
	public ResponseEntity<String> deleteCompanyByCode(@PathVariable("companyCode")String companyCode){
		LOG.info("Delete company by code controller entry.");
		return new ResponseEntity<>(companyService.deleteCompanyByCode(companyCode),HttpStatus.OK);
	}
	
	@GetMapping(value = "/stock/get/{companyCode}/{startdate}/{enddate}")
	public ResponseEntity<CompanyStock> getCompanyStockExchangeByStockDateRange(@PathVariable("companyCode")String companyCode,@PathVariable("startdate")String startdate,@PathVariable("enddate")String enddate){
		LOG.info("Get company with stock details by code, start and end date controller entry.");
		return new ResponseEntity<>(companyService.getCompanyStockExchangeByStockDateRange(companyCode,startdate,enddate),HttpStatus.OK);
	}
	
	@PostMapping(value="/stock/add/{companyCode}")
	public ResponseEntity<String> addExistingCompanyNewStock(@PathVariable String companyCode,@RequestBody List<StockExchange> stockExchanges){
		LOG.info("Add existing company new stock exchange controller entry.");
		String newStockAddedStatus = companyService.addExistingCompanyNewStock(companyCode,stockExchanges);
		LOG.info("Add existing Company new stock status : {}",newStockAddedStatus);
		return new ResponseEntity<>(newStockAddedStatus,HttpStatus.OK);
	}

}
