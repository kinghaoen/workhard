package cn.nyrmt.user.config;

import cn.nyrmt.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

@Configuration
public class AuditorAwareConfig implements AuditorAware {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Optional getCurrentAuditor() {
        String username = "Non";
        //从redis里获取认证的时候保存的用户信息 如果能获取到 返回用户id
        User user = (User) redisTemplate.boundValueOps("user").get();
        if(user != null && StringUtils.isNotBlank(user.getPermission())){
            username = user.getUsername();
        }

        //尽量存储用户id
        //return Optional.ofNullable(Objects.requireNonNull(userinfo).getId());
        return Optional.ofNullable(username);
    }
}
