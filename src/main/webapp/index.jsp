<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Notebook</title>
       <style>
          <%@include file='css/registerStyle.css' %>
       </style>
    </head>

    <body>
        <div class = "form">
            <h1>Вход</h1>
            <div class = "error-text" style = "padding: 8px 0; border-radius: 5px; color: #fa0505; text-align: center;">
               ${textError}
            </div>
            <form action="main" method="html">
                <div class = "input-form">
                    <input type = "text" required="required" name = "username" placeholder = "Логин">
                </div>
                <div class = "input-form">
                    <input type = "password" required="required" name = "password" placeholder = "Пароль">
                </div>

                <div class = "input-form">
                    <input type="submit" value="Войти" display: block/>
                </div>

                <a href = "register.jsp" class = "sign_up">Нет аккаунта?</a>
            </form>

        </div>
    </body>
</html>