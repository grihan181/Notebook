<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit note</title>
        <style>
            <%@include file='css/createEdit.css' %>
        </style>
    </head>
    <body>
    <jsp:include page = "uppermenu.jsp" />
    <div name = "item" id = "item">
            <h2><center>Изменение заметкии</center></h2>

            <form method="post">
            <input type="hidden" value="${notebook.id}" name="id" />
                <center><input name="name" id = "name" value = "${notebook.name}" placeholder = "Название заметки"/></center><br><br>
                <div class = "input-form">
                     <textarea name="notes" id = "notes" value = "${notebook.notes}" placeholder = "Текст заметки" oninput="this.style.height = (this.scrollHeight - 4) + 'px';">${notebook.notes}</textarea>
                </div>
                 <div id = "buttons">
                    <label>Важно
                        <input type="checkbox" name="important" id = "checkbox" value = "${notebook.important}"/></label>
                    </label>
                    <label>Напомнить <input type="checkbox" id = "checkbox"
                    onclick="if(this.checked){this.nextSibling.style.display=''; }else {this.nextSibling.style.display='none'; this.nextSibling.value=''; }"
                   ><input type="datetime-local"  id="reminder" style="display:none;"
                           name="reminder" value="${notebook.reminder}"
                           min="2021-06-07T00:00" max="2025-06-14T00:00">
                   </label>

                    <input id = "save" type="submit" value="Сохранить" />
                </div>
            </form>
        </div>
    </body>
</html>
