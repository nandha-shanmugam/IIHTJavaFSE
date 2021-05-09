package com.estock.management.estockmarket.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.estock.management.estockmarket.entity.Company;
import com.estock.management.estockmarket.entity.CompanyDTO;
import com.estock.management.estockmarket.entity.CompanyStock;
import com.estock.management.estockmarket.entity.StockExchange;
import com.estock.management.estockmarket.exception.CompanyStockCustomException;
import com.estock.management.estockmarket.repository.CompanyRepo;
import com.estock.management.estockmarket.repository.StockExchangeRepo;
import com.estock.management.estockmarket.service.CompanyService;
import com.estock.management.estockmarket.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	private static final Logger LOG=LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private StockExchangeRepo stockExchangeRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	
	@Override
	public List<CompanyDTO> findAllCompanies() {
		String message;
		try {
			LOG.info("Find all companies.");
			List<Company> resultList =  companyRepo.findAll();
			List<CompanyDTO> companyDTOList = new ArrayList<>();
			
			resultList.stream().forEach(company -> {
				CompanyDTO companyDTO = objectMapper.convertValue(company, CompanyDTO.class);
				companyDTO.setStockExchange(getLatestStockFromListOfStocks(company.getStockExchanges()));
				companyDTOList.add(companyDTO);
			});
			LOG.info("result list size : {}",resultList.size());
			return companyDTOList;
		}
		catch(Exception exception) {
			message = "Exception on fetching all companies from database";
			LOG.error(message,exception);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), message);
		}
	}
	
	private StockExchange getLatestStockFromListOfStocks(List<StockExchange> stockExchanges) {
		if(stockExchanges.isEmpty() || stockExchanges.size()==0) {
			LOG.error("Invalid stockExchanges list to get latest stock.");
			return null;
		}
		return Collections.max(stockExchanges,Comparator.comparing(stock -> stock.getStockCreatedTs()));
	}

	@Override
	public String registerNewCompany(Company newCompany) {
		String message;
		String companyCode = newCompany.getCompanyCode();
		Optional<Company> exisitingCompany = companyRepo.findByCompanyCode(companyCode);
		if(exisitingCompany.isPresent()) {
			message = "Company already existing by company code : "+exisitingCompany;
			LOG.info(message);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), message);
		}
		try {
			addParentReferenceToChild(newCompany);
			companyRepo.save(newCompany);
			message = "Company added successfully for company code : "+companyCode;
			
		} catch(Exception exception) {
			message = "Exception on adding company on company code : "+companyCode;
			LOG.error(message,exception);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), message);
		}
		return message;
	}
	
	private void addParentReferenceToChild(Company company) {
		if(company!=null) {
			setParentRefernceToAllChilds(company.getStockExchanges(),company);
		}
	}
	
	private void setParentRefernceToAllChilds(List<StockExchange> stockExchnages,Company company) {
		stockExchnages.parallelStream().forEach(stockExchange -> {
			stockExchange.setStockCreatedTs(getCurrentTimestamp());
			stockExchange.setCompany(company);
		});
	}
	
	private LocalDateTime getCurrentTimestamp() {
		return LocalDateTime.now();
	}
	
	private LocalDateTime convertStringDateToDateTime(String date) {
		try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
            LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
            return localDate.atStartOfDay();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), "Invalid date input.");
        }
	}

	@Override
	public Company findCompanyByCode(String companyCode) {
		String message;
		Optional<Company> exisitingCompany = companyRepo.findByCompanyCode(companyCode);
		if(!exisitingCompany.isPresent()) {
			message = "Company not existing by company code : "+companyCode;
			LOG.info(message);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_CODE);
		}
		Company company = exisitingCompany.get();
		company.setStockExchanges(Arrays.asList(getLatestStockFromListOfStocks(exisitingCompany.get().getStockExchanges())));
		return company;
	}

	@Override
	public String deleteCompanyByCode(String companyCode) {
		String message;
		Optional<Company> exisitingCompany = companyRepo.findByCompanyCode(companyCode);
		if(!exisitingCompany.isPresent()) {
			message = "Delete can't be done. Company not existing by company code : "+exisitingCompany;
			LOG.info(message);
			throw new CompanyStockCustomException(HttpStatus.NOT_FOUND.value(), message);
		}
		try {
			message = "Company delete successfully.";
			companyRepo.deleteByCompanyCode(companyCode);
		} catch(Exception exception) {
			message = "Exception on deleting company by code : " + companyCode;
			LOG.error(message,exception);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), message);
		}
		return message;
	}

	@Override
	public CompanyStock getCompanyStockExchangeByStockDateRange(String companyCode, String startdate, String enddate) {
		LocalDateTime startDate = convertStringDateToDateTime(startdate);
		LocalDateTime endDate = convertStringDateToDateTime(enddate);
		String message;
		Optional<Company> exisitingCompany = companyRepo.findByCompanyCode(companyCode);
		if(!exisitingCompany.isPresent()) {
			message = "Company not existing by company code : "+companyCode;
			LOG.info(message);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_COMPANY_CODE);
		}
		Company company = exisitingCompany.get();
		List<StockExchange> stockExchangeList = stockExchangeRepo.findByStockCreatedTsBetween(startDate,endDate);
		Optional<StockExchange> minstockExchange = stockExchangeList.stream()
	      .min(Comparator.comparing(StockExchange::getPrice));
		Optional<StockExchange> maxstockExchange = stockExchangeList.stream().max(Comparator.comparing(StockExchange::getPrice));
		double avg = stockExchangeList.stream().mapToDouble(stock -> stock.getPrice().doubleValue()).average().getAsDouble();
		CompanyStock companyStock = objectMapper.convertValue(company, CompanyStock.class);
		companyStock.setAvgStockPrice(avg);
		if(maxstockExchange.isPresent()) {
			companyStock.setMaxStockPrice(maxstockExchange.get().getPrice());
		}
		if(minstockExchange.isPresent()) {
			companyStock.setMinStockPrice(minstockExchange.get().getPrice());
		}
		companyStock.setStockExchangeList(stockExchangeList);
		return companyStock;
	}

	@Override
	public String addExistingCompanyNewStock(String companyCode,List<StockExchange> stockExchanges) {
		String message;
		if(stockExchanges.isEmpty() || stockExchanges.size()==0) {
			message = "Invalid stock information to add existing company : "+ companyCode;
			LOG.info(message);
			return message;
		}
		Optional<Company> exisitingCompany = companyRepo.findByCompanyCode(companyCode);
		if(!exisitingCompany.isPresent()) {
			message = "Company not existing by company code : "+companyCode;
			LOG.info(message);
			throw new CompanyStockCustomException(HttpStatus.NOT_FOUND.value(), message);
		}
		setParentRefernceToAllChilds(stockExchanges,exisitingCompany.get());
		try {
			stockExchangeRepo.saveAll(stockExchanges);
			message = "Successfully added " + stockExchanges.size() + " stocks for the company code : "+companyCode;
		}
		catch(Exception exception) {
			message = "Exception on adding stock for exisitng company code : " + companyCode;
			LOG.error(message,exception);
			throw new CompanyStockCustomException(HttpStatus.BAD_REQUEST.value(), message);
		}
		return message;
	}

}
