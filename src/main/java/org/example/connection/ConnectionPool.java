package org.example.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance = null;
    final static Logger logger = Logger.getLogger(ConnectionPool.class);

    private  ConnectionPool() { }

    public static synchronized ConnectionPool getInstance() {
        if(instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        Context ctx;
        DataSource dataSource;
        Connection con = null;

        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/notebookDB");

            con = dataSource.getConnection();
        } catch (Exception e) {
            logger.error(e);
        }
        return con;
    }
    public static void closeConnection (Connection connection) throws SQLException {
        connection.close();
    }
}
