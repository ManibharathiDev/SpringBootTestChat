package com.test.chat.Test.Chat.broker;

import com.test.chat.Test.Chat.model.Chat;
import com.test.chat.Test.Chat.model.User;
import com.test.chat.Test.Chat.service.ChatService;
import com.test.chat.Test.Chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class APIController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/add_user")
    public User addNewUser(@RequestBody User user)
    {
        return userService.saveUser(user);
    }

    @GetMapping("/view_users")
    public ResponseEntity<List<User>> findConnectedUsers()
    {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/message/{senderid}/{recipientid}")
    public ResponseEntity<List<Chat>> findMessages(@PathVariable String senderid, @PathVariable String recipientid)
    {
        return ResponseEntity.ok(chatService.findAllChatBySenderIdAndRecipientId(senderid,recipientid));
    }

    @PostMapping("/chat")
    public void sendMessage(@RequestBody Chat chat)
    {
        chat.setCreatedAt(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        chatService.saveNewChat(chat);
        messagingTemplate.convertAndSendToUser(
                chat.getRecipientId(),"/queue/messages",
                chat
        );
    }

}