package cn.nyrmt.user.Controller;

import cn.nyrmt.user.bean.UserInfo;
import cn.nyrmt.user.entity.User;
import cn.nyrmt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    //增加用户
    @PostMapping
    public User addUser(@RequestBody @Validated UserInfo userInfo){
        return userService.addUser(userInfo);
    }

    //删除用户
    @DeleteMapping("/{userId}")
    public void delUser(@PathVariable String userId){
        userService.delUser(userId);
    }

    //修改用户
    @PutMapping
    public User modifyUser(@RequestBody UserInfo userInfo){
        return userService.modifyUser(userInfo);
    }

    //查询所有用户
    @GetMapping
    public List<User> getUsers(){
        return userService.queryUsers();
    }

    //根据条件查询用户
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId, HttpServletRequest request) {
        //User user = (User) request.getAttribute("user");
        User user = (User) redisTemplate.boundValueOps("user").get();
        if (user == null || !userId.equals(user.getId())) {
            throw new RuntimeException("用户认证异常");
        }
        return userService.queryUserById(userId);
    }

    @PostMapping("/login")
    public void userLogin(@RequestBody UserInfo userInfo, HttpServletRequest request){
        //登录的本质就是将用户信息存入session
        UserInfo login = userService.login(userInfo);
        if(login != null){
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
            }
            request.getSession(true).setAttribute("user",login);
        }
    }

}
