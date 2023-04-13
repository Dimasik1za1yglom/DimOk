<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Поиск пользователей</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<form method="post" action="/users">
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
                    <form method="get" action="/user/profile/${user.id}">
                        <input type="submit" value="Посмотреть профиль">
                    </form>
                </td>
            </tr>
        </#list>
    </table>
<#else>
</#if>
<br>
<form method="get" action="/user/myprofile">
    <input type="submit" value="Вернуться на свою страницу.">
</form>
</body>
</html>