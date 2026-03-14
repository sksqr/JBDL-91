package com.example.L15_UTandITdemo.repo;


import com.example.L15_UTandITdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo  extends JpaRepository<Employee,Long> {

    Employee findByEmail(String email);
}
