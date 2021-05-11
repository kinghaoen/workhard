package com.nyrmt.oauth.util;

import com.nyrmt.oauth.bean.User;
import com.nyrmt.oauth.repository.UserRepository;
import com.nyrmt.oauth.utils.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser() {
        userService.CreateUser();

    }

    @Test
    void finOne() {
        User user = new User();
        user.setUsername("192168092161547461069282");
        User getUser = userRepository.findByUsername("192168092161547461069282");
//        User getUser = userRepository.findOne(Example.of(user)).orElse(null);
        System.out.println(getUser);

    }
}