package com.workmotion.ems.dal.model;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeUpdateState {

    private int employeeID;

    private String event;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
