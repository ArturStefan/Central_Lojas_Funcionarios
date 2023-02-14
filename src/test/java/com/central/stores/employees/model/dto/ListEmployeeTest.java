package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import com.central.stores.employees.test.utils.ClassBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListEmployeeTest {
	
	private ListEmployee expectedListEmployee;
	private PaginationDTO paginationDTO;
	private ResponseEmployeeDTO responseEmployeeDTO;
	private UUID id;

	@BeforeEach
	void setUp() throws Exception {
		id = UUID.randomUUID();
		expectedListEmployee = ClassBuilder.listEmployeeBuilder();
		paginationDTO = ClassBuilder.paginationBuilder();
		responseEmployeeDTO = ClassBuilder.responseEmployeeDTOBuilder();
		responseEmployeeDTO.setId(id);
		expectedListEmployee.getContent().get(0).setId(id);
	}

	@Test
	void setter() {
		ListEmployee listEmployee = new ListEmployee();
		listEmployee.setPageable(paginationDTO);
		listEmployee.setContent(List.of(responseEmployeeDTO));
		assertEquals(expectedListEmployee.toString(), listEmployee.toString());
	}

}