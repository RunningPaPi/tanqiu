<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Java API for WebSocket (JSR-356)</title>
</head>
<body>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
<script type="text/javascript">
    var websocket = null;
    document.cookie = "WEBSOCKET_USERNAME=user1; WEBSOCKET_GROUP=fight_room_20180731093652_1; JSESSIONID=2DACDB19C8E36A721D7EC0896A328E5B;domain=ws://localhost/websocket/socketServer"
    if ('WebSocket' in window) {
        //Websocket的连接
        websocket = new WebSocket("ws://localhost/websocket/socketServer");//WebSocket对应的地址
    }
    else if ('MozWebSocket' in window) {
        //Websocket的连接
        websocket = new MozWebSocket("ws://localhost/websocket/socketServer");//SockJS对应的地址
    }
    else {
        alert("socketjs")
        //SockJS的连接
        websocket = new SockJS("http://localhost/sockjs/socketServer");    //SockJS对应的地址
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        //alert(openEvt.Data);
        alert("连接建立成功!");
    }

    function onMessage(evt) {
        alert(evt.data);
        console.log(evt.data);
    }
    function onError() {
    }
    function onClose() {
    }

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            var msg = document.getElementById("inputMsg").value;
            websocket.send(msg);//调用后台handleTextMessage方法
            alert("发送成功!");
        } else {
            alert("连接失败!");
        }
    }

    window.close = function () {
        websocket.onclose();
    }
</script>
请输入：<textarea rows="3" cols="100" id="inputMsg" name="inputMsg">{code:"tq_fight_game_enter_room",params:"userId=2&roomNo=tq_fight_room_20180731093652_1",msg:"进入房间"}</textarea>
<button onclick="doSend();">发送</button>

<br>
加入房间：{code:"tq_fight_game_enter_room",params:"userId=2&roomNo=",msg:"进入房间"}
<br>
比赛数据：{code:"tq_fight_game_data",params:"userId=2&roomNo=fight_room_20180731093652_1&score=100&data=2000",msg:"比赛数据"}

</body>
</html>