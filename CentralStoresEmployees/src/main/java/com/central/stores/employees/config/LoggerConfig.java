package com.central.stores.employees.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.central.stores.employees.services.implement.AddressServicesImplent;
import com.central.stores.employees.services.implement.EmployeesServicesImplement;

import lombok.Generated;

@Generated
public final class LoggerConfig {
	public static final Logger LOGGER_EMPLOYEE = LoggerFactory.getLogger(EmployeesServicesImplement.class);
	public static final Logger LOGGER_ADDRESS = LoggerFactory.getLogger(AddressServicesImplent.class);
}
