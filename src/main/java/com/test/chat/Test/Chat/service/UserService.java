package com.test.chat.Test.Chat.service;

import com.test.chat.Test.Chat.model.User;
import com.test.chat.Test.Chat.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User saveUser(User user)
    {
        User eUser = findByEmail(user);
        if(eUser != null)
        {
            eUser.setStatus(1);
            userRepository.save(eUser);
            return eUser;
        }
        user.setStatus(1);
        userRepository.save(user);
        return user;
    }

    public User findByEmail(User user)
    {
        return userRepository.findByEmail(user.getEmail());
    }

    public List<User> fetchAllUsers()
    {
        return userRepository.findAll();
    }

    public User updateStatus(User user)
    {
        System.out.println("Update Calling "+user);
        User eUser = findByEmail(user);
        if(eUser != null)
        {
            eUser.setStatus(0);
            userRepository.save(eUser);
            System.out.println("Update Set");

        }
        return eUser;
    }
}
