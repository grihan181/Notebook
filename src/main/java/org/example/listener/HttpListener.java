package org.example.listener;

import org.example.NotebookClasses.NotebookDB;
import org.example.connection.ConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;


@WebListener
public class HttpListener implements  ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            NotebookDB notebookDB = NotebookDB.getInstance();

            sce.getServletContext().setAttribute("notebookBD", notebookDB);
            Connection connection = ConnectionPool.getInstance().getConnection();
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
            ConnectionPool.closeConnection(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



