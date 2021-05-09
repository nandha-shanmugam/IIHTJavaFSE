package com.estock.management.estockmarket.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(Include. NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyStock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1761838557454544138L;

	private String companyCode;
	
	private String companyName;
	
	private Float maxStockPrice;
	
	private Float minStockPrice;
	
	private double avgStockPrice;
	
	@JsonIgnoreProperties("company")
	private List<StockExchange> stockExchangeList;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Float getMaxStockPrice() {
		return maxStockPrice;
	}

	public void setMaxStockPrice(Float maxStockPrice) {
		this.maxStockPrice = maxStockPrice;
	}

	public Float getMinStockPrice() {
		return minStockPrice;
	}

	public void setMinStockPrice(Float minStockPrice) {
		this.minStockPrice = minStockPrice;
	}

	public double getAvgStockPrice() {
		return avgStockPrice;
	}

	public void setAvgStockPrice(double avgStockPrice) {
		this.avgStockPrice = avgStockPrice;
	}

	public List<StockExchange> getStockExchangeList() {
		return stockExchangeList;
	}

	public void setStockExchangeList(List<StockExchange> stockExchangeList) {
		this.stockExchangeList = stockExchangeList;
	}
	
}
