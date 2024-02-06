package com.test.chat.Test.Chat.broker;

import com.test.chat.Test.Chat.model.User;
import com.test.chat.Test.Chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public User addNewUser(@Payload User user)
    {
        return userService.saveUser(user);
    }

    @MessageMapping("/user.logout")
    @SendTo("/user/disconnect")
    public User disconnectUser(@Payload User user)
    {
        return userService.updateStatus(user);

    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers()
    {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

}
