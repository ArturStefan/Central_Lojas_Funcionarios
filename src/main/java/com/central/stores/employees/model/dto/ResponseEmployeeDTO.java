package com.central.stores.employees.model.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEmployeeDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private UUID id;
	private String cpf;
	private String name;
	private String rg;
	private String role;
	private String gender;
	private String phone;
	private String email;
	private AddressDTO address;
}
