package com.test.chat.Test.Chat.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
    private String senderId;
    private String recipientId;

    private String message;
}
