package com.example.L15_UTandITdemo.repo;


import com.example.L15_UTandITdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository <Product,Long> {

}
