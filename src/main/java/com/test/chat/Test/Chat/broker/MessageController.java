package com.test.chat.Test.Chat.broker;

import com.test.chat.Test.Chat.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    /**
     *
     * @MessageMapping -> It is used for send message from client
     * @SendTo -> It is used for subscribe url for the client
     */
    @MessageMapping("/user.addMessage")
    @SendTo("/user/public")
    public User passMessage(@Payload User user)
    {
        //return user.getContent();
        return user;
        //return getClass().getSimpleName()+"- Message Received";
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public User addNewUser(@Payload User user)
    {
        return user;
    }

}
