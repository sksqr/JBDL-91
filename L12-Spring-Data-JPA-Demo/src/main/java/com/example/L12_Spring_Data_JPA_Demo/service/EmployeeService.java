package com.example.L12_Spring_Data_JPA_Demo.service;

import com.example.L12_Spring_Data_JPA_Demo.dto.EmployeeDetailReqDto;
import com.example.L12_Spring_Data_JPA_Demo.entity.Address;
import com.example.L12_Spring_Data_JPA_Demo.entity.Branch;
import com.example.L12_Spring_Data_JPA_Demo.entity.Employee;
import com.example.L12_Spring_Data_JPA_Demo.exception.AdhaarNotVerifiedException;
import com.example.L12_Spring_Data_JPA_Demo.exception.LaptopAllocationFailedException;
import com.example.L12_Spring_Data_JPA_Demo.repo.BranchRepo;
import com.example.L12_Spring_Data_JPA_Demo.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BranchRepo branchRepo;

    public List<Employee> getAll(){
        return employeeRepo.findAll();
    }

    @Transactional(rollbackFor = AdhaarNotVerifiedException.class)
    public Long create(EmployeeDetailReqDto employeeDetailReq) throws AdhaarNotVerifiedException, LaptopAllocationFailedException {


        Employee employee = new Employee();
        employee.setName(employeeDetailReq.getName());
        employee.setEmail(employeeDetailReq.getEmail());

        Branch branch = branchRepo.findById(employeeDetailReq.getBranchId()).get();
        employee.setBranch(branch);

        Address address = new Address();
        address.setLine1(employeeDetailReq.getLine1());
        address.setLine2(employeeDetailReq.getLine2());
        address.setCity(employeeDetailReq.getCity());
        employee.setAddress(address);
        employeeRepo.save(employee);


        // Adhaar API call
        boolean isAdhaarVerified = true;

        // Asset Allocation API call to Asset Management System
        boolean isLaptopAllocated = true;

        if(!isAdhaarVerified){
            throw new AdhaarNotVerifiedException("Adhaar verification failed");
        }
        if(!isLaptopAllocated){
            throw new LaptopAllocationFailedException("Laptop allocation failed");
        }


        return employee.getId();

    }



    public Employee findById(Long id){
        return employeeRepo.findById(id).get();
    }

    public Employee findByEmail(String email){
        return employeeRepo.findByEmail(email);
    }
}
