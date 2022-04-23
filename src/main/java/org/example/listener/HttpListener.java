

package org.example.listener;

import org.example.connection.DataBaseConnectionManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


@WebListener
public class HttpListener implements  ServletContextListener {
    private Connection connection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Context initCx = null;
        try {
            initCx = new InitialContext();
        Context envCtx = (Context) initCx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jdbc/notebookDB");


            Connection connection = ds.getConnection();
            context.setAttribute("dbConnection", connection);

            context.setAttribute("dbConnection", connection);

            Statement stat;
            stat = connection.createStatement();
            stat.execute("CREATE TABLE IF NOT EXISTS Users(" +
                    "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "USERNAME VARCHAR(255) NOT NULL," +
                    "PASSWORD VARCHAR(255) NOT NULL," +
                    "EMAIL VARCHAR(255) NOT NULL);" +
                    "");

            stat.execute("CREATE TABLE IF NOT EXISTS NOTEBOOKS(" +
                    "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) NOT NULL," +
                    "NOTES VARCHAR(255) NOT NULL," +
                    "IMPORTANT BOOLEAN DEFAULT FALSE," +
                    "CREATED_WHEN DATETIME NOT NULL," +
                    "REMINDER VARCHAR(255)," +
                    "USERS_ID BIGINT," +
                    "FOREIGN KEY (USERS_ID) REFERENCES Users(ID));");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ((Connection)sce.getServletContext().getAttribute("dbConnection")).close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


