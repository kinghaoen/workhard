package cn.nyrmt.gateway.exception;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//限流出错了 可以做一些记录 比如可能被攻击了之类的
@Component
public class MyRateLimitHandleError extends DefaultRateLimiterErrorHandler {

    Logger logger = LoggerFactory.getLogger(MyRateLimitHandleError.class);

    @Override
    public void handleError(String msg, Exception e) {

        logger.info("RateLimit Error");
//        logger.info("RateLimit error message {}",e.getMessage());

        //super.handleError(msg, e);
    }
}
