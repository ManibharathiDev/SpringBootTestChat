package com.test.chat.Test.Chat.broker;

import com.test.chat.Test.Chat.model.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(@Payload Chat chat)
    {
        messagingTemplate.convertAndSendToUser(
                chat.getRecipientId(),"/queue/messages",
                chat
        );
    }

}
