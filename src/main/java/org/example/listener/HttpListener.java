package org.example.listener;

import org.apache.log4j.Logger;
import org.example.DAOClasses.NotebookDao;
import org.example.NotebookClasses.NotebookDB;
import org.example.connection.ConnectionPool;

import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class HttpListener implements ServletContextListener {
    final static Logger logger = Logger.getLogger(HttpListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            NotebookDao notebookDao = NotebookDao.getInstance();
            sce.getServletContext().setAttribute("notebookDao", notebookDao);

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
