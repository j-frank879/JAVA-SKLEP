package wipb.ee.jspdemo.web.dao;

import wipb.ee.jspdemo.web.model.Product;
import wipb.ee.jspdemo.web.model.User;
import wipb.ee.jspdemo.web.util.ConnectionFactory;
import wipb.ee.jspdemo.web.util.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    public void save(User user) {
        final String SQL = "insert into \"USERS\" values (DEFAULT, ?,?,'customer',?,?,?)";

        try
                (
                        Connection connection = ConnectionFactory.getConnection();
                        PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                ) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setBigDecimal(5, user.getBalance());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void updatePassword(User user) {
        final String SQL = "update \"USERS\" set password = ? where id = ?";
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1,user.getPassword());
            statement.setLong(2,user.getId());

            statement.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
    }

    public void delete(Long id) {
        final String SQL = "delete \"USERS\" where id = ?";
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

    public Optional<User> findByLoginPassword(String login,String password) {
        final  String SQL = "select * from \"USERS\" where login = ? and password=?";
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            statement.setString(1,login);
            statement.setString(2,password);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setName(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setBalance(resultSet.getBigDecimal("balance"));
                    return Optional.of(user);
                }
            }catch (SQLException e){
                throw new DataAccessException(e);
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return Optional.empty();
    }


    public List<User> findAll() {
        final String SQL = "select * from \"USERS\"";
        List<User> result = new ArrayList<>();
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result.add(new User(resultSet.getLong("id"),resultSet.getString("login"),
                        resultSet.getString("password"),resultSet.getString("role"),
                        resultSet.getString("name"),resultSet.getString("email"),
                        resultSet.getBigDecimal("balance")

                ));
            }
        }catch (SQLException e){
            throw new DataAccessException(e);
        }
        return result;
    }
}
