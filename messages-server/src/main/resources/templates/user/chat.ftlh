<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Диалог</title>
</head>
<body>
<#if errors??>
<#list errors as error>
    <h2 style="color:red">${error}</h2>
</#list>
<#else>
</#if>
<hr>
<#list messages as message>
    ${message.textMessage}<br>
</#list>
<hr>
<a href="http://localhost:8082/chat/${dialogId}">
    <b style="background:#FFCCCC">Вернуться к своим диалогам.</b></a>
</body>
</html>