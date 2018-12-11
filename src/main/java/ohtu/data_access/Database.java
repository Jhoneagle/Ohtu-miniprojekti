package ohtu.data_access;

import java.sql.Connection;

public interface Database {
    Connection getConnection();
    
    void initializeSqlTables();
}
