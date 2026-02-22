package com.example.L11_Spring_JDBC_demo.dao;

import com.example.L11_Spring_JDBC_demo.entity.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return  new Product(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getDouble("cost"));
    }
}
