<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Notebook</title>
        <style>
            <%@include file='css/main.css' %>
        </style>
        <link  href="http://fonts.googleapis.com/css?family=Neucha&subset=cyrillic"
             rel="stylesheet" type="text/css" >
    </head>

    <body>
        <jsp:include page = "uppermenu.jsp" />
            <div class="searchDiv">

                <form action = '<c:url value = "/search" />'  style = "display:inline;">
                   <div class = "error-text" style = "padding: 8px 0; border-radius: 5px;  color: #fa0505; text-align: center;">
                        ${textError}
                   </div>
                   <select name="selectSearch" id = "selectSearch">
                      <option selected value="searchName">По названию</option>
                      <option value="searchImportant">Статусу важное(Важно/Не важно)</option>
                      <option value="searchNotes">Поиск по содержимому</option>
                      <option value="searchReminder">Напомнить(Напомнить/Не напоминать)</option>
                      <option value="searchData">По дате и времени записи
                      Пример(YYYY-MM-DD HH:MM::SS)</option>
                    </select>
                    <br>
                     <nav>
                        <input name = "searchText" id = "searchText" type = "search" placeholder = "Искать запись здесь...(запрос Like)">
                     <nav>
               </form>
            </div>
            <br>
            <c:forEach var = "textAlert" items = "${textAlerts}">
                <div class="alert">
                  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                  <center>${textAlert} </center>
                </div>
            </c:forEach>

            <script>
            var close = document.getElementsByClassName("closebtn");
            var i;
            for (i = 0; i < close.length; i++) {
                close[i].onclick = function(){
                    var div = this.parentElement;
                    div.style.opacity = "0";
                    setTimeout(function(){ div.style.display = "none"; }, 600);
                }
            }
            </script>

             <p id="makeText"><center><a href ='<c:url value = "/create" />' type = "submit"
             style="color: #6e2c0f; font-size: 30; text-decoration: none;" >
                Тапни по мне, чтобы создать новую запись
             </center></a></p>


        <br>
        <br>
            <ul name = "item" id = "item">
             <c:forEach var = "notebook" items = "${notebooks}">
            <li class = "list">
                 <div class ="divClose">
                 <a id = "newNote" href = '<c:url value = "/edit?id=${notebook.id}"/>' class = "note">
                     <form method = "post" action = '<c:url value = "/delete" />' style = "display:inline;">
                        <input type = "hidden" name = "id" value = "${notebook.id}">
                        <input name = "userId" type = "hidden" value = ${userId}>

                        <input type = "submit"
                        style = "display:inline; background: transparent; border: 0px; cursor: pointer; position: absolute; right:5;"
                        value = "&#10006;">
                     </form>

                 <h2 class = "notebookName"><center>${notebook.name}</center></h2>
                 <p>${notebook.notes}</p>
                 <p class = "importantVisible" >Важно: ${notebook.important}</p>
                 <input type = "hidden" class = "important" value = "${notebook.important}"></input>
                 <p>Дата создания: ${notebook.createdWhen}</p>
                 <p>Надо сделать к: ${notebook.reminder}</p>

                 </a>
             </div>
             </li>
             </c:forEach>
        </ul>
            <script>
                 let reminder = document.getElementsByClassName("important");
                 let element = document.getElementsByClassName("notebookName");
                 let importantVisible = document.getElementsByClassName("importantVisible");
                 for(let i = 0; i < reminder.length; i++){
                 console.log(reminder[i].value);
                    if(reminder[i].value == "true") {
                        element[i].style.color = "red";
                        importantVisible[i].style.color = "red";
                    }
                }
            </script>
    </body>
</html>