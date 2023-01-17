package com.central.stores.employees.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.central.stores.employees.model.Address;

public interface AddressRepository extends JpaRepository<Address, UUID>{

}
