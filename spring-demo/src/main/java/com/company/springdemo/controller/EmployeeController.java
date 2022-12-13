package com.company.springdemo.controller;

import com.company.springdemo.rest.model.dto.EmployeeDto;
import com.company.springdemo.rest.model.response.EmployeeResponse;
import com.company.springdemo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Services",description = "emp serv")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public EmployeeResponse getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    @Operation(summary = "this gets employee by id")
    public EmployeeDto getEmployee(@PathVariable("id")long employeeId){
       return  employeeService.getEmployee(employeeId);
    }

    @GetMapping("/search")
    public EmployeeResponse getEmployeeByNameAndSurname(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname){
        return employeeService.getEmployeeByNameAndSurname(name,surname);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        employeeService.insert(employeeDto);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable("id") long id){
       employeeService.update(employeeDto,id);
    }

    @PatchMapping ("/{id}")
    public void updateEmployeePatch(@RequestBody EmployeeDto employeeDto, @PathVariable("id") long id){
        employeeService.updatePatch(employeeDto,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id){
        employeeService.delete(id);
    }
}
