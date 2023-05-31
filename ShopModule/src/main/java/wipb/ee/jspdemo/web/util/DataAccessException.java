package wipb.ee.jspdemo.web.util;

import java.sql.SQLException;

public class DataAccessException extends RuntimeException {
    public DataAccessException(){

    }
    public DataAccessException(Throwable e) {
        super(e);
    }
}
