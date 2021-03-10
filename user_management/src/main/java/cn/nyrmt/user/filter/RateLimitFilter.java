package cn.nyrmt.user.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Order(1)
@Component
public class RateLimitFilter  extends OncePerRequestFilter {
    RateLimiter rateLimiter = RateLimiter.create(1);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("1执行限流");

        //尝试获取令牌桶中的令牌
        if(rateLimiter.tryAcquire()){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            PrintWriter writer = httpServletResponse.getWriter();
            writer.println("too many request");
            writer.flush();
        }
    }
}
