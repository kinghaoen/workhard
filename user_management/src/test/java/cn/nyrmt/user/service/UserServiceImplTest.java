package cn.nyrmt.user.service;

import cn.nyrmt.user.bean.UserInfo;
import cn.nyrmt.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void Tev(){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId("888888");
//        userInfo.setUsername("lisi");
//        userInfo.setPassword("123456");
//        userInfo.setPermission("rw");
//        redisTemplate.boundValueOps("userinfo").set(userInfo);

//        redisTemplate.boundHashOps("user_"+userInfo.getId()).put("id",userInfo.getId());
//        redisTemplate.boundHashOps("user_"+userInfo.getId()).put("username",userInfo.getUsername());
//        redisTemplate.boundHashOps("user_"+userInfo.getId()).put("password",userInfo.getPassword());
//        redisTemplate.boundHashOps("user_"+userInfo.getId()).put("permission",userInfo.getPermission());

//        List list = redisTemplate.boundHashOps("user_" + userInfo.getId()).values();
//        System.out.println(list);

        //{username=zhangsan, password=123456, permission=rw, userid=10001}
//        Map entries = redisTemplate.opsForHash().entries("user_" + userInfo.getId());
//        System.out.println(entries);


//        UserInfo userinfo = (UserInfo) redisTemplate.opsForValue().get("userinfo");
//        System.out.println(userinfo);


//        UserInfo userinfo = (UserInfo) redisTemplate.boundValueOps("userinfo").get();
        User user = (User) redisTemplate.boundValueOps("user").get();
        //UserInfo userinfo = (UserInfo) redisTemplate.opsForValue().get("userinfo");
//        System.out.println(Objects.requireNonNull(userinfo).getId());
        System.out.println(Objects.requireNonNull(user));

    }

    @Test
    public void TestNull(){
        User user = new User();
        if(user != null){
            System.out.println("1111");
        }else {
            System.out.println("222222222222");
        }
    }

    @Test
    public void TestDK(){
        int num = 9331;
        int reduce =38;
        for (int i = 0; i < num; i+=38) {
            System.out.println(num-i);
            if(i %(38*12) == 0){
                System.out.println("------------------");
            }
        }
    }

}