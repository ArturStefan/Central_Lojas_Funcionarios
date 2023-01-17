package com.central.stores.employees.crypto;

import java.util.Base64;

import com.central.stores.employees.model.Employee;

import lombok.Generated;

@Generated
public final class Cryptography {
	public static Employee encode(Employee employee) {
		employee.setCpf(new String(Base64.getEncoder().encode(employee.getCpf().getBytes())));
		employee.setRg(new String(Base64.getEncoder().encode(employee.getRg().getBytes())));

		return employee;
	}

	public static Employee decode(Employee employee) {
		employee.setCpf(new String(Base64.getDecoder().decode(employee.getCpf().getBytes())));
		employee.setRg(new String(Base64.getDecoder().decode(employee.getRg().getBytes())));

		return employee;
	}

	public static String encodeCpf(String employeeCpf) {
		employeeCpf = (new String(Base64.getEncoder().encode(employeeCpf.getBytes())));
		
		return employeeCpf;
	}
}
