package com.central.stores.employees.services;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.dto.AddressDTO;

@Service
public interface AddressServices {
	public ResponseEntity<Address> create(AddressDTO requestAddressDTO, UUID employeeId);
	public ResponseEntity<Address> update(AddressDTO requestAddressDTO, UUID addressId);
}
