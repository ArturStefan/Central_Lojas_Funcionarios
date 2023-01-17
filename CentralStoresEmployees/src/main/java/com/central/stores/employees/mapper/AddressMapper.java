package com.central.stores.employees.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.dto.AddressDTO;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	@Mappings({
		@Mapping(target= "id", ignore= true),
		@Mapping(target= "changed", ignore= true),
		@Mapping(target= "created", expression = "java(java.time.LocalDate.now())"),
	})
	
	Address toModel(AddressDTO addressDTO);
	
}
