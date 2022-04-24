package org.example.servlets;

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

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String usernamesignup = req.getParameter("usernamesignup");
        String emailsignup = req.getParameter("emailsignup");
        try {
        Connection connection = (Connection)req.getServletContext().getAttribute("dbConnection");
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM USERS WHERE" +
                " (USERNAME = '" + usernamesignup +"')" +
                " OR (EMAIL = '" + emailsignup + "');");
        if(rs.next()) {
            req.setAttribute("textError", "Такое имя пользователя или почта уже используется");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(req.getParameter("passwordsignup").getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte b : bytes) {
                builder.append(String.format("%02X", b));
            }

            stat.execute("INSERT INTO USERS (USERNAME, PASSWORD, EMAIL)  " +
                    "values('" + usernamesignup +
                    "', '" + builder +
                    "', '" + emailsignup + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("textError", "Поздравляю, вы зарегистрировали аккаунт!");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }
}
