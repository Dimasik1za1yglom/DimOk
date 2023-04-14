<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мои посты</title>
</head>
<body>
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
<hr>
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
                <form method="get" action="/post/${post.id}">
                    <input type="submit" value="Посмотреть">
                </form>
            </td>
        </tr>
    </#list>
</table>
<hr>
<a href="/user/myprofile"> Вернуться на страницу</a>
</body>
</html>