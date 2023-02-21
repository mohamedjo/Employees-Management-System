package com.workmotion.ems.dal.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.workmotion.ems.util.EmployeeStatusAttributeConverter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.findAll", query = "SELECT c FROM Employee c")
public class Employee implements Serializable {
    private static final long serialVersionUID = 849343719056973592L;

    @Id
    private Integer id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private LocalDate dob;
    
    private String gender;
    
    @Column(name = "passport_number")
    private String passportNumber;
    
    @ManyToOne
    @JoinColumn(name = "employement_terms_id")
    private EmployementTerms employementTerms;
    
    @Convert(converter = EmployeeStatusAttributeConverter.class)
    private List<String> status;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @Column(name = "modified_at")
    private OffsetDateTime modifiedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public EmployementTerms getEmployementTerms() {
        return employementTerms;
    }

    public void setEmployementTerms(EmployementTerms employementTerms) {
        this.employementTerms = employementTerms;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(OffsetDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
