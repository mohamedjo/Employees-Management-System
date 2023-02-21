package com.workmotion.ems.controller;

import com.workmotion.ems.dal.model.Employee;
import com.workmotion.ems.dal.model.EmployeeUpdateState;
import com.workmotion.ems.service.IEmployeeService;
import com.workmotion.ems.util.EMSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) throws Exception {

        employeeService.addEmployee(employee);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/employee")
    public ResponseEntity<Void> updateEmployeeStateByEmployeeID(@RequestBody EmployeeUpdateState employeeUpdateState) throws EMSException {


        employeeService.updateEmployeeStatus(employeeUpdateState.getEmployeeID(), employeeUpdateState.getEvent());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployeeByID(@RequestParam("employeeID") int employeeID) throws EMSException {
        HttpStatus httpStatus = HttpStatus.OK;
        Employee employee = employeeService.getEmployee(employeeID);

        return new ResponseEntity<>(employee, httpStatus);
    }
}
