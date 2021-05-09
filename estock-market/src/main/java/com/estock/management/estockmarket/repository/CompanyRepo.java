package com.estock.management.estockmarket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estock.management.estockmarket.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {

	Optional<Company> findByCompanyCode(String companyCode);

	void deleteByCompanyCode(String companyCode);
	
}
