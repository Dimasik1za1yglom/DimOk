<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мой профиль</title>
</head>
<body>
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
<hr>
<a href="http://localhost:8082/search"><b style="background:#FFCCCC">Поиск пользователей.</b></a>
<hr>
Имя: ${user.firstName} <br>
Фамилия: ${user.lastName} <br>
Дата рождения: ${user.birthday} <br>
О себе: ${user.bio} <br>
Страна: ${user.country} <br>
Город: ${user.city} <br>
Мой телефон: ${user.phone} <br>
<hr>
<a href="http://localhost:8082/post/${user.id}"><b style="background:#FFCCCC">Мои посты.</b></a>
<hr>
<form method="get" action="/user/change">
    <input type="submit" value="Изменить аккаунт">
</form>
<hr>
<form method="post" action="/user/${user.id}/delete">
    <input type="submit" value="Удалить аккаунт:(">
</form>
<hr>
<form method="get" action="/user/logout">
    <input type="submit" value="Выйти из аккаунта">
</form>
</body>
</html>