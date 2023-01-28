package com.central.stores.employees.mapper;

import java.time.LocalDate;

import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.dto.AddressDTO;

public final class AddressMapper {

	public static Address toModel(AddressDTO addressDTO) {
		return Address.builder()
				.created(LocalDate.now())
				.city(addressDTO.getCity())
				.street(addressDTO.getStreet())
				.number(addressDTO.getNumber())
				.neighborhood(addressDTO.getNeighborhood())
				.build();
	}
	
	public static Address updateAddress(Address address, AddressDTO requestAddressDTO) {
		
		address.setChanged(LocalDate.now());
		address.setCity(requestAddressDTO.getCity());
		address.setStreet(requestAddressDTO.getStreet());
		address.setNumber(requestAddressDTO.getNumber());
		address.setNeighborhood(requestAddressDTO.getNeighborhood());
		
		return address;
	}
}
