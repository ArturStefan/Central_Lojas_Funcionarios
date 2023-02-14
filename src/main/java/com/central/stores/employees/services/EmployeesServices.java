package com.central.stores.employees.services;

import java.util.UUID;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.ListEmployee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmployeesServices {
	public ResponseEmployeeDTO findByCpf(String employeeCpf);
	public ListEmployee findAll(Integer pageSize, Integer page, String sortBy);
	public ListEmployee findByNeighborhood(Integer pageSize, Integer page, String sortBy, String neighborhood);
	public ResponseEntity<Object> create(RequestEmployeeDTO requestEmployeeDTO);
	public ResponseEntity<Object> update(RequestEmployeeDTO requestEmployeeDTO, UUID employeeId);
	public Employee delete(UUID employeeId);
}
