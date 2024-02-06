package com.test.chat.Test.Chat.repo;

import com.test.chat.Test.Chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
