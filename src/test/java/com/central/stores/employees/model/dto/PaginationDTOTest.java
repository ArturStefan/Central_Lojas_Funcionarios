package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.central.stores.employees.test.utils.ClassBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaginationDTOTest {
	
	private PaginationDTO expectedPaginationDTO;

	@BeforeEach
	void setUp() throws Exception {
		expectedPaginationDTO = ClassBuilder.paginationBuilder();
	}
	
	@Test
	void builder() {
		PaginationDTO paginationDTO = PaginationDTO.builder()
				.offset(0)
				.pageSize(4)
				.pageNumber(1)
				.moreElements(true)
				.totalPages(6)
				.totalElements(2)
				.build();
		assertEquals(expectedPaginationDTO.toString(), paginationDTO.toString());
	}

	@Test
	void setter() {
		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setOffset(0);
		paginationDTO.setPageSize(4);
		paginationDTO.setPageNumber(1);
		paginationDTO.setMoreElements(true);
		paginationDTO.setTotalPages(6);
	    paginationDTO.setTotalElements(2);
		assertEquals(expectedPaginationDTO.toString(), paginationDTO.toString());
	}

}