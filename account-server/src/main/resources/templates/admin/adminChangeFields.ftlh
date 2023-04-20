<!doctype html>
<html lang=ru>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Изменить данные пользователя</title>
</head>
<body>
<br>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<br>
<form method="post" action="/account/admin/${user.id}/update">
    <input type="text" name="firstName" value="<#if user??>${user.firstName}<#else></#if>" placeholder="Имя">
    <input type="text" name="lastName" value="<#if user??>${user.lastName}<#else></#if>" placeholder="Фамилия">
    <input type="date" name="birthday" value="<#if (user.birthday)??>${user.birthday}<#else></#if>" placeholder="Дата рождения">
    <input type="text" name="bio" value="<#if user??>${user.bio}<#else></#if>" placeholder="О себе">
    <input type="text" name="country" value="<#if user??>${user.country}<#else></#if>" placeholder="Страна">
    <input type="text" name="city" value="<#if user??>${user.city}<#else></#if>" placeholder="Город">
    <input type="text" name="phone" value="<#if user??>${user.phone}<#else></#if>" placeholder="Телефон">
    <input type="submit" value="Изменить">
</form>
</body>
</html>