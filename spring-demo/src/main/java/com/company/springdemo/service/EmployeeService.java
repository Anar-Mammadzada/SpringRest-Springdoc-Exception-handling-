package com.company.springdemo.service;

import com.company.springdemo.rest.model.dto.EmployeeDto;
import com.company.springdemo.rest.model.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse getAllEmployees();

    EmployeeDto getEmployee(long id);

    EmployeeResponse getEmployeeByNameAndSurname(String name, String surname);

    void insert(EmployeeDto employeeDto);

    void update(EmployeeDto employeeDto, long id);

    void updatePatch(EmployeeDto employeeDto, long id);

    void delete(long id);

}
