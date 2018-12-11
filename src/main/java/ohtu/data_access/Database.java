package ohtu.data_access;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection getConnection() throws SQLException;
    
    void initializeSqlTables();
}
