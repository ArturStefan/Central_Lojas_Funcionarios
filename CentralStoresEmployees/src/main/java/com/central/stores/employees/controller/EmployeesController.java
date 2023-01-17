package com.central.stores.employees.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.services.EmployeesServices;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
	@Autowired
	private EmployeesServices services;

	@PostMapping
	public ResponseEntity<ResponseEmployeeDTO> create(@RequestBody RequestEmployeeDTO requestEmployeeDTO) {
		return services.create(requestEmployeeDTO);
	}

	@PutMapping("/{employeeId}")
	public ResponseEntity<ResponseEmployeeDTO> update(@RequestBody RequestEmployeeDTO requestEmployeeDTO,
			@PathVariable("employeeId") UUID employeeId) {
		return services.update(requestEmployeeDTO, employeeId);
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<ResponseEmployeeDTO> delete(@PathVariable("employeeId") UUID employeeId){
		return services.delete(employeeId);
	}
	
	@GetMapping("list")
	public ResponseEntity<List<Employee>> listEmployees(){
		return services.findAll();
	}
	
	@GetMapping("/{employeeCPF}")
	public ResponseEntity<Employee> findByCPF(@PathVariable("employeeCPF") String employeeCpf){
		return services.findByCpf(employeeCpf);
	}
	
	@GetMapping
	public ResponseEntity<List<Employee>> findByNeighborhood(@RequestParam("neighborhood") String neighborhood){
		return services.findByNeighborhood(neighborhood);
	}
}
