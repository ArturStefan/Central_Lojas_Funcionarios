package com.central.stores.employees.repository;

import java.util.UUID;

import com.central.stores.employees.model.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employee, UUID> {
	Employee findByCpf(String cpf);
	Employee findByRg(String rg);
	Page<Employee> findAllByActiveTrue(Pageable pageable);
	Page<Employee> findAllByActiveTrueAndAddressNeighborhood(Pageable pageable, String neighborhood);
}
