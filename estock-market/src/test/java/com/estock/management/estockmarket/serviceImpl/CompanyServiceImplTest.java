package com.estock.management.estockmarket.serviceImpl;

import static org.mockito.Mockito.lenient;

import java.text.ParseException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.estock.management.estockmarket.entity.Company;
import com.estock.management.estockmarket.repository.CompanyRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {

	@Mock
	private CompanyRepo companyRepo;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@Mock
	CompanyServiceImpl companyServiceImpl;
	
	@BeforeEach
    void initialize() throws ParseException {
        MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindCompanyByCode() {
		lenient().when(companyRepo.findByCompanyCode(Mockito.anyString()))
				.thenReturn(Optional.ofNullable(new Company()));
		try {
			companyServiceImpl.findCompanyByCode("12345");

		} catch (Exception exception) {

		}

	}
}
