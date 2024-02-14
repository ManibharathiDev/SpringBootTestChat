package com.test.chat.Test.Chat.repo;

import com.test.chat.Test.Chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    @Query(value = "SELECT * FROM user WHERE email NOT IN(:email)", nativeQuery = true)
    List<User> findAllEmailNotIn(String email);
}
