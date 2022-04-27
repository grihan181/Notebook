package org.example.servlets;

import org.apache.log4j.Logger;
import org.example.NotebookClasses.NotebookDB;
import org.example.listener.HttpListener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/delete")
public class DeleteNotebookServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(DeleteNotebookServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            long userId = Long.parseLong((req.getSession().getAttribute("userId")).toString());

            ((NotebookDB) req.getServletContext().getAttribute("notebookBD")).delete(id, userId);
            resp.sendRedirect(req.getContextPath() + "/main?username=" + req.getSession().getAttribute("username") +
                    "&password=" +  req.getSession().getAttribute("password"));

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
