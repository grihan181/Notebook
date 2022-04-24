package org.example.servlets;

import org.example.classes.Notebook;
import org.example.classes.NotebookDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@WebServlet(urlPatterns ="/main")
public class IndexNotebookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");


        String username = req.getParameter("username");
        String password = req.getParameter("password");
        long id;

        Connection connection = (Connection)req.getServletContext().getAttribute("dbConnection");
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte b : bytes) {
                builder.append(String.format("%02X", b));
            }

            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM USERS WHERE" +
                    " USERNAME = '" + username +"'" +
                    " AND PASSWORD = '" + builder + "';");
            if (rs.next() ) {
                id = rs.getLong("ID");

                req.getSession().setAttribute("userId", id);
                req.getSession().setAttribute("password", password);
                req.getSession().setAttribute("username", username);

                ArrayList<Notebook> notebooks = NotebookDB.select(id, req);
                ArrayList<String> textAlerts = new ArrayList<>();
                for(Notebook notebook : notebooks) {
                    if(notebook.getReminder() != null) {
                        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime notebookData = LocalDateTime.parse(notebook.getReminder(), DATEFORMATTER);

                        long minutes = ChronoUnit.MINUTES.between(now, notebookData);
                        if(minutes < 60 && minutes > 0) {
                            textAlerts.add("Напоминание о записке: " + notebook.getName() + "  Остался час!");
                        } else if(minutes < 0) {
                            textAlerts.add("Напоминание о записке: "  + notebook.getName() + " Срок истек!");
                        }
                    }
                }
                for(Notebook notebook : notebooks) {
                    if(notebook.getReminder() != null) {
                        notebook.setReminder(notebook.getReminder().replace('T', ' '));
                    }
                }
                req.setAttribute("notebooks", notebooks);
                req.setAttribute("textAlerts", textAlerts);
                req.getRequestDispatcher("/notebook.jsp").forward(req, resp);
            } else {
                req.setAttribute("textError", "Неправильный логин или пароль");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
