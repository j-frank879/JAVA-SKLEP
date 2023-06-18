package wipb.ee.jspdemo.web.dao;


import wipb.ee.jspdemo.web.model.Orders;
import wipb.ee.jspdemo.web.util.ConnectionFactory;
import wipb.ee.jspdemo.web.util.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersDao {
    public void save(Orders orders) {
        final String SQL = "insert into \"ORDERS\" values (DEFAULT, ?,?,?,?,?,? )";
        try
                (
                        Connection connection = ConnectionFactory.getConnection();
                        PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ) {
            statement.setLong(1, orders.getCustomerId());
            statement.setString(2, orders.getProductName());
            statement.setLong(3, orders.getProductCount());
            statement.setBigDecimal(4, orders.getTotal());
            statement.setBoolean(5, orders.isPaid());
            statement.setBoolean(6, orders.isCancelled());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    orders.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    public void cancel(Long id) {
        final String SQL = "update \"ORDERS\" set isCancelled = true where id = ?";
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
    public void pay(Long id) {
        final String SQL = "update \"ORDERS\" set isPaid = true where id = ?";
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

    public Optional<Orders> find(Long id) {
        final  String SQL = "select * from \"ORDERS\" where id = ?";
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
                ){
            statement.setLong(1,id);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Orders orders = new Orders();
                    orders.setId(resultSet.getLong("id"));
                    orders.setCustomerId(resultSet.getLong("customerId"));
                    orders.setProductName(resultSet.getString("productName"));
                    orders.setProductCount(resultSet.getInt("productCount"));
                    orders.setTotal(resultSet.getBigDecimal("total"));
                    orders.setPaid(resultSet.getBoolean("isPaid"));
                    orders.setCancelled(resultSet.getBoolean("isCancelled"));

                    return Optional.of(orders);
                }
            }catch (SQLException e){
                throw new DataAccessException(e);
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return Optional.empty();
    }
    public List<Orders> findAll() {
        final String SQL = "select * from \"ORDERS\"";
        List<Orders> result = new ArrayList<>();
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
                ){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result.add(new Orders(
                        resultSet.getLong("id"),
                        resultSet.getLong("customerId"),
                        resultSet.getString("productName"),
                        resultSet.getInt("productCount"),
                        resultSet.getBigDecimal("total"),
                        resultSet.getBoolean("isPaid"),
                        resultSet.getBoolean("isCancelled")
                ));
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return result;
    }
    public List<Orders> findAllNotCancelled() {
        final String SQL = "select * from \"ORDERS\" where isCancelled = 0";
        List<Orders> result = new ArrayList<>();
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result.add(new Orders(
                        resultSet.getLong("id"),
                        resultSet.getLong("customerId"),
                        resultSet.getString("productName"),
                        resultSet.getInt("productCount"),
                        resultSet.getBigDecimal("total"),
                        resultSet.getBoolean("isPaid"),
                        resultSet.getBoolean("isCancelled")
                ));
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return result;
    }
    public List<Orders> findAllByCustomerId(Long customerId) {
        final String SQL = "select * from \"ORDERS\" where customerId = ?";
        List<Orders> result = new ArrayList<>();
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            statement.setLong(1,customerId);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result.add(new Orders(
                        resultSet.getLong("id"),
                        resultSet.getLong("customerId"),
                        resultSet.getString("productName"),
                        resultSet.getInt("productCount"),
                        resultSet.getBigDecimal("total"),
                        resultSet.getBoolean("isPaid"),
                        resultSet.getBoolean("isCancelled")
                ));
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return result;
    }
}
