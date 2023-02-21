package com.workmotion.ems.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workmotion.ems.EmsApplication;
import com.workmotion.ems.dal.model.Employee;
import com.workmotion.ems.dal.model.EmployeeUpdateState;
import com.workmotion.ems.fixture.EmployeeFixture;
import com.workmotion.ems.service.IEmployeeService;
import com.workmotion.ems.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.workmotion.ems.fixture.EmployeeFixture.getEmployeeUpdateState;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = EmsApplication.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    public static final String REQUEST_URL = "/api/employee";


    private MockMvc mockMvc;

    @MockBean
    private IEmployeeService employeeService;

    @Autowired
    private EmployeeController classUnderTest;

    @BeforeEach
    public void init() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest)
                .build();
    }


    @Test
    void givenValidBody_whenAddEmployeeRequestIsReceived_ThenStatusIs201() throws Exception {
        Employee employee = EmployeeFixture.getEmployee();
        doNothing().when(employeeService).addEmployee(employee);
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(employee);

        mockMvc.perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }


    @Test
    void givenInValidBody_whenAddEmployeeRequestIsReceived_ThenStatusIs400() throws Exception {
        doNothing().when(employeeService).addEmployee(any());
        mockMvc.perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidBody_whenUpdateEmployeeStatusRequestIsReceived_ThenStatusIs201() throws Exception {
        EmployeeUpdateState employeeUpdateState = getEmployeeUpdateState();
        doNothing().when(employeeService).updateEmployeeStatus(employeeUpdateState.getEmployeeID(),employeeUpdateState.getEvent());
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(employeeUpdateState);

        mockMvc.perform(patch(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }



    @Test
    void givenInValidBody_whenUpdateEmployeeStatusRequestIsReceived_ThenStatusIs201() throws Exception {
        doNothing().when(employeeService).updateEmployeeStatus(any(),any());

        mockMvc.perform(patch(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenEmployeeID_whenGetEmployeeByIdRequestIsReceived_ThenStatusIs201AndEmployeeReturned() throws Exception {

        when(employeeService.getEmployee(23)).thenReturn(EmployeeFixture.getEmployee());


        MvcResult mvcResult = mockMvc.perform(get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("employeeID", "23"))
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper objectMapper = TestUtils.getMapper();
        Employee actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Employee>() {
        });
        assertNotNull(actualResponseBody);
    }

    @Test
    void givenEmptyEmployeeID_whenGetEmployeeByIdRequestIsReceived_ThenStatusIs201AndEmployeeReturned() throws Exception {
        mockMvc.perform(get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("employeeID", ""))
                .andExpect(status().isBadRequest())
                .andReturn();


    }




}

