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
 <section>
        <div class = "form">
            <form  action="signup" method = "post" autocomplete="on">
                <h1>Регистрация</h1>
                <div class = "error-text">Введенные пароли не совпадают</div>

                <div class = "input-form">
                    <input name="usernamesignup" required="required" type="text" placeholder="Введите ваш логин" />
                </div>
                <div class = "input-form">
                    <input name="emailsignup" required="required" type="email" placeholder="Введите вашу почту"/>
                </div>
                <div class = "input-form">
                    <input id = "passwordsignup"  name="passwordsignup" required="required" type="password" placeholder="Введите ваш пароль"/>
                </div>
                <div class = "input-form">
                    <input id = "passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="Подтвердите пароль"/>
                </div>
                <div class = "input-form">
                    <input class = "button" type = "submit" value = "Зарегистрироваться"/>
                </div>


                <a href = "index.jsp" class = "sign_in">Уже есть аккаунт?</a>
            </form>

            <script>
               var password = document.getElementById("passwordsignup")
                 , confirm_password = document.getElementById("passwordsignup_confirm");

               function validatePassword(){
                 if(password.value != confirm_password.value) {
                   confirm_password.setCustomValidity("Пароли не совпадают");
                 } else {
                   confirm_password.setCustomValidity('');
                 }
               }

               password.onchange = validatePassword;
               confirm_password.onkeyup = validatePassword;
            </script>
        </div>
        </div>
    </section>
    </body>
</html>