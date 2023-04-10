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
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
<hr>
Полное содержание: ${post.text} <br>
Дата создания: ${post.timeCreation}<br>
<form method="post" action="/post/${post.id}/delete">
    <input type="submit" value="Удалить">
</form>
<hr>
<form method="get" action="/post/${post.id}/change">
    <input type="submit" value="Изменить">
</form>
<hr>
<a href="/post">Вернуться к списку постов</a>
</body>
</html>