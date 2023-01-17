package com.central.stores.employees.services.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.central.stores.employees.crypto.Cryptography;
import com.central.stores.employees.mapper.EmployeeMapper;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.test.utils.ClassBuilder;

public class EmployeesServicesImplementTest {

	@InjectMocks
	private EmployeesServicesImplement services;
	
	@Mock
	private EmployeesRepository repository;
	
	@Mock
	private EmployeeMapper mapper;
	
	private Employee employee;

	private RequestEmployeeDTO requestEmployeeDTO;
	
	private ResponseEmployeeDTO responseEmployeeDTO;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		employee = ClassBuilder.employeeBuilder();
		requestEmployeeDTO = ClassBuilder.requestEmployeeDTOBuilder();
		responseEmployeeDTO = ClassBuilder.responseEmployeeDTOBuilder();
	}
	
	@Test
	public void findByCpfTest() {
		employee = Cryptography.encode(employee);
		when(repository.findByCpf(anyString())).thenReturn(employee);
		Employee emp = services.findByCpf("123456789").getBody();

		assertEquals(emp, employee);
	}
	
	@Test
	public void findByNeighborhoodTest() {
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrueAndAddressNeighborhood(anyString()))
		.thenReturn(List.of(employee));
		ResponseEntity<List<Employee>> empList = services.findByNeighborhood("teste");
		
		assertNotNull(empList);
	}
	
	@Test
	public void findAllTeste() {
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrue()).thenReturn(List.of(employee));
		ResponseEntity<List<Employee>> empList = services.findAll();
		assertNotNull(empList);
	}
	
	@Test
	public void deleteTest() {
		employee = Cryptography.encode(employee);
		when(mapper.employeeDelete(any())).thenReturn(employee);
		when(repository.findById(any())).thenReturn(Optional.of(employee));
		ResponseEntity<ResponseEmployeeDTO> emp = services.delete(UUID.randomUUID());
		
		assertTrue(emp.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void createTest() {
		when(mapper.toModel(any())).thenReturn(employee);
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		ResponseEntity<ResponseEmployeeDTO> emp = services.create(requestEmployeeDTO);
		
		assertNotNull(emp);
	}
	
	@Test
	public void updateTest() {
		when(repository.findById(any())).thenReturn(Optional.of(employee));
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		ResponseEntity<ResponseEmployeeDTO> emp = services.update(requestEmployeeDTO, UUID.randomUUID());
		
		assertNotNull(emp);
	}
}
