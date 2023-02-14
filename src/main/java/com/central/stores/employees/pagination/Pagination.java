package com.central.stores.employees.pagination;

import java.util.List;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.ListEmployee;
import com.central.stores.employees.model.dto.PaginationDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class Pagination {

	public static ListEmployee paginationEmployee(Page<Employee> pageListEmployee, 
			List<ResponseEmployeeDTO> listResponseEmployeeDTO) {

		PaginationDTO paginationDTO = PaginationDTO.builder()
				.moreElements(!pageListEmployee.isLast())
				.offset(pageListEmployee.getPageable().getOffset())
				.pageNumber(pageListEmployee.getPageable().getPageNumber())
				.pageSize(pageListEmployee.getPageable().getPageSize())
				.totalElements(pageListEmployee.getTotalElements())
				.totalPages(pageListEmployee.getTotalPages())
				.build();


		return ListEmployee.builder()
				.pageable(paginationDTO)
				.content(listResponseEmployeeDTO).build();
	}

	public static Pageable createPageable(Integer pageSize, Integer page, String sortBy) {
		String[] sort = sortBy.split(",");
		String evalSort = sort[0];
		String sortDirection = sort[1];
		Sort.Direction evalDirection = sortDirection(sortDirection);
		Sort sortOrderIgnoreCase = Sort.by(new Sort.Order(evalDirection, evalSort));
		return PageRequest.of(page, pageSize, sortOrderIgnoreCase);
	}

	private static Sort.Direction sortDirection(String sortDirection) {
		if (sortDirection.equalsIgnoreCase("DESC")) {
			return Sort.Direction.DESC;
		} else {
			return Sort.Direction.ASC;
		}
	}
}
