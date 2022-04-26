package org.example.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance = null;

    private  ConnectionPool() { }

    public static synchronized ConnectionPool getInstance() {
        if(instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        Context ctx = null;
        DataSource dataSource = null;
        Connection con = null;

        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/notebookDB");

            con = dataSource.getConnection();
            System.out.println("connection have been get...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    public static void closeConnection (Connection connection) throws SQLException {
        connection.close();
    }
}
