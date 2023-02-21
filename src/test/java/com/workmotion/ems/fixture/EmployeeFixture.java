package com.workmotion.ems.fixture;

import com.workmotion.ems.dal.model.Employee;
import com.workmotion.ems.dal.model.EmployeeUpdateState;
import com.workmotion.ems.dal.model.EmployementTerms;

public class EmployeeFixture {

    public static Employee getEmployee() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Mohamed");
        employee.setLastName("Youssef");
        employee.setPassportNumber("cvfvmkf123fff");
        employee.setGender("male");
        EmployementTerms employementTerms= new EmployementTerms();
        employementTerms.setId(1);
        employee.setEmployementTerms(employementTerms);
        return employee;
    }
    public static EmployeeUpdateState getEmployeeUpdateState() {
        EmployeeUpdateState employeeUpdateState = new EmployeeUpdateState();
        employeeUpdateState.setEmployeeID(1);
        employeeUpdateState.setEvent("ACTIVATE");
        return employeeUpdateState;
    }
}
