package com.estock.management.estockmarket.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="stock_exchange")
public class StockExchange {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name= "price")
	private Float price;

	@Column(name = "stock_created_ts")
	private LocalDateTime stockCreatedTs;
	
	@JoinColumn(name = "company_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("stockExchanges")
	private Company company;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public LocalDateTime getStockCreatedTs() {
		return stockCreatedTs;
	}

	public void setStockCreatedTs(LocalDateTime stockCreatedTs) {
		this.stockCreatedTs = stockCreatedTs;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
