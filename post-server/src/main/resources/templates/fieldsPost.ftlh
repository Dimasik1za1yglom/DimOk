<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Добавление поста</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<hr>
<form method="post" action="/post/add">
    <input type="text" name="text" value="<#if post??>${post.text}<#else></#if>" placeholder="Содержание">
    <input type="submit" value="Добавить">
</form>
<hr>
<a href="/user/myprofile"> Вернуться на страницу</a>
</body>
</html>