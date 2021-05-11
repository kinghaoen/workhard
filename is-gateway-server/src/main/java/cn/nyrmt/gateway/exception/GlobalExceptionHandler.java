package cn.nyrmt.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    //{
    //    "timestamp": "2021-03-08T16:05:19.282+0000",
    //    "status": 500,
    //    "error": "Internal Server Error",
    //    "message": "用户认证异常",
    //    "path": "/user/1921680921614929008974"
    //}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> handlerRuntimeException(Exception Exception, HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp",new Date());
        result.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("message",Exception.getMessage());
        result.put("path",request.getRequestURI());

        return result;
    }
}
