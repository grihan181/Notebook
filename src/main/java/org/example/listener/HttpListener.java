package org.example.listener;

import org.apache.log4j.Logger;
import org.example.DAOClasses.NotebookDao;

import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;


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
