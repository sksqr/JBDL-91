package com.example.L15_UTandITdemo.repo;


import com.example.L15_UTandITdemo.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends JpaRepository <Branch,Long> {
}
