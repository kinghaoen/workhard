package cn.nyrmt.gateway.filter;

import cn.nyrmt.gateway.entity.AuditLog;
import cn.nyrmt.gateway.repository.AuditLogRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuditLogPreFilter extends ZuulFilter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取请求上下文 获取请求 从请求中获取当前请求的方法 请求的URL
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        AuditLog auditLog = new AuditLog();
        auditLog.setRequestMethod(request.getMethod());
        auditLog.setRequestURI(request.getRequestURI());

        auditLogRepository.save(auditLog);

        request.setAttribute("AuditLog",auditLog);

        return null;
    }
}
