<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Notebook</title>
       <style>
          <%@include file='css/upperMenu.css' %>
       </style>
    </head>

    <body>
       <nav class="menu">
         <ul>
           <li><div>Вы вошли под аккаунтом: ${username}</div></li>
           <li><a href="index.jsp">Выйти</a></li>
         </ul>
       </nav>
    </body>
</html>