package com.central.stores.employees.services.implement;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.central.stores.employees.config.LoggerConfig;
import com.central.stores.employees.crypto.Cryptography;
import com.central.stores.employees.exception.DuplicateDocumentsException;
import com.central.stores.employees.exception.ResourceNotFoundException;
import com.central.stores.employees.mapper.EmployeeMapper;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.ListEmployee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseSummarizedEmployeeDTO;
import com.central.stores.employees.model.dto.error.ResponseError;
import com.central.stores.employees.pagination.Pagination;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.services.EmployeesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeesServicesImplement implements EmployeesServices {
	@Autowired
	private EmployeesRepository repository;
	
	@Autowired
	private Validator validator;

	private Employee employee;
	
	private Page<Employee> pageListEmployees;
	
	private ListEmployee responseListEmployee;
	
	private ResponseEmployeeDTO responseEmployeeDTO;
	
	private List<ResponseEmployeeDTO> listEmployeesDTO;

	private ResponseSummarizedEmployeeDTO responseSummarizedEmployeeDTO;
	
	@Override
	@Cacheable(cacheNames = "Employees", key="#root.method.name")
	public ListEmployee findAll(Integer pageSize, Integer page, String sortBy) {
		Pageable pageable = Pagination.createPageable(pageSize, page, sortBy);
		
		pageListEmployees = repository.findAllByActiveTrue(pageable);
		
		pageListEmployees.forEach(employee -> employee = Cryptography.decode(employee));
		
		listEmployeesDTO = pageListEmployees
				.stream()
				.map(employee -> EmployeeMapper.modelToResponseEmployeeDTO(employee))
				.collect(Collectors.toList());
		
		responseListEmployee = Pagination.paginationEmployee(pageListEmployees, listEmployeesDTO);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Employee listing");

		return responseListEmployee;
	}

	@Override
	@Cacheable(cacheNames = "Employees", key="#employeeCpf")
	public ResponseEmployeeDTO findByCpf(String employeeCpf) {
		employee = repository.findByCpf(Cryptography.encodeCpf(employeeCpf));
		
		if(employee == null) {
			LoggerConfig.LOGGER_EMPLOYEE.error("No record found for this cpf");
			throw new ResourceNotFoundException("No record found for this cpf");
		}

		employee = Cryptography.decode(employee);
		
		responseEmployeeDTO = EmployeeMapper.modelToResponseEmployeeDTO(employee);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Employee found");

		return responseEmployeeDTO;
	}

	@Override
	@Cacheable(cacheNames = "Employees", key="#neighborhood")
	public ListEmployee findByNeighborhood(Integer pageSize, Integer page, String sortBy, String neighborhood) {
		Pageable pageable = Pagination.createPageable(pageSize, page, sortBy);
		
		pageListEmployees = repository.findAllByActiveTrueAndAddressNeighborhood(pageable, neighborhood);

		if(pageListEmployees.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("No record found for this neighborhood");
			throw new ResourceNotFoundException("No record found for this neighborhood");
		}
		
		pageListEmployees.forEach(employee -> employee = Cryptography.decode(employee));
		
		listEmployeesDTO = pageListEmployees
				.stream()
				.map(employee -> EmployeeMapper.modelToResponseEmployeeDTO(employee))
				.collect(Collectors.toList());
		
		responseListEmployee = Pagination.paginationEmployee(pageListEmployees, listEmployeesDTO);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("List of employees by neighborhood");

		return responseListEmployee;
	}

	@Override
	public ResponseEntity<Object> create(RequestEmployeeDTO requestEmployeeDTO) {
		Set<ConstraintViolation<RequestEmployeeDTO>> violations = validator.validate(requestEmployeeDTO);
		
		if(!violations.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("Validation error");
			return new ResponseEntity<Object>(ResponseError.createFromValidation(violations), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		employee = EmployeeMapper.toModel(requestEmployeeDTO);
		employee = Cryptography.encode(employee);
		
		String message = duplicateDocumentValidator(employee);
		
		if(!message.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("Duplicate documents");
			throw new DuplicateDocumentsException(message);
		}
		
		repository.save(employee);

		responseSummarizedEmployeeDTO = EmployeeMapper.modelToResponseSummarizedEmployeeDTO(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Employee saved");

		return new ResponseEntity<Object>(responseSummarizedEmployeeDTO, HttpStatus.CREATED) ;

	}

	@Override
	public ResponseEntity<Object> update(RequestEmployeeDTO requestEmployeeDTO, UUID employeeId) {
		Set<ConstraintViolation<RequestEmployeeDTO>> violations = validator.validate(requestEmployeeDTO);
		
		if(!violations.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("Validation error");
			return new ResponseEntity<Object>(ResponseError.createFromValidation(violations), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		employee = repository.findById(employeeId).orElseThrow(() -> 
		 new ResourceNotFoundException("No records found for this id"));

		employee = EmployeeMapper.updateEmployee(employee, requestEmployeeDTO);
		employee = Cryptography.encode(employee);
		
		repository.save(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Employee data successfully updated");

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@Override
	public Employee delete(UUID employeeId) {
		employee = repository.findById(employeeId).orElseThrow(() -> 
		 new ResourceNotFoundException("No records found for this id"));
		
		employee = EmployeeMapper.employeeDelete(employee);

		repository.save(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Successfully deleted employee");
		
		return employee;
	}
	
	private String duplicateDocumentValidator(Employee employee) {
		String message = "";

		Employee employeeEntityRg =  repository.findByRg(employee.getRg()); 	
		Employee employeeEntityCpf =  repository.findByCpf(employee.getCpf()); 
		
		if(employeeEntityCpf != null && employeeEntityRg != null) {
			message = "Documents already registered";
		}
		else if(employeeEntityCpf != null) {
			message = "Cpf already registered";
		}
		else if( employeeEntityRg != null){
			message = "Rg already registered";
		}		
		
		return message;
	}
}