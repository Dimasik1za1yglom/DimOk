<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мой постик</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<hr>
<form method="post" action="/post/my/${post.id}/update/${post.userId}">
    <textarea name="text" cols="60" rows="10" placeholder="Текст Поста">${post.text}</textarea>
    <input type="submit" value="Изменить постик">
</form>
<hr>
<form method="get" action="/post/my/${post.id}/${post.userId}">
    <input type="submit" value="Вернуться к посту">
</form>
</body>
</html>