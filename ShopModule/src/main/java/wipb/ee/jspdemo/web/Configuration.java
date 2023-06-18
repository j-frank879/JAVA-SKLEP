package wipb.ee.jspdemo.web;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;

@DataSourceDefinition(
    name = "java:global/DemoDataSource",
    className = "org.h2.jdbcx.JdbcDataSource",
    url = "jdbc:h2:mem:test2;DB_CLOSE_DELAY=-1",
    minPoolSize = 1,
    initialPoolSize = 1,
    user = "sa",
    password = ""
)
@Singleton
public class Configuration {
}
