package cn.nyrmt.user.service;

import cn.nyrmt.user.bean.UserInfo;
import cn.nyrmt.user.entity.User;

import java.util.List;

public interface UserService {
    //add
    User addUser(UserInfo userInfo);

    //del
    void delUser(String userId);

    //update
    User modifyUser(UserInfo userInfo);

    //query
    List<User> queryUsers();

    //queryById
    User queryUserById(String userId);

    //queryByUser
    User queryUserBy(User user);

    UserInfo login(UserInfo userInfo);
}
