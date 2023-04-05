<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Регистрация</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<form method="post" action="/registration">
    <label class="whitetext">
        Email: <input type="text" name="email" placeholder="Введите ваш E-mail">
    </label>
    <br>
    <label class="whitetext">
        Password: <input type="password" name="password" placeholder="Введите ваш Пароль">
    </label>
    <br>
    <input type="submit" value="Зарегистрироваться">
</form>
<br>
<form method="get" action="/input">
    <input type="submit" value="Страница входа.">
</form>
</body>
</html>