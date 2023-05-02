<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Профиль пользователя</title>
</head>
<body>
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
<hr>
Имя: ${user.firstName} <br>
Фамилия: ${user.lastName} <br>
Дата рождения: ${user.birthday} <br>
О себе: <#if (user.bio)??>${user.bio}<#else></#if> <br>
Страна: <#if (user.country)??>${user.country}<#else></#if> <br>
Город: <#if (user.city)??>${user.city}<#else></#if> <br>
Мой телефон: ${user.phone} <br>
<hr>
<a href="http://localhost:8082/search"><b style="background:#FFCCCC">Поиск пользователей.</b></a>
<hr>
<a href="http://localhost:8082/post/user/all/${user.id}"><b style="background:#FFCCCC">Его посты.</b></a>
<hr>
<#if createDialog??>
    <form method="post" action="http://localhost:8082/user/dialog/add/${user.id}">
        <input type="hidden" name="firstName" value="${user.firstName}">
        <input type="hidden" name="lastName" value="${user.lastName}">
        <input type="submit" value="Создать диалог.">
    </form>
<#else>
    <a href="http://localhost:8082/chat/${dialogId}">
        <b style="background:#FFCCCC">Перейти к диалогу</b></a>
</#if>
<hr>
<form method="get" action="/user/myprofile">
    <input type="submit" value="Вернуться на свою страницу.">
</form>
</body>
</html>