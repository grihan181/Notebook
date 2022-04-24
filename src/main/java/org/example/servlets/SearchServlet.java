package org.example.servlets;

import org.example.classes.Notebook;
import org.example.classes.NotebookDB;

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
        ArrayList<Notebook> notebooks;

        switch (req.getParameter("selectSearch")) {
            case "searchName"  ->  {
                row = "NAME";
                current = req.getParameter("searchText");
            }
            case "searchNotes" -> {
                row = "NOTES";
                current = req.getParameter("searchText");
            }
            case "searchData" -> {
                row = "CREATED_WHEN";
                current = req.getParameter("searchText");
            }
            case "Важно" -> {
                row = "IMPORTANT";
                current = "true";
            }
            case "Не важно" -> {
                row = "IMPORTANT";
                current = "false";
            }
            case "Напомнить" -> {
                row = "REMINDER";
                current = "NOT NULL";
            }
            case "Не напоминать" -> {
                row = "REMINDER";
                current = "NULL";
            }
            case "all" -> {
                notebooks = NotebookDB.select(userId, req);
                req.setAttribute("notebooks", notebooks);
                req.getRequestDispatcher("/notebook.jsp").forward(req, resp);
                return;
            }
        }



        notebooks = NotebookDB.search(userId, req, row, current);
        for(Notebook notebook : notebooks) {
            if(notebook.getReminder() != null) {
                notebook.setReminder(notebook.getReminder().replace('T', ' '));
            }
        }
        req.setAttribute("notebooks", notebooks);
        req.getRequestDispatcher("/notebook.jsp").forward(req, resp);
    }
}
