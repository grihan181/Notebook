/*
package org.example.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebFilter("/main")
public class SignInFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        Connection connection = (Connection)servletRequest.getServletContext().getAttribute("dbConnection");
        try {
            Statement stat = connection.createStatement();
           ResultSet rs = stat.executeQuery("SELECT * FROM USERS WHERE" +
                   " USERNAME = '" + username +"'" +
                   " AND PASSWORD = '" + password + "';");
           if (rs.next() ) {
                long id = rs.getLong("ID");

                servletRequest.setAttribute("userId", id);
                filterChain.doFilter(servletRequest, servletResponse);

           } else {
               servletRequest.setAttribute("textError", "Неправильный логин или пароль");

               filterConfig.getServletContext().getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);

           }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void destroy() {
        Filter.super.destroy();
        filterConfig = null;
    }
}
*/
