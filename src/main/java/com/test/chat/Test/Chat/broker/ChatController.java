package com.test.chat.Test.Chat.broker;

import com.test.chat.Test.Chat.model.Chat;
import com.test.chat.Test.Chat.model.User;
import com.test.chat.Test.Chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(@Payload Chat chat)
    {
        chat.setCreatedAt(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        chatService.saveNewChat(chat);
        messagingTemplate.convertAndSendToUser(
                chat.getRecipientId(),"/queue/messages",
                chat
        );
    }

    @GetMapping("/chats/{senderid}/{recipientid}")
    public ResponseEntity<List<Chat>> findMessages(@PathVariable String senderid, @PathVariable String recipientid)
    {
        return ResponseEntity.ok(chatService.findAllChatBySenderIdAndRecipientId(senderid,recipientid));
        //return ResponseEntity.ok(chatService.findAllChatBySenderIdAndRecipientId(senderid,recipientid));
    }

}
