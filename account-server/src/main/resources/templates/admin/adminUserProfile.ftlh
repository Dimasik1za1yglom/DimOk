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
О себе: ${user.bio} <br>
Страна: ${user.country} <br>
Город: ${user.city} <br>
Мой телефон: ${user.phone} <br>
<hr>
<form method="post" action="/account/admin/${user.id}/delete">
    <input type="submit" value="Удалить профиль">
</form>
<hr>
<form method="get" action="/account/admin/${user.id}/change">
    <input type="submit" value="Изменить профиль">
</form>
<hr>
<a href="http://localhost:8082/search/admin/users/all">
    <b style="background:#FFCCCC">Вернуться ко всем пользователям.</b></a></form>
</body>
</html>