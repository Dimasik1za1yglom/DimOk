<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Его посты</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<hr>
<#if posts??>
<table>
    <tr>
        <th>Содержание</th>
        <th>Время создания</th>
        <th>Действия</th>
    </tr>
    <#list posts as post>
        <tr>
            <td>${post.text}</td>
            <td>${post.timeCreation}</td>
            <td>
                <form method="get" action="/post/user/${post.id}/${post.userId}">
                    <input type="submit" value="Посмотреть">
                </form>
            </td>
        </tr>
    </#list>
</table>
<#else>
</#if>
<hr>
<a href="http://localhost:8082/user/profile/${userId}">
    <b style="background:#FFCCCC">Вернуться на cтраницу пользователя.</b></a>
</body>
</html>