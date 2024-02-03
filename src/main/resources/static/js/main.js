let stompClient = null;
const socket = new SockJS("/ws");
stompClient = Stomp.over(socket);
stompClient.connect(
    {},
    onConnected,
    onError
);

function onConnected()
{
    console.log("Socket Connected Successfully");

    stompClient.subscribe("/user/public",onMessageReceived);

}


function onMessageReceived(payload)
{
    console.log("Message Received");
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);

    let data = "<li>"+message.name+"-"+message.content+"</li>";
    $(myMessages).append(data);

}

function onError()
{
    console.log("Could not connect to WebSocket server. Please refresh this page to try again!");
}

$(document).ready(function(){

// stompClient.send("/app/user.addUser",
//        {},
//        JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
//    );
    $('#sendMessage').click(function(){
        console.log("Send message clicked");
        let profileName = $('#profileName').val();
        let name = $('#name').val();
        let message = $('#message').val();
        stompClient.send("/app/user.addMessage",
                {},
                JSON.stringify({profileName: profileName, name: name, content: message})
            );

    });

});