package cn.nyrmt.user.interceptor;

import cn.nyrmt.user.entity.AuditLog;
import cn.nyrmt.user.entity.User;
import cn.nyrmt.user.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(3)
@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("3执行审计日志");

        AuditLog auditLog = new AuditLog();

        String method = request.getMethod();
        auditLog.setRequestMethod(method);

        String requestURI = request.getRequestURI();
        auditLog.setRequestURI(requestURI);

//        User user = (User) request.getAttribute("user");
//        if(user!= null){
//            auditLog.setUsername(user.getUsername());
//        }

        request.setAttribute("auditLog",auditLog);
        auditLogRepository.save(auditLog);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuditLog auditLog = (AuditLog) request.getAttribute("auditLog");

        auditLog.setResponseStatus(response.getStatus());
        auditLogRepository.save(auditLog);

        super.afterCompletion(request, response, handler, ex);
    }

}
