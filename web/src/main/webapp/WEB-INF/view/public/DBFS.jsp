<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
    <head>
        <meta charset="UTF-8">
        <title>
            Medrag Logistics
        </title>
        <meta name="viewpoint" content="width=device-width, initial-scale=1">
        <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    </head>
<body>
  <div class="container">
     <div class="jumbotron" style="margin-top: 20px;">
        <div class="row justify-content-center align-items-center">
            <div class="text text-primary">
                <h1>Medrag</h1>
            </div>
            <div class="text text-success">
                <h1>Logistics</h1>
            </div>
        </div>
        <div class="text text-center">
            Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo. Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos, qui ratione voluptatem sequi nesciunt, neque porro quisquam est, qui dolorem ipsum, quia dolor sit, amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt, ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit, qui in ea voluptate velit esse, quam nihil molestiae consequatur, vel illum, qui dolorem eum fugiat, quo voluptas nulla pariatur? At vero eos et accusamus et iusto odio dignissimos ducimus, qui blanditiis praesentium voluptatum deleniti atque corrupti, quos dolores et quas molestias excepturi sint, obcaecati cupiditate non provident, similique sunt in culpa, qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio, cumque nihil impedit, quo minus id, quod maxime placeat, facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet, ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.
       </div>
         <br>
         <div  class="text-primary text-right">
             <a href="${contextPath}/title">To the title page.</a>
         </div>
     </div>
     <div class="footer">
         <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
     </div>
 </div>
</body>
</html>

<%--<html>--%>
<%--<head>--%>
    <%--<title>Chat WebSocket</title>--%>
    <%--<script src="/resources/js/sockjs.js"></script>--%>
    <%--<script src="/resources/js/stomp.js"></script>--%>
    <%--<script type="text/javascript">--%>
        <%--var stompClient = null;--%>

        <%--function setConnected(connected) {--%>
            <%--document.getElementById('connect').disabled = connected;--%>
            <%--document.getElementById('disconnect').disabled = !connected;--%>
            <%--document.getElementById('conversationDiv').style.visibility--%>
                <%--= connected ? 'visible' : 'hidden';--%>
            <%--document.getElementById('response').innerHTML = '';--%>
        <%--}--%>

        <%--function connect() {--%>
            <%--var socket = new SockJS('/changes');--%>
            <%--stompClient = Stomp.over(socket);--%>
            <%--stompClient.connect({}, function(frame) {--%>
                <%--setConnected(true);--%>
                <%--console.log('Connected: ' + frame);--%>
                <%--stompClient.subscribe('/drivers/messages', function(messageOutput) {--%>
                    <%--showMessageOutput(messageOutput);--%>
                <%--});--%>
            <%--});--%>
        <%--}--%>

        <%--function disconnect() {--%>
            <%--if(stompClient != null) {--%>
                <%--stompClient.disconnect();--%>
            <%--}--%>
            <%--setConnected(false);--%>
            <%--console.log("Disconnected");--%>
        <%--}--%>

        <%--function sendMessage() {--%>
            <%--var from = document.getElementById('from').value;--%>
            <%--var text = document.getElementById('text').value;--%>
            <%--stompClient.send("/medrag/changes", {},--%>
                <%--JSON.stringify({'from':from, 'text':text}));--%>
        <%--}--%>

        <%--function showMessageOutput(messageOutput) {--%>
            <%--var response = document.getElementById('response');--%>
            <%--var p = document.createElement('p');--%>
            <%--p.style.wordWrap = 'break-word';--%>
            <%--p.appendChild(document.createTextNode(messageOutput));--%>
            <%--response.appendChild(p);--%>
        <%--}--%>
    <%--</script>--%>
<%--</head>--%>
<%--<body onload="disconnect()">--%>
<%--<div>--%>
    <%--<div>--%>
        <%--<input type="text" id="from" placeholder="Choose a nickname"/>--%>
    <%--</div>--%>
    <%--<br />--%>
    <%--<div>--%>
        <%--<button id="connect" onclick="connect();">Connect</button>--%>
        <%--<button id="disconnect" disabled="disabled" onclick="disconnect();">--%>
            <%--Disconnect--%>
        <%--</button>--%>
    <%--</div>--%>
    <%--<br />--%>
    <%--<div id="conversationDiv">--%>
        <%--<input type="text" id="text" placeholder="Write a message..."/>--%>
        <%--<button id="sendMessage" onclick="sendMessage();">Send</button>--%>
        <%--<p id="response"></p>--%>
    <%--</div>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>