package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import com.central.stores.employees.test.utils.ClassBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResponseSummarizedEmployeeDTOTest {

	private UUID id;
	
	private ResponseSummarizedEmployeeDTO expectedResponseSummarizedEmployeeDTO;
	
	@BeforeEach
	void setUp() {
		id = UUID.randomUUID();
		expectedResponseSummarizedEmployeeDTO = ClassBuilder.responseSummarizedEmployeeDTOBuilder();
		expectedResponseSummarizedEmployeeDTO.setId(id);
	}
	
	@Test
	void setter() {
		ResponseSummarizedEmployeeDTO responseSummarizedEmployeeDTO = new ResponseSummarizedEmployeeDTO();
		responseSummarizedEmployeeDTO.setId(id);
		responseSummarizedEmployeeDTO.setName("Teste");
		
		assertEquals(expectedResponseSummarizedEmployeeDTO.toString(), responseSummarizedEmployeeDTO.toString());
	}
	
	@Test
	void builder() {
		ResponseSummarizedEmployeeDTO responseSummarizedEmployeeDTO = ResponseSummarizedEmployeeDTO.builder()
				.id(id)
				.name("Teste")
				.build();
		
		assertEquals(expectedResponseSummarizedEmployeeDTO.toString(), responseSummarizedEmployeeDTO.toString());
	}
}
