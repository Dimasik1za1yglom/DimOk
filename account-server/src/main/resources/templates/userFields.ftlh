<!doctype html>
<html lang=ru>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Личные данные</title>
</head>
<body>
<br>
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
<br>
<form method="post" action="/user/add">
    <input type="text" name="firstName" placeholder="Имя">
    <input type="text" name="lastName" placeholder="Фамилия">
    <input type="date" name="birthday" placeholder="Дата рождения">
    <input type="text" name="bio" placeholder="О себе">
    <input type="text" name="country" placeholder="Страна">
    <input type="text" name="city" placeholder="Город">
    <input type="tel" name="phone" placeholder="Телефон"
           pattern="\+7\s?[\(]{0,1}9[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}">
    <input type="submit" value="Добавить">
</form>
</body>
</html>