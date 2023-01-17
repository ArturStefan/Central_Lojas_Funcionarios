package com.central.stores.employees.model.dto;

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
public class RequestEmployeeDTO {
	private String name;
	private String cpf;
	private String rg;
	private String role;
	private String gender;
	private String phone;
	private String email;
}