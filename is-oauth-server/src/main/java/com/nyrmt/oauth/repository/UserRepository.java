package com.nyrmt.oauth.repository;

import com.nyrmt.oauth.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
