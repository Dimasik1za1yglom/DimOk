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
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<hr>
<form method="post" action="/search/admin/users">
    Заполните одно или все поля. <br>
    <input type="text" name="firstName" value="<#if request??>${request.firstName}<#else></#if>" placeholder="Имя">
    <input type="text" name="lastName" value="<#if request??>${request.lastName}<#else></#if>" placeholder="Фамилия">
    <input type="submit" value="Поиск">
</form>
<hr>
<form method="get" action="/search/admin/users/all">
    <input type="submit" value="Получить всех пользователей">
</form>
<hr>
<#if users??>
    <table>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Дата рождения</th>
            <th>Роль</th>
            <th>Действие</th>
        </tr>
        <#list users as user>
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.birthday}</td>
                <td>${user.role.name}</td>
                <td>
                    <a href="http://localhost:8082/account/admin/profile/${user.id}">
                        <b style="background:#FFCCCC">Посмотреть страницу пользователя.</b></a>
                    <form method="get" action="/search/admin/requests/${user.id}">
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