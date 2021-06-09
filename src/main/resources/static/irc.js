let stompClient = null;

function connect() {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
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
    console.log("Disconnected");
}

function sendMessage(event) {
    event.preventDefault();
    let newMessageInput = document.getElementById('newMessage');
    stompClient.send("/app/chat", {},
        JSON.stringify({'text': newMessageInput.value}));
    newMessageInput.value = "";
}

function showMessageOutput(dto) {
    console.log(dto)
    let allMessagesDiv = document.getElementById("allMessages");
    let messageDiv = document.createElement("div");
    let dateElement = document.createElement("i");
    dateElement.innerText = dto.dateFormatted;

    let authorElement = document.createElement("code");
    authorElement.innerText = ' ' + dto.author + ' ';

    let textElement = document.createElement("span");
    textElement.innerText = dto.text;

    messageDiv.appendChild(dateElement);
    messageDiv.appendChild(authorElement);
    messageDiv.appendChild(textElement);
    allMessagesDiv.appendChild(messageDiv);

    // {text: "hej to treść", author: "Anonim", dateFormatted: "11:40:28"}
    /*
    <div th:each="msg : ${allMessages}">
        <i th:text="${msg.getDateFormatted()}"></i>
        <code th:text="${msg.author}"></code>
        <span th:text="${msg.text}"></span>
    </div>
     */
}

connect();