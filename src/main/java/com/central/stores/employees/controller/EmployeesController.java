package com.central.stores.employees.controller;

import java.util.UUID;

import javax.validation.constraints.Min;

import com.central.stores.employees.model.dto.ListEmployee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.services.EmployeesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
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

@RestController
@EnableSpringDataWebSupport
@RequestMapping("/employees")
public class EmployeesController {
	@Autowired
	private EmployeesServices services;
	
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody RequestEmployeeDTO requestEmployeeDTO) {
		return services.create(requestEmployeeDTO);
	}

	@PutMapping("/{employeeId}")
	public ResponseEntity<Object> update(@RequestBody RequestEmployeeDTO requestEmployeeDTO,
			@PathVariable("employeeId") UUID employeeId) {
		return services.update(requestEmployeeDTO, employeeId);
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<Object> delete(@PathVariable("employeeId") UUID employeeId){
		 services.delete(employeeId);
		 return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{employeeCPF}")
	public ResponseEntity<ResponseEmployeeDTO> findByCPF(@PathVariable("employeeCPF") final String employeeCpf){
		return new ResponseEntity<ResponseEmployeeDTO>(services.findByCpf(employeeCpf),HttpStatus.OK);
	}
	
	@GetMapping("list")
	public ResponseEntity<ListEmployee> listEmployees(
			@Min(value=1, message = "Tamanho mínimo 1.")
			@RequestParam(defaultValue = "10" , value="pageSize", required = false) Integer pageSize, 
			@Min(value=0, message = "Tamanho mínimo 0.")
			@RequestParam(defaultValue = "0" , value="page", required = false) Integer page, 
			@RequestParam(defaultValue = "name, DESC" , value="sortBy", required = false) String sortBy){
		return new ResponseEntity<ListEmployee>(services.findAll(pageSize, page, sortBy), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ListEmployee> findByNeighborhood(
			@Min(value=1, message = "Tamanho mínimo 1.")
			@RequestParam(defaultValue = "10" , value="pageSize", required = false) Integer pageSize, 
			@Min(value=0, message = "Tamanho mínimo 0.")
			@RequestParam(defaultValue = "0" , value="page", required = false) Integer page, 
			@RequestParam(defaultValue = "name, DESC" , value="sortBy", required = false) String sortBy,
			@RequestParam("neighborhood") String neighborhood){
		return new ResponseEntity<ListEmployee>(services.findByNeighborhood(pageSize, page, sortBy, neighborhood), HttpStatus.OK);
	}
}
