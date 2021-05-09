package com.estock.management.estockmarket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estock.management.estockmarket.entity.StockExchange;

public interface StockExchangeRepo extends JpaRepository<StockExchange, Integer> {

	List<StockExchange> findByStockCreatedTsBetween(LocalDateTime startDate, LocalDateTime endDate);

}
