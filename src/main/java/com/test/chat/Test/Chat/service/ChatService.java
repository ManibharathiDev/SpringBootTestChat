package com.test.chat.Test.Chat.service;

import com.test.chat.Test.Chat.model.Chat;
import com.test.chat.Test.Chat.model.User;
import com.test.chat.Test.Chat.repo.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    public Chat saveNewChat(Chat chat)
    {
        chatRepository.save(chat);
        return chat;
    }

    public List<Chat> findAllChatBySenderIdAndRecipientId(String senderId, String reciepientId)
    {
        //return chatRepository.findBySenderIdAndRecipientId(senderId,reciepientId);
        return chatRepository.findBySenderIdOrRecipientIdAndSenderIdAndRecipientId(senderId,reciepientId);
    }



}
