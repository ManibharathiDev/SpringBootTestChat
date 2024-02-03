let stompClient = null;
let nickName = null;
let selectedId = null;
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

function onOneMessageReceived(payload)
{
    console.log("One to one message received");
    const message = JSON.parse(payload.body);
    let data = "<li>"+message.senderId+"-"+message.message+"</li>";
    $(myMessages).append(data);
}


function onMessageReceived(payload)
{
    console.log("Message Received");
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);

    let data = "<li>"+message.name+"-"+message.content+"</li>";
    //$(myMessages).append(data);

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


    $('#sendChatMessage').click(function(){

        let message = $('#chatMessage').val();
        alert(selectedId);
        stompClient.send("/app/chat",
                        {},
                        JSON.stringify({senderId: nickName, recipientId: selectedId, message: message})
                    );

    });

    $('#sendMessage').click(function()
    {

        nickName = $('#nickName').val();
        let name = $('#name').val();

        stompClient.subscribe(`/user/${nickName}/queue/messages`,onOneMessageReceived)

        /*console.log("Send message clicked");
        let profileName = $('#profileName').val();
        let name = $('#name').val();
        let message = $('#message').val();
        stompClient.send("/app/user.addMessage",
                {},
                JSON.stringify({profileName: profileName, name: name, content: message})
            );*/

    });

    $('.selectedId').click(function(){
        selectedId = this.id;
        alert(selectedId);
    })

});