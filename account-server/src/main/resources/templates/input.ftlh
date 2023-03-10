<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Авторизация</title>
</head>
<body>
<div style="text-align: center;">
    <h2>Ресторан с доставкой</h2>
    <hr>
    <h3>Авторизация</h3>
    <h2 style="color:red"><#if error??>${error}<#else></#if></h2>
    <form method="post" action="/input">
        <label class="whitetext">
            Login: <input  type="email" name="email" placeholder="Email">
        </label>
        <br>
        <label class="whitetext">
            Password: <input type="password" name="password" placeholder="Password">
        </label>
        <br>
        <input type="submit" value="Войти">
    </form>
</div>
<form method="get" action="/registration">
    <input type="submit" value="Страница регистарции.">
</form>
</body>
</html>