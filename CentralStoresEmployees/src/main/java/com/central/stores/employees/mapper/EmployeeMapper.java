package com.central.stores.employees.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	@Mappings({
		@Mapping(target= "id", ignore= true),
		@Mapping(target= "changed", ignore= true),
		@Mapping(target= "address", ignore= true),
		@Mapping(target= "active", expression = "java(java.lang.Boolean.TRUE)"),
		@Mapping(target= "created", expression = "java(java.time.LocalDate.now())"),
	})
	Employee toModel(RequestEmployeeDTO requestEmployeeDTO);
	
	@Mappings({
		@Mapping(target= "active", expression = "java(java.lang.Boolean.FALSE)"),
		@Mapping(target= "changed", expression = "java(java.time.LocalDate.now())"),
	})
	Employee employeeDelete(Employee employee);

	ResponseEmployeeDTO modelToResponseEmployeeDTO(Employee employee);
}
