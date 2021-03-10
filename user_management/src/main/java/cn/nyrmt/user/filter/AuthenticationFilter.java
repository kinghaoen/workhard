package cn.nyrmt.user.filter;

import cn.nyrmt.user.entity.User;
import cn.nyrmt.user.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("2执行认证");

        //实现基本的认证 Basic Authentication
        String BasicAuthHeader = httpServletRequest.getHeader("Authorization");
        if(StringUtils.isNotBlank(BasicAuthHeader)){
            String basic_auth = StringUtils.substringAfter(BasicAuthHeader, "Basic ");
            String auth = new String(Base64Utils.decodeFromString(basic_auth));

            String[] strings = StringUtils.splitByWholeSeparator(auth, ":");
            String username = strings[0];
            String password = strings[1];

            User user = new User();
            user.setUsername(username);
            user = userService.queryUserBy(user);

            if(user != null && SCryptUtil.check(password,user.getPassword())){
                //用redis存储
                redisTemplate.boundValueOps("user").set(user);
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
