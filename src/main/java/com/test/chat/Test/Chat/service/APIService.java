package com.test.chat.Test.Chat.service;

import com.test.chat.Test.Chat.model.User;
import com.test.chat.Test.Chat.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class APIService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepository userRepository;


    public void addNewUser(User user)
    {
        user.setStatus(1);
        User eUser = userRepository.save(user);
        simpMessagingTemplate.convertAndSend("/user/public",eUser);
    }

}
