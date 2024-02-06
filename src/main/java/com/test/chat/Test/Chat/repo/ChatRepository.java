package com.test.chat.Test.Chat.repo;

import com.test.chat.Test.Chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    List<Chat> findBySenderIdAndRecipientId(String senderId,String recipientId);

    @Query(value = "SELECT * FROM chat WHERE (sender_id = :senderId OR sender_id = :recipientId) AND (recipient_id = :senderId OR recipient_id = :recipientId)", nativeQuery = true)
    List<Chat> findBySenderIdOrRecipientIdAndSenderIdAndRecipientId(String senderId,String recipientId);

}
