package wipb.ee.jspdemo.web.dao;


import wipb.ee.jspdemo.web.model.Product;

import wipb.ee.jspdemo.web.util.ConnectionFactory;
import wipb.ee.jspdemo.web.util.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {
    public void save(Product product) {
        final String SQL = "insert into \"PRODUCT\" values (DEFAULT, ?,?)";
        try
                (
                        Connection connection = ConnectionFactory.getConnection();
                        PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(Product product) {
        final String SQL = "update \"PRODUCT\" set name = ?, price = ? where id = ?";
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1,product.getName());
            statement.setBigDecimal(2,product.getPrice());
            statement.setLong(3,product.getId());

            statement.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
    }

    public void delete(Product product) {
        final String SQL = "delete \"PRODUCT\" where id = ?";
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            statement.setLong(1,product.getId());

            statement.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
    }
    public void delete(Long id) {
        final String SQL = "delete \"PRODUCT\" where id = ?";
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            statement.setLong(1,id);

            statement.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
    }

    public Optional<Product> find(Long id) {
        final  String SQL = "select * from \"PRODUCT\" where id = ?";
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
                ){
            statement.setLong(1,id);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Product product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    return Optional.of(product);
                }
            }catch (SQLException e){
                throw new DataAccessException(e);
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return Optional.empty();
    }
    public Optional<Product> find(String name) {
        final  String SQL = "select * from \"PRODUCT\" where name = ?";
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Product product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    return Optional.of(product);
                }
            }catch (SQLException e){
                throw new DataAccessException(e);
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        final String SQL = "select * from \"PRODUCT\"";
        List<Product> result = new ArrayList<>();
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
                ){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result.add(new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price")
                ));
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return result;
    }
    public void truncate(){

    }

}
