package cn.nyrmt.user.service;

import cn.nyrmt.user.bean.UserInfo;
import cn.nyrmt.user.entity.User;
import cn.nyrmt.user.repository.UserRepository;
import cn.nyrmt.user.utils.GenerateID;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        String userId = new GenerateID().GetUserId();
        user.setId(userId);

        String scryptPassword = SCryptUtil.scrypt(userInfo.getPassword(), 16, 8, 2);
        user.setPassword(scryptPassword);
        return userRepository.save(user);
    }

    @Override
    public void delUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User modifyUser(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        return userRepository.save(user);

    }

    @Override
    public List<User> queryUsers() {
        return userRepository.findAll();
    }

    @Override
    public User queryUserById(String userId) {
        return userRepository.findById(userId).orElse(new User());
    }

    @Override
    public User queryUserBy(User user) {
        return userRepository.findOne(Example.of(user)).orElse(null);
    }

    //登录接口
    @Override
    public UserInfo login(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        User getUser = userRepository.findOne(Example.of(user)).orElse(null);

        if(getUser != null){
            BeanUtils.copyProperties(getUser,userInfo);
            return userInfo;
        }else{
            return null;
        }

    }
}
