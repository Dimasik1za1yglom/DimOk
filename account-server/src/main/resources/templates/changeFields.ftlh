<!doctype html>
<html lang=ru>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Изменить данные</title>
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
<form method="post" action="/user/update">
    <input type="text" name="firstName" value="<#if user??>${user.firstName}<#else></#if>" placeholder="Имя">
    <input type="text" name="lastName" value="<#if user??>${user.lastName}<#else></#if>" placeholder="Фамилия">
    <input type="date" name="birthday" value="<#if user??>${user.birthday}<#else></#if>" placeholder="Дата рождения">
    <input type="text" name="bio" value="<#if user??>${user.bio}<#else></#if>" placeholder="О себе">
    <input type="text" name="country" value="<#if user??>${user.country}<#else></#if>" placeholder="Страна">
    <input type="text" name="city" value="<#if user??>${user.city}<#else></#if>" placeholder="Город">
    <input type="tel" name="phone" value="<#if user??>${user.phone}<#else></#if>" placeholder="Телефон"
           pattern="\+7\s?[\(]{0,1}9[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}">
    <input type="submit" value="Добавить">
</form>
</body>
</html>