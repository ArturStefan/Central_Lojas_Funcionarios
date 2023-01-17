package com.central.stores.employees.services.implement;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.central.stores.employees.config.LoggerConfig;
import com.central.stores.employees.mapper.AddressMapper;
import com.central.stores.employees.mapper.UpdateModel;
import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.repository.AddressRepository;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.services.AddressServices;

@Component
public class AddressServicesImplent implements AddressServices {
	@Autowired
	private AddressMapper mapper;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

	private Address address;
	
	private Employee employee;
	
	@Override
	public ResponseEntity<Address> create(AddressDTO requestAddressDTO, UUID employeeId) {
		address = mapper.toModel(requestAddressDTO);
		addressRepository.save(address);
		
		employee = employeesRepository.findById(employeeId).get();
		employee.setAddress(address);
		
		employeesRepository.save(employee);
		
		LoggerConfig.LOGGER_ADDRESS.info("Endereço do funcionário " + employee.getName() + " salvo com sucesso!!!");

		return new ResponseEntity<Address>(address, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Address> update(AddressDTO requestAddressDTO, UUID addressId) {
		address = addressRepository.findById(addressId).get();
		address =  UpdateModel.address(address, requestAddressDTO);
		
		addressRepository.save(address);
		
		LoggerConfig.LOGGER_ADDRESS.info("Endereço atualizado com sucesso!!!");
		
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

}
