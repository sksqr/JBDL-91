package com.example.L13_minor_project_01.repo;

import com.example.L13_minor_project_01.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByNumber(String number);
}
