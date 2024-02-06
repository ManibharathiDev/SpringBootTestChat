let stompClient = null;
let nickName = null;
let email = null;
let selectedId = null;
let onlineUsers = null;
let currentId = null;
let prevId = null;
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

    stompClient.subscribe("/user/public",onNewUserReceived);
    //stompClient.subscribe("/user/new",onNewUserReceived);
}

function onNewUserReceived(payload)
{
    const user = JSON.parse(payload.body);
    let isAvailable = false
                onlineUsers.forEach(users=>
                {
                    if(user.email === users.email)
                    {
                        isAvailable = true;

                    }
                });
     console.log(isAvailable +" - ")

     if(isAvailable === true)
     {
         let userId = user.id+"-"+user.id;
         console.log(userId);
         $("#"+userId).show();
         return
     }
     if(user.email !== email && isAvailable === false)
     {

        let userList = "<li class='flex p-2 text-white font-bold cursor-pointer text-sm selectedId justify-center' id="+user.id+"><span class='mr-auto'>"+user.email+"</span>";
                if(user.status == 1)
                {
                    let spanId = user.id+"-"+user.id;
                    userList += "<span id="+spanId+" class='ml-auto bg-lime-500 mt-2 p-2 rounded-full block h-0.5 w-0.5'></span>";
                }
                userList += "</li>";
                $('#myUsers').append(userList);

     }
}

function onOneMessageReceived(payload)
{
    console.log("One to one message received "+selectedId+"-"+email);
    const message = JSON.parse(payload.body);
    if(message.senderId === selectedId && message.recipientId === email)
        {
            let data = "<li><div class='flex mt-2 mr-2'><span class='ml-auto bg-amber-200 rounded p-2'>"+message.message+"</span></div></li>";
            $(myMessages).append(data);
        }
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

$('#chatView').hide();

// stompClient.send("/app/user.addUser",
//        {},
//        JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
//    );


    $('#sendChatMessage').click(function(){

        let message = $('#chatMessage').val();
        if(message != "")
        {
            stompClient.send("/app/chat",
                            {},
                            JSON.stringify({senderId: email, recipientId: selectedId, message: message})
                        );
            let data = "<li><div class='flex mt-2 ml-2'><span class='mr-auto bg-lime-200 rounded p-2'>"+message+"</span></div></li>";
            $(myMessages).append(data);
        }
        $('#chatMessage').val("");
    });

    $('#login').click(function()
    {

        nickName = $('#profileName').val();
        let name = $('#name').val();
        email = $('#email').val();

        stompClient.send("/app/user.addUser",{},
            JSON.stringify(
            {
                profileName:nickName,
                name:name,
                email:email
            })
        );

        stompClient.subscribe(`/user/${email}/queue/messages`,onOneMessageReceived)
        stompClient.subscribe('/user/disconnect',onUserLogout);

        $('#profileView').hide();
        $('#chatView').show();

        fetchAllUsers();

        /*console.log("Send message clicked");
        let profileName = $('#profileName').val();
        let name = $('#name').val();
        let message = $('#message').val();
        stompClient.send("/app/user.addMessage",
                {},
                JSON.stringify({profileName: profileName, name: name, content: message})
            );*/

    });

    function onUserLogout(payload)
    {
        const message = JSON.parse(payload.body);
            onlineUsers.forEach(user =>
            {

                if(message.id === user.id && message.email === user.email)
                {
                    let spanId = message.id+"-"+message.id;
                    $('#'+spanId).hide();
                }

            });
        if(message.email === email)
        {
            nickName = null;
            email = null;
            selectedId = null;
            onlineUsers = null;
            currentId = null;
            prevId = null;
            $('#myUsers').empty();
            $('#myMessages').empty();
        }

    }

    $(document).on('click','.selectedId',function()
    {
        currentId = this.id;

         onlineUsers.map((user)=>{
                if(user.id == currentId)
                {
                    selectedId = user.email;

                }
         });

         if(prevId == currentId)
         {
            return
         }

         $('#myMessages').empty();

         if(prevId == null)
         {
            $('#'+currentId).removeClass("text-white");
             $('#'+currentId).addClass("bg-stone-50");
             $('#'+currentId).addClass("text-black");
         }
         else{
            $('#'+currentId).removeClass("text-white");
                         $('#'+currentId).addClass("bg-stone-50");
                         $('#'+currentId).addClass("text-black");

            $('#'+prevId).removeClass("bg-stone-50");
            $('#'+prevId).removeClass("text-black");
            $('#'+prevId).addClass("text-white");

         }
         prevId = currentId;
         fetchAllMessages();
    });

    $(document).on('click','#logout',function(){



            stompClient.send("/app/user.logout",{},
                        JSON.stringify(
                        {
                            profileName:nickName,
                            name:"",
                            email:email
                        })
                    );

//            nickName = null;
//             email = null;
//            selectedId = null;
//             onlineUsers = null;
//             currentId = null;
//              prevId = null;
            $('#profileView').show();
            $('#chatView').hide();
    });
});

async function fetchAllMessages()
{
    const allMessages = await fetch(`/chats/${email}/${selectedId}`);
    let messages = await allMessages.json();
    messages.forEach(message => {
        console.log(message);

        if(message.senderId == email)
        {
            let data = "<li><div class='flex mt-2 ml-2'><span class='mr-auto bg-lime-200 rounded p-2'>"+message.message+"</span></div></li>";
             $(myMessages).append(data);
        }
        else
        {
            let data = "<li><div class='flex mt-2 mr-2'><span class='ml-auto bg-amber-200 rounded p-2'>"+message.message+"</span></div></li>";
            $(myMessages).append(data);
        }

    });
}

async function fetchAllUsers()
{
    const allUsers = await fetch("/users");
    let users = await allUsers.json();
    onlineUsers = users.filter(user => user.email !== email);
    onlineUsers.forEach(user =>
    {
        let userList = "<li class='flex p-2 text-white font-bold cursor-pointer text-sm selectedId justify-center' id="+user.id+"><span class='mr-auto'>"+user.email+"</span>";

            let spanId = user.id+"-"+user.id;
            userList += "<span id="+spanId+" class='ml-auto bg-lime-500 mt-2 p-2 rounded-full block h-0.5 w-0.5'></span>";

        userList += "</li>";
        $('#myUsers').append(userList);
        if(user.status == 1)
        {
            $('#'+spanId).show();
        }
        else
        {
            $('#'+spanId).hide();
        }
    });
}