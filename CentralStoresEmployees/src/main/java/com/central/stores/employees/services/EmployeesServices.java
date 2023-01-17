package com.central.stores.employees.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;

@Service
public interface EmployeesServices {
	public ResponseEntity<List<Employee>> findAll();
	public ResponseEntity<Employee> findByCpf(String employeeCpf);
	public ResponseEntity<List<Employee>> findByNeighborhood(String neighborhood);
	public ResponseEntity<ResponseEmployeeDTO> create(RequestEmployeeDTO requestEmployeeDTO);
	public ResponseEntity<ResponseEmployeeDTO> update(RequestEmployeeDTO requestEmployeeDTO, UUID employeeId);
	public ResponseEntity<ResponseEmployeeDTO> delete(UUID employeeId);
}
