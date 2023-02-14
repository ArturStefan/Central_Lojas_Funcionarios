package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import com.central.stores.employees.test.utils.ClassBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResponseEmployeeDTOTest {
	private ResponseEmployeeDTO expectedResponseEmployeeDTO;
	private AddressDTO addressDTO;
	private UUID id;

	@BeforeEach
	void setUp() throws Exception {
		id = UUID.randomUUID();
		expectedResponseEmployeeDTO = ClassBuilder.responseEmployeeDTOBuilder();
		addressDTO = ClassBuilder.addressDTOBuilder();
		expectedResponseEmployeeDTO.setId(id);
	}

	@Test
	void builder() {
		ResponseEmployeeDTO responseEmployeeDTO = ResponseEmployeeDTO.builder()
				.id(id)
				.name("Teste")
				.cpf("123456789")
				.rg("5544669878")
				.role("testador")
				.gender("M")
				.phone("987654321")
				.email("teste@teste.com")
				.address(addressDTO)
				.build();
				
		assertEquals(expectedResponseEmployeeDTO.toString(), responseEmployeeDTO.toString());
	}
	
	@Test
	void setter() {
		ResponseEmployeeDTO responseEmployeeDTO = new ResponseEmployeeDTO();
		responseEmployeeDTO.setId(id);
		responseEmployeeDTO.setName("Teste");
		responseEmployeeDTO.setCpf("123456789");
		responseEmployeeDTO.setRg("5544669878");
		responseEmployeeDTO.setRole("testador");
		responseEmployeeDTO.setGender("M");
		responseEmployeeDTO.setPhone("987654321");
		responseEmployeeDTO.setEmail("teste@teste.com");
		responseEmployeeDTO.setAddress(addressDTO);
		
		assertEquals(expectedResponseEmployeeDTO.toString(), responseEmployeeDTO.toString());
	}

}