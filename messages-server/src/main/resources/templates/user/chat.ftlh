<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Диалог</title>
    <script type="text/javascript" src="https://unpkg.com/kd-shim-sockjs-client@0.3.4/sockjs-0.3.4.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script type="text/javascript">
        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('conversationDiv').style.visibility
                = connected ? 'visible' : 'hidden';
            document.getElementById('response').innerHTML = '';
        }

        function connect() {
            var socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                stompClient.subscribe('/topic/messages', function(messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
        }

        function disconnect() {
            if(stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
        }

        function sendMessage() {
            var dialogId = document.getElementById('dialogId').value;
            var text = document.getElementById('text').value;
            var userId = document.getElementById('userId').value;
            stompClient.send("/app/chat", {},
                JSON.stringify({'dialogId':dialogId, 'textMessage':text, 'userId':userId}));
        }

        function showMessageOutput(messageOutput) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(messageOutput.userId + ": "
                + messageOutput.textMessage + " (" + messageOutput.timeCreation + ")"));
            response.appendChild(p);
        }
    </script>
</head>
<body onload="connect()">
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
<div>
    <br>
    <div id="conversationDiv">
        <input type="text" id="text" placeholder="Write a message..."/>
        <input type="hidden" id="dialogId" value="${dialogId}" />
        <input type="hidden" id="userId" value="${userId}">
        <button id="sendMessage" onclick="sendMessage();">Отправитьь</button>
        <p id="response"></p>
    </div>
</div>
<hr>
<a href="http://localhost:8082/user/dialog/my/${userId}" onclick="disconnect()">
    <b style="background:#FFCCCC">Вернуться к своим диалогам.</b></a>
</body>
</html>