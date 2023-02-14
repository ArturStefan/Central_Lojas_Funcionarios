package com.central.stores.employees.services.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.central.stores.employees.crypto.Cryptography;
import com.central.stores.employees.exception.DuplicateDocumentsException;
import com.central.stores.employees.exception.ResourceNotFoundException;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.ListEmployee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.pagination.Pagination;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.test.utils.ClassBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class EmployeesServicesImplementTest {

	@InjectMocks
	private EmployeesServicesImplement services;
	
	@Mock
	private Validator mockValidator;
	
	@Mock
	private EmployeesRepository repository;

	private Employee employee;

	private LocalValidatorFactoryBean validator;
	
	private ResponseEmployeeDTO responseEmployeeDTO; 

	private RequestEmployeeDTO requestEmployeeDTO;
	
	private Set<ConstraintViolation<Object>> violations;
	
	
	
	
	
	private Pageable pageable;
	
	private Page<Employee> pageEmployee;

	private Page<Employee> pageEmployeeEmpty;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();

		UUID id = UUID.randomUUID();
		employee = ClassBuilder.employeeBuilder();
		responseEmployeeDTO = ClassBuilder.responseEmployeeDTOBuilder();
		requestEmployeeDTO = ClassBuilder.requestEmployeeDTOBuilder();

		employee.setId(id);
		responseEmployeeDTO.setId(id);
		employee.setAddress(ClassBuilder.addressBuilder());
		
		pageable = Pagination.createPageable(1, 1, "name,DESC");
		pageEmployeeEmpty = new PageImpl<Employee>(List.of(), pageable, 1L);
		pageEmployee = new PageImpl<Employee>(List.of(employee), pageable, 1L);
	}
	
	@Test
	public void findByCpf() {
		employee = Cryptography.encode(employee);
		when(repository.findByCpf(anyString())).thenReturn(employee);
		ResponseEmployeeDTO emp = services.findByCpf("123456789");

		assertEquals(emp.toString(), responseEmployeeDTO.toString());
	}
	
	@Test
	public void findByCpfResourceNotFoundException() {
		String messageError = "No record found for this cpf";
		employee = Cryptography.encode(employee);
		
		when(repository.findByCpf(anyString())).thenReturn(null);
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.findByCpf("123456789");
		}).getMessage();

		assertEquals(messageError, message);
	}
	
	@Test
	public void findByNeighborhood() {
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrueAndAddressNeighborhood(any(), anyString()))
		.thenReturn(pageEmployee);
		ListEmployee empList = services.findByNeighborhood(1, 1, "name,DESC","teste");
		
		assertNotNull(empList);
	}
	
	@Test
	public void findByNeighborhoodResourceNotFoundException() {
		String messageError = "No record found for this neighborhood";
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrueAndAddressNeighborhood(any(), anyString()))
		.thenReturn(pageEmployeeEmpty);
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.findByNeighborhood(1, 1, "name,DESC", "teste");
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void findAll() {
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrue(any())).thenReturn(pageEmployee);
		ListEmployee empList = services.findAll(1, 1, "name,DESC");
		assertNotNull(empList);
	}
	
	@Test
	public void delete() {
		employee.setActive(false);
		employee = Cryptography.encode(employee);
		when(repository.findById(any())).thenReturn(Optional.of(employee));
		Employee emp = (Employee) services.delete(UUID.randomUUID());
		
		assertTrue(emp.getActive().equals(false));
	}
	@Test
	public void deleteResourceNotFoundException() {
		String messageError = "No records found for this id";
		
		employee = Cryptography.encode(employee);
		when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.delete(UUID.randomUUID());
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void create() {
		ResponseEntity<Object> emp = services.create(requestEmployeeDTO);
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.CREATED));
	}
	
	@Test
	public void createWithMissingFields() {
		requestEmployeeDTO.setCpf(null);
		
		violations = validator.validate(requestEmployeeDTO);
		when(mockValidator.validate(any())).thenReturn(violations);
		ResponseEntity<Object> emp = services.create(requestEmployeeDTO);
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
	}
	
	@Test
	public void createDuplicateDocumentsException() {
		String messageError = "Documents already registered";
		
		when(repository.findByRg(anyString())).thenReturn(employee);
		when(repository.findByCpf(anyString())).thenReturn(employee);
		
		String message = assertThrows(DuplicateDocumentsException.class, () -> {
			services.create(requestEmployeeDTO);
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void createWithDuplicateCpf() {
		String messageError = "Cpf already registered";
		
		when(repository.findByCpf(anyString())).thenReturn(employee);
		
		String message = assertThrows(DuplicateDocumentsException.class, () -> {
			services.create(requestEmployeeDTO);
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	@Test
	public void createWithDuplicateRg() {
		String messageError = "Rg already registered";
		
		when(repository.findByRg(anyString())).thenReturn(employee);
		
		String message = assertThrows(DuplicateDocumentsException.class, () -> {
			services.create(requestEmployeeDTO);
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void update() {
		when(repository.findById(any())).thenReturn(Optional.of(employee));
		ResponseEntity<Object> emp = services.update(requestEmployeeDTO, UUID.randomUUID());
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.NO_CONTENT));
	}

	@Test
	public void updateWithMissingFields() {
		requestEmployeeDTO.setCpf(null);
		
		violations = validator.validate(requestEmployeeDTO);
		when(mockValidator.validate(any())).thenReturn(violations);
		ResponseEntity<Object> emp = services.update(requestEmployeeDTO, UUID.randomUUID());
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
	}
	
	@Test
	public void updateResourceNotFoundException() {
		String messageError = "No records found for this id";
		
		when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.update(requestEmployeeDTO, UUID.randomUUID());
		}).getMessage();
		
		assertEquals(messageError, message);
	}
}
