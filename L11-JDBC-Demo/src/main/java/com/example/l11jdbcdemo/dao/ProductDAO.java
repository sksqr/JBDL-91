package com.example.l11jdbcdemo.dao;

import com.example.l11jdbcdemo.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class ProductDAO {

    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    public Product getByID(Long id){
        // call DB
        Product product = null;
        String query = "Select id, name, cost from product where id="+id;
        try(Connection connection = DriverManager.getConnection(dbUrl,username,password)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                product = new Product(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getDouble("cost"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

//    public Product create(Product product) {
//        try (Connection con = DriverManager.getConnection(dbUrl,username,password)){
//            String sqlInsert = "insert into product values (null,'"+product.getName()+"',"+product.getCost()+")";
//            Statement statement = con.createStatement();
//            int affectedRow = statement.executeUpdate(sqlInsert,Statement.RETURN_GENERATED_KEYS);
//            if(affectedRow ==0){
//                throw new SQLException("Creation Failed");
//            }
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if(generatedKeys.next()){
//                product.setId(generatedKeys.getLong(1));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return product;
//    }

    public Product create(Product product) {
        try (Connection con = DriverManager.getConnection(dbUrl,username,password)){
            String sqlInsert = "insert into product values (null,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2,product.getCost());
            int affectedRow = preparedStatement.executeUpdate();
            if(affectedRow ==0){
                throw new SQLException("Creation Failed");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                product.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return product;
    }
}
