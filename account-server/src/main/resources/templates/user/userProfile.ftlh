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
<form method="get" action="/users/search">
    <input type="submit" value="Поиск пользователей">
</form>
<hr>
<form method="get" action="/user/myprofile">
    <input type="submit" value="Вернуться на свою страницу.">
</form>
</body>
</html>