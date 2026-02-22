package com.example.L11_Spring_JDBC_demo.dao;


import com.example.L11_Spring_JDBC_demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class ProductDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${product.query.find.by.id}")
    private String findById;

    @Autowired
    private ProductMapper productMapper;

    public Product getByID(Long id){
        // call DB
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        List<Product> products = namedParameterJdbcTemplate.query(findById,parameterSource, productMapper);
        if(!products.isEmpty()){
            return products.get(0);
        }
        return null;
    }

    public Product create(Product product) {
        String insertQuery = "insert into product (name, cost) values (:name, :cost)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("cost", product.getCost());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertQuery,parameterSource,keyHolder);
        product.setId(keyHolder.getKey().longValue());
        return product;
    }
}
