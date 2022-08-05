<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hastatus</title>
    <script>
        let socket = new WebSocket("ws://localhost:8080/demo_war/teste");

        socket.onmessage = function(event) {
            console.log("Recebeu do servidor:"+event.data);
        }
    </script>
</head>
<body>
<p>HASTATUS</p>
<p>v-1.10.10.0</p>

<button onclick="socket.send(JSON.stringify({mensagem:'Opa!'}));">Enviar mensagem</button>
<button onclick="socket.close();">Fechar socket</button>

</body>
</html>