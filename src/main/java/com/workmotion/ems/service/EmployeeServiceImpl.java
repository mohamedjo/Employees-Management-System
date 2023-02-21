package com.workmotion.ems.service;

import com.workmotion.ems.dal.model.Employee;
import com.workmotion.ems.dal.model.EmployementTerms;
import com.workmotion.ems.dal.repository.EmployeeRepository;
import com.workmotion.ems.dal.repository.EmployementTermsRepository;
import com.workmotion.ems.util.EMSException;
import com.workmotion.ems.util.ExceptionEnums;
import com.workmotion.ems.workflow.WorkFlowStates;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private static Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private ModelMapper mapper;
    @Autowired

    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployementTermsRepository employementTermsRepository;

    @Autowired
    private IWorkFlowService workFlowService;

    @Override
    public void addEmployee(Employee data) throws EMSException {
        if (data == null) {
            throw ExceptionEnums.INVALID_INPUT_EXCEPTION.get();
        }

        OffsetDateTime now = OffsetDateTime.now();
        Employee employee = mapper.map(data, Employee.class);
        employee.setCreatedAt(now);
        employee.setModifiedAt(now);
        employee.setStatus(Arrays.asList(WorkFlowStates.ADDED.name()));

        EmployementTerms contract = employementTermsRepository.findById(employee.getId()).orElse(null);
        if (contract == null) {
            throw ExceptionEnums.CONTRACT_NOT_FOUND.get();
        }
        employeeRepository.save(employee);

    }

    @Override
    public void updateEmployeeStatus(final Integer id, final String event) throws EMSException {
        final String methodName = "updateEmployeeStatus()";
        log.info("Updating empoyeeId:{} status to: {}", id, event);

        Employee employee = employeeRepository.findById(id).orElseThrow(ExceptionEnums.EMPLOYEE_NOT_FOUND);
        boolean accepted = workFlowService.executeTransition(employee, event);
        if (!accepted) {
            log.error("{} Status update failed with event: {}.", methodName, event);
            throw ExceptionEnums.STATE_TRANSITION_EXCEPTION.get();
        }

    }

    @Override
    public Employee getEmployee(final Integer id) throws EMSException {
        final String methodName = "getEmployee()";
        log.info("{} Get employee empoyeeId: {}", methodName, id);
        Employee employee = null;

        employee = employeeRepository.findById(id).orElseThrow(ExceptionEnums.EMPLOYEE_NOT_FOUND);

        return employee;


    }


}
