<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create note</title>
        <style>
            <%@include file='css/createEdit.css' %>
        </style>
    </head>
    <body>
    <jsp:include page = "uppermenu.jsp" />
    <div name = "item" id = "item">
        <h2><center>Создание новой заметки</center></h2>
        <form method="post">
            <center><input name="name" id = "name" placeholder = "Название заметки" /></center><br><br>
             <div class = "input-form">
                <textarea cols="10" type = "text" name="notes" id = "notes" placeholder = "Текст заметки" oninput="this.style.height = (this.scrollHeight - 4) + 'px';"></textarea>
             </div>
            <div id = "buttons">
                <label>Важно
                    <input type="checkbox" id = "checkbox" name="important" value = "true"/>
                </label>

                <label>Напомнить <input type="checkbox" id = "checkbox"
                onclick="if(this.checked){this.nextSibling.style.display='';} else {this.nextSibling.style.display='none'; this.nextSibling.value=''; }"
                ><input type="datetime-local"  name="reminder" id="reminder" style="display:none; "
                       min="2021-06-07T00:00" max="2025-06-14T00:00">
                </label>
                <input id = "save" type="submit" value="Сохранить" />
            </div>
        </form>
 </div>
    </body>
</html>
