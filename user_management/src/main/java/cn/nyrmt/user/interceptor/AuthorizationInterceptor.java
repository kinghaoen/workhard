package cn.nyrmt.user.interceptor;

import cn.nyrmt.user.entity.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//授权拦截器
@Order(4)
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    private String[] permitUrls = new String[]{"/user/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("4执行授权");

        //判断请求是否需要授权
        boolean result = true;

        String requestURI = request.getRequestURI();
        //这里默认所有请求都需要授权(登录、注册一般不用授权)
        if(isNeedAuthorized(requestURI)){
            User user = (User) redisTemplate.boundValueOps("user").get();
            if(user == null){
                response.setContentType("text/explain");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().println("need authentication");
                response.getWriter().flush();
                result = false;
            }else{
                String method = request.getMethod();
                if(!user.hasPermission(method)){
                    response.setContentType("text/explain");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().println("forbidden");
                    response.getWriter().flush();
                    result = false;
                }
            }
        }
        return result;
    }


    //默认所有接口都需要授权
    private boolean isNeedAuthorized(String requestURI) {
        return !ArrayUtils.contains(permitUrls, requestURI);
    }

}
