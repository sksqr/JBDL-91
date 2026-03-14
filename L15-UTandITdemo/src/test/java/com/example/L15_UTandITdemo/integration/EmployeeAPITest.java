package com.example.L15_UTandITdemo.integration;


import com.example.L15_UTandITdemo.dto.EmployeeDetailReqDto;
import com.example.L15_UTandITdemo.entity.Address;
import com.example.L15_UTandITdemo.entity.Branch;
import com.example.L15_UTandITdemo.entity.Employee;
import com.example.L15_UTandITdemo.repo.BranchRepo;
import com.example.L15_UTandITdemo.repo.EmployeeRepo;
import com.example.L15_UTandITdemo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = ("classpath:application-it.properties")
)
public class EmployeeAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BranchRepo branchRepo;

    private EmployeeDetailReqDto employeeDetailReq;

    private EmployeeDetailReqDto employeeDetailReq2;


    private Branch branch;

    private Employee employee;


    private Address address;

    @BeforeEach
    public void setup(){


        branch = new Branch();
        branch.setName("Noida Branch");
        branchRepo.save(branch);

        employeeDetailReq = new EmployeeDetailReqDto();
        employeeDetailReq.setEmail("ravi@yopmail.com");
        employeeDetailReq.setName("Ravi");
        employeeDetailReq.setCity("Delhi");
        employeeDetailReq.setLine1("H 120");
        employeeDetailReq.setLine2("Sector 40");
        employeeDetailReq.setBranchId(1l);


        employeeDetailReq2 = new EmployeeDetailReqDto();
        employeeDetailReq2.setEmail("rahul@yopmail.com");
        employeeDetailReq2.setCity("Delhi");
        employeeDetailReq2.setLine1("H 120");
        employeeDetailReq2.setLine2("Sector 40");
        employeeDetailReq2.setBranchId(1l);


    }



    @Test
    public void testCreateEmpAPI() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(employeeDetailReq);

        mockMvc.perform(post("/emp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andDo(print()).andExpect(status().isOk());
        Employee employee1 = employeeRepo.findByEmail("ravi@yopmail.com");
        assertThat(employee1).isNotNull();
    }



    @Test
    public void testCreateEmpAPIWithoutName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(employeeDetailReq2);

        mockMvc.perform(post("/emp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print()).andExpect(status().is5xxServerError());

        Employee employeeFromDB = employeeRepo.findByEmail("rahul@yopmail.com");
        assertThat(employeeFromDB).isNull();
    }



}
