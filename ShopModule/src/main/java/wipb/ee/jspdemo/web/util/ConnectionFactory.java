package wipb.ee.jspdemo.web.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    public static HikariConfig config;
    private static HikariDataSource ds;
    static{
        config = new HikariConfig("/hikari.properties");
        ds = new HikariDataSource(config);
    }
    public static Connection getConnection(){
        try{
            return ds.getConnection();
        } catch (SQLException e){
            throw new DataAccessException(e);
        }
    }
}
