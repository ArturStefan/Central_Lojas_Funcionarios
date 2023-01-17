package com.central.stores.employees.mapper;

import java.time.LocalDate;

import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;

import lombok.Generated;

@Generated
public final class UpdateModel {

	public static Employee employee(Employee employee, RequestEmployeeDTO requestEmployeeDTO) {
		employee.setChanged(LocalDate.now());
		employee.setRg(requestEmployeeDTO.getRg());
		employee.setCpf(requestEmployeeDTO.getCpf());
		employee.setName(requestEmployeeDTO.getName());
		employee.setRole(requestEmployeeDTO.getRole());
		employee.setPhone(requestEmployeeDTO.getPhone());
		employee.setEmail(requestEmployeeDTO.getEmail());
		employee.setGender(requestEmployeeDTO.getGender());
		
		return employee;
	}
	
	public static Address address(Address address, AddressDTO requestAddressDTO) {
	
		address.setChanged(LocalDate.now());
		address.setCity(requestAddressDTO.getCity());
		address.setStreet(requestAddressDTO.getStreet());
		address.setNumber(requestAddressDTO.getNumber());
		address.setNeighborhood(requestAddressDTO.getNeighborhood());
		
		return address;
	}
}
