package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectionManager {
    private final Connection connection;

    public DataBaseConnectionManager(String username,
                                     String password,
                                     String dburl,
                                     String driver)
        throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        this.connection = DriverManager.getConnection(dburl, username, password);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
