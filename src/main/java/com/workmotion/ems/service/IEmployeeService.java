package com.workmotion.ems.service;

import com.workmotion.ems.dal.model.Employee;
import com.workmotion.ems.util.EMSException;

public interface IEmployeeService {

    public abstract void addEmployee(Employee employee) throws EMSException;

    public abstract Employee getEmployee(Integer id) throws EMSException;

    public abstract void updateEmployeeStatus(Integer id, String status) throws EMSException;
}
