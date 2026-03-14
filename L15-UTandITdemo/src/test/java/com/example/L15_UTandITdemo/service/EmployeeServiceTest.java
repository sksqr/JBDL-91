package com.example.L15_UTandITdemo.service;

import com.example.L15_UTandITdemo.dto.EmployeeDetailReqDto;
import com.example.L15_UTandITdemo.entity.Address;
import com.example.L15_UTandITdemo.entity.Branch;
import com.example.L15_UTandITdemo.entity.Employee;
import com.example.L15_UTandITdemo.exception.AdhaarNotVerifiedException;
import com.example.L15_UTandITdemo.exception.LaptopAllocationFailedException;
import com.example.L15_UTandITdemo.repo.BranchRepo;
import com.example.L15_UTandITdemo.repo.EmployeeRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployeeServiceTest {

    private EmployeeService employeeService;



    @Mock
    private BranchRepo branchRepo;
    @Mock
    private EmployeeRepo employeeRepo;

    private EmployeeDetailReqDto employeeDetailReq;


    private Branch branch;

    private Employee employee;

    private AutoCloseable autoCloseable;

    private Address address;

    @BeforeEach
    public void setup(){

        autoCloseable = MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(employeeRepo,branchRepo);


        branch = new Branch();
        branch.setName("Noida Branch");
        branch.setId(1l);

        employeeDetailReq = new EmployeeDetailReqDto();
        employeeDetailReq.setEmail("ravi@yopmail.com");
        employeeDetailReq.setName("Ravi");
        employeeDetailReq.setCity("Delhi");
        employeeDetailReq.setLine1("H 120");
        employeeDetailReq.setLine2("Sector 40");
        employeeDetailReq.setBranchId(1l);

        address = new Address();
        address.setCity("Delhi");
        address.setLine1("H 120");
        address.setLine2("Sector 40");




        employee = new Employee();
        employee.setId(1l);
        employee.setEmail("ravi@yopmail.com");
        employee.setName("Ravi");
        employee.setBranch(branch);
        employee.setAddress(address);


    }

    @Test
    public void testCreateEmployee() throws AdhaarNotVerifiedException, LaptopAllocationFailedException {

        Optional<Branch> optionalBranch = Optional.of(branch);
        when(branchRepo.findById(1l)).thenReturn(optionalBranch);
        when(employeeRepo.save(any())).thenReturn(employee);


        Long id = employeeService.create(employeeDetailReq);

        assertThat(id).isEqualTo(employee.getId());


    }

    @Test
    public void testFindByEmail(){
        when(employeeRepo.findByEmail(any())).thenReturn(employee);
        Employee employee1 = employeeService.findByEmail("ravi@yopmail.com");
        assertThat(employee1.getId()).isEqualTo(employee.getId());

    }
}
