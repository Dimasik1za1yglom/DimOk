<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Пользователи</title>
</head>
<body>
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
<form method="post" action="/admin/users">
    Заполните одно или все поля. <br>
    <input type="text" name="firstName" value="<#if user??>${user.firstName}<#else></#if>" placeholder="Имя">
    <input type="text" name="lastName" value="<#if user??>${user.lastName}<#else></#if>" placeholder="Фамилия">
    <input type="submit" value="Поиск">
</form>
<hr>
<#if users??>
    <table>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Дата рождения</th>
            <th>Действие</th>
        </tr>
        <#list users as user>
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.birthday}</td>
                <td>
                    <form method="get" action="/admin/profile/${user.id}">
                        <input type="submit" value="Посмотреть профиль">
                    </form>
                    <form method="get" action="/requests/${user.id}">
                        <input type="submit" value="Посмотреть историю поиска">
                    </form>
                </td>
            </tr>
        </#list>
    </table>
<#else>
</#if>
</body>
</html>