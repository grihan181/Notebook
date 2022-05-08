package org.example.servlets;

import org.example.DAOClasses.NotebookDao;
import org.example.NotebookClasses.Notebook;
import org.example.NotebookClasses.NotebookDB;
import org.example.modelClasses.Notebooks;
import org.example.modelClasses.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        long userId = Long.parseLong((req.getSession().getAttribute("userId")).toString());
        String row = "";
        String current = "";
        ArrayList<Notebooks> notebooks;

        switch (req.getParameter("selectSearch")) {
            case "searchName"  ->  {
                row = "name";
                current = req.getParameter("searchText");
            }
            case "searchNotes" -> {
                row = "notes";
                current = req.getParameter("searchText");
            }
            case "searchData" -> {
                row = "createdWhen";
                current = req.getParameter("searchText");
            }
            case "Важно" -> {
                row = "important";
                current = "true";
            }
            case "Не важно" -> {
                row = "important";
                current = "false";
            }
            case "Напомнить" -> {
                row = "reminder";
                current = "not null";
            }
            case "Не напоминать" -> {
                row = "reminder";
                current = "null";
            }
            case "", "all" -> {
                resp.sendRedirect(req.getContextPath() + "/main?username=" + req.getSession().getAttribute("username") +
                        "&password=" +  req.getSession().getAttribute("password"));
                return;
            }
        }



        notebooks = ((NotebookDao) req.getServletContext().getAttribute("notebookDao")).search((Users)req.getSession().getAttribute("user"), row, current);
        for(Notebooks notebook : notebooks) {
            if(notebook.getReminder() != null) {
                notebook.setReminder(notebook.getReminder().replace('T', ' '));
            }
        }
        req.setAttribute("notebooks", notebooks);
        req.getRequestDispatcher("/notebook.jsp").forward(req, resp);
    }
}
