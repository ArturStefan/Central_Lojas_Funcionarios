package com.central.stores.employees.services.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.central.stores.employees.config.LoggerConfig;
import com.central.stores.employees.crypto.Cryptography;
import com.central.stores.employees.mapper.EmployeeMapper;
import com.central.stores.employees.mapper.UpdateModel;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.services.EmployeesServices;

@Component
public class EmployeesServicesImplement implements EmployeesServices {
	@Autowired
	private EmployeeMapper mapper;

	@Autowired
	private EmployeesRepository repository;

	private Employee employee;

	private ResponseEmployeeDTO responseEmployeeDTO;
	
	@Override
	public ResponseEntity<List<Employee>> findAll() {
		List<Employee> listEmployees = repository.findAllByActiveTrue();
		
		listEmployees.forEach(employee -> employee = Cryptography.decode(employee));

		LoggerConfig.LOGGER_EMPLOYEE.info("Listagem de funcionários realizada com sucesso!!!");

		return new ResponseEntity<List<Employee>>(listEmployees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> findByCpf(String employeeCpf) {
		employee = repository.findByCpf(Cryptography.encodeCpf(employeeCpf));

		employee = Cryptography.decode(employee);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Busca por funcionário " + employee.getName() + " realizada com sucesso!!!");

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Employee>> findByNeighborhood(String neighborhood) {
		List<Employee> listEmployees = repository.findAllByActiveTrueAndAddressNeighborhood(neighborhood);

		listEmployees.forEach(employee -> employee = Cryptography.decode(employee));
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Listagem de funcionários por bairro realizada com sucesso!!!");

		return new ResponseEntity<List<Employee>>(listEmployees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseEmployeeDTO> create(RequestEmployeeDTO requestEmployeeDTO) {
		employee = mapper.toModel(requestEmployeeDTO);
		employee = Cryptography.encode(employee);
		
		repository.save(employee);

		responseEmployeeDTO = mapper.modelToResponseEmployeeDTO(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Funcionário " + employee.getName() + " salvo com sucesso!!!");

		return new ResponseEntity<ResponseEmployeeDTO>(responseEmployeeDTO, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<ResponseEmployeeDTO> update(RequestEmployeeDTO requestEmployeeDTO, UUID employeeId) {
		employee = repository.findById(employeeId).get();
		employee = UpdateModel.employee(employee, requestEmployeeDTO);
		employee = Cryptography.encode(employee);
		
		repository.save(employee);

		responseEmployeeDTO = mapper.modelToResponseEmployeeDTO(employee);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Dados do funcionário " + employee.getName() + " atualizados com sucesso!!!");

		return new ResponseEntity<ResponseEmployeeDTO>(responseEmployeeDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseEmployeeDTO> delete(UUID employeeId) {
		employee = repository.findById(employeeId).get();
		employee = mapper.employeeDelete(employee);

		repository.save(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Funcionário " + employee.getName() + " deletado com sucesso!!!");

		return new ResponseEntity<ResponseEmployeeDTO>(HttpStatus.NO_CONTENT);
	}
}