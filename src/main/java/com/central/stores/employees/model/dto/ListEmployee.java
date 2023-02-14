package com.central.stores.employees.model.dto;

import java.io.Serializable;
import java.util.List;

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
public class ListEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    private PaginationDTO pageable;
    private List<ResponseEmployeeDTO> content;
}