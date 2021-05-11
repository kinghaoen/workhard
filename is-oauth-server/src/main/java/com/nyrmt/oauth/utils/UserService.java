package com.nyrmt.oauth.utils;

import com.nyrmt.oauth.bean.User;
import com.nyrmt.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void CreateUser(){
        User user = new User();
        user.setUsername(new GenerateUnique().GetUniqueStr());
//        user.setUsername("192168092161547221785430");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRealName("方圆");
        user.setPhone("18510444551");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);

        User save = userRepository.save(user);
        System.out.println(save);
    }
}
