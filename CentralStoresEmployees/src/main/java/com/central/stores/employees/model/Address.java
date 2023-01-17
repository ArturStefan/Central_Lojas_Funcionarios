package com.central.stores.employees.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.central.stores.employees.constants.Conf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private UUID id;
	@Column(nullable = false)
	private String street;
	@Column(nullable = false)
	private Integer number;
	@Column(nullable = false)
	private String neighborhood;
	@Column(nullable = false)
	private String city;
	@DateTimeFormat(pattern = Conf.dateFormat)
	private LocalDate created;
	@DateTimeFormat(pattern = Conf.dateFormat)
	private LocalDate changed;
}
