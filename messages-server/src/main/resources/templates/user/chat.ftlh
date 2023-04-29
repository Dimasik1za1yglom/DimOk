<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Диалог</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="js/stomp.js"></script>
    <script type="text/javascript">
        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility
                = connected ? 'visible' : 'hidden';
            document.getElementById('response').innerHTML = '';
        }

        function connect() {
            var socket = new SockJS('/chat');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
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
            console.log("Disconnected");
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
    <div>
        <input type="hidden" id="dialogId" value="${dialogId}" />
        <input type="hidden" id="userId" value="${userId}">
    </div>
    <br />
    <div id="conversationDiv">
        <p id="response"></p>
        <input type="text" id="text" placeholder="Write a message..."/>
        <button id="sendMessage" onclick="sendMessage();">Отправить</button>
    </div>
</div>
<hr>
<a href="http://localhost:8082/chat/${dialogId}" onclick="disconnect()">
    <b style="background:#FFCCCC">Вернуться к своим диалогам.</b></a>
</body>
</html>