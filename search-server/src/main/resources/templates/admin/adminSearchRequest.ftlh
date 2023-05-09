<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>История запросов</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<hr>
<table>
    <tr>
        <th>Текст запроса</th>
        <th>Время создания запроса</th>
    </tr>
    <#list searchRequests as searchRequest>
        <tr>
            <td>${searchRequest.textRequest}</td>
            <td>${searchRequest.timeCreation}</td>
        </tr>
    </#list>
</table>
<hr>
<form method="post" action="/search/admin/requests/${userId}/delete">
    <input type="submit" value="Удалить историю поиска данного пользователя">
</form>
<hr>
<form method="get" action="/search/admin/users/all">
    <input type="submit" value="Вернуться к списку пользователекй">
</form>
</body>
</html>