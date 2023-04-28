<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мои диалоги</title>
</head>
<body>
<#if errors??>
    <#list errors as error>
        <h2 style="color:red">${error}</h2>
    </#list>
<#else>
</#if>
<hr>
<#if dialogs??>
    <table>
        <tr>
            <th>Кто</th>
            <th>Действия</th>
        </tr>
        <#list dialogs as dialog>
            <tr>
                <td>${dialog.name}</td>
                <td>
                    <form method="get" action="/chat/${dialod.dialogId}">
                        <input type="submit" value="Перейти к диалогу">
                    </form>
                </td>
            </tr>
        </#list>
    </table>
<#else>
</#if>
<hr>
<a href="http://localhost:8082/user/myprofile">
    <b style="background:#FFCCCC">Вернуться на свою страницу.</b></a>
</body>
</html>