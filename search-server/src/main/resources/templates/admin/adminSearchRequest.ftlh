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
<h2 style="color:red"><#if error??>${error}<#else></#if></h2>
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
<form method="post" action="/admin/requests/${searchRequest.userId}/delete">
    <input type="submit" value="Удалить историю поиска данного пользователя">
</form>
<hr>
</body>
</html>