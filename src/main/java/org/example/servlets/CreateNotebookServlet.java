package org.example.servlets;


import org.example.NotebookClasses.Notebook;
import org.example.NotebookClasses.NotebookDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/create")
public class CreateNotebookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        req.getRequestDispatcher("/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        try {
            String name = req.getParameter("name");
            String notes = req.getParameter("notes");
            boolean important = req.getParameter("important") != null;
            String createdWhen = req.getParameter("createdWhen");


            String reminded;
            if(req.getParameter("reminder").equals("")) {
                reminded = null;
            } else  {
                reminded = req.getParameter("reminder");
            }
            Notebook notebook = new Notebook(name, notes, important, createdWhen, reminded, Long.parseLong((req.getSession().getAttribute("userId")).toString()));
            ((NotebookDB) req.getServletContext().getAttribute("notebookBD")).insert(notebook);

            resp.sendRedirect(req.getContextPath() + "/main?username=" + req.getSession().getAttribute("username") +
                    "&password=" +  req.getSession().getAttribute("password"));
        } catch (Exception e) {
            req.getRequestDispatcher("/create.jsp").forward(req, resp);
        }
    }
}
