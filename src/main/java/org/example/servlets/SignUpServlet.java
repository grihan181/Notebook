package org.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Connection connection = (Connection)req.getServletContext().getAttribute("dbConnection");
        try {
            Statement stat = connection.createStatement();
            stat.execute("INSERT INTO USERS (USERNAME, PASSWORD, EMAIL)  " +
                    "values('" + req.getParameter("usernamesignup") +
                    "', '" + req.getParameter("passwordsignup") +
                    "', '" + req.getParameter("emailsignup") + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("textError", "Поздравляю, вы зарегистрировали аккаунт!");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }
}
