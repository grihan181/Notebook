package org.example.servlets;

import org.example.classes.Notebook;
import org.example.classes.NotebookDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/edit")
public class EditNotebookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        long id = Long.parseLong(req.getParameter("id"));
        long userId = Long.parseLong((req.getSession().getAttribute("userId")).toString());

        Notebook notebook = NotebookDB.selectOne(id, userId);
        if(notebook != null) {
            req.setAttribute("notebook", notebook);
            getServletContext().getRequestDispatcher("/edit.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String notes = req.getParameter("notes");
        boolean important;
        important = req.getParameter("important") != null;
        String createdWhen = req.getParameter("createdWhen");

        String reminded;
        if(req.getParameter("reminder").equals("")) {
            reminded = null;
        } else  {
            reminded = req.getParameter("reminder");
        }

        Notebook notebook = new Notebook(id, name, notes, important, createdWhen, reminded, Long.parseLong((req.getSession().getAttribute("userId")).toString()));
        NotebookDB.update(notebook);

        resp.sendRedirect(req.getContextPath() + "/main?username=" + req.getSession().getAttribute("username") +
                "&password=" +  req.getSession().getAttribute("password"));

    }
}
