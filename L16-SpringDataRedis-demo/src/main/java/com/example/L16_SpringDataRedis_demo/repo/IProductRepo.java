package com.example.L16_SpringDataRedis_demo.repo;

import com.example.L16_SpringDataRedis_demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository<Product, Long> {
}
