package com.example.L12_Spring_Data_JPA_Demo.repo;

import com.example.L12_Spring_Data_JPA_Demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository <Product,Long> {

}
