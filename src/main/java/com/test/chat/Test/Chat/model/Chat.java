package com.test.chat.Test.Chat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String chatId; // ChatRoom chatId
    private Date createdAt;
    private String senderId;
    private String recipientId;
    private String message;
}
