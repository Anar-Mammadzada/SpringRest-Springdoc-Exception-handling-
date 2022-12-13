package com.company.springdemo.service.impl;

import com.company.springdemo.enums.ErrorCodeEnum;
import com.company.springdemo.exceptions.CustomNotFoundException;
import com.company.springdemo.model.Employee;
import com.company.springdemo.repository.EmployeeRepository;
import com.company.springdemo.rest.model.dto.EmployeeDto;
import com.company.springdemo.rest.model.response.EmployeeResponse;
import com.company.springdemo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse getAllEmployees() {
        List<EmployeeDto> employeeDtoList = employeeRepository.findAll()
                .stream().map(employee -> convertToDto(employee))
                .collect(Collectors.toList());

        return EmployeeResponse.builder()
                .employees(employeeDtoList)
                .build();
    }

    @Override
    public EmployeeDto getEmployee(long id) {
       return  employeeRepository.findById(id)
                .map(employee -> convertToDto(employee))
                .orElseThrow(()-> new CustomNotFoundException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND));
    }

    @Override
    public EmployeeResponse getEmployeeByNameAndSurname(String name, String surname) {
        List<EmployeeDto> employeeDtos = employeeRepository.findByNameAndSurname(name, surname)
                .stream().map(employee -> convertToDto(employee))
                .collect(Collectors.toList());

       return EmployeeResponse.builder().employees(employeeDtos).build();

    }

    @Override
    public void insert(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto,employee);
        employeeRepository.save(employee);
    }

    @Override
    public void update(EmployeeDto employeeDto, long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND));
        BeanUtils.copyProperties(employeeDto,employee);
        employeeRepository.save(employee);
    }

    @Override
    public void updatePatch(EmployeeDto employeeDto, long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND));
        if(employeeDto.getName() != null)
            employee.setName(employeeDto.getName());

        if(employeeDto.getSurname() != null)
            employee.setSurname(employeeDto.getSurname());

        if(employeeDto.getAge() > 0)
            employee.setAge(employeeDto.getAge());

        if(employeeDto.getSalary() > 0)
            employee.setSalary(employeeDto.getSalary());

        BeanUtils.copyProperties(employeeDto,employee);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND));
        employeeRepository.delete(employee);
    }


    private EmployeeDto convertToDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee,employeeDto);
        return employeeDto;
    }
}
