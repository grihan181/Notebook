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
import java.security.MessageDigest;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(SignUpServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String usernamesignup = req.getParameter("usernamesignup");
        String emailsignup = req.getParameter("emailsignup");



            if (((NotebookDao) req.getServletContext().getAttribute("notebookDao")).selectOne(usernamesignup, emailsignup) != 0) {
                req.setAttribute("textError", "Такое имя пользователя или почта уже используется");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                return;
            } else {
                try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] bytes = md5.digest(req.getParameter("passwordsignup").getBytes());
                StringBuilder builder = new StringBuilder();
                for (byte b : bytes) {
                    builder.append(String.format("%02X", b));
                }
                    Users user = new Users(usernamesignup, builder.toString(), emailsignup);
                    ((NotebookDao) req.getServletContext().getAttribute("notebookDao")).insert(user);
            } catch(Exception e){
                logger.error(e);
            }
            req.setAttribute("textError", "Поздравляю, вы зарегистрировали аккаунт!");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
