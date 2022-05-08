package org.example.servlets;

import org.apache.log4j.Logger;
import org.example.DAOClasses.NotebookDao;
import org.example.modelClasses.Users;

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

            ((NotebookDao) req.getServletContext().getAttribute("notebookDao")).delete(id ,(Users)req.getSession().getAttribute("user"));
            resp.sendRedirect(req.getContextPath() + "/main?username=" + req.getSession().getAttribute("username") +
                    "&password=" +  req.getSession().getAttribute("password"));

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
