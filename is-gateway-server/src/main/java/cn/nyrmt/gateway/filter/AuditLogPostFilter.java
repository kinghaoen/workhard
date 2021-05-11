package cn.nyrmt.gateway.filter;

import cn.nyrmt.gateway.entity.AuditLog;
import cn.nyrmt.gateway.repository.AuditLogRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuditLogPostFilter extends ZuulFilter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();

        AuditLog auditLog = (AuditLog) request.getAttribute("AuditLog");
        auditLog.setResponseStatus(response.getStatus());

        //更新审计日志
        auditLogRepository.save(auditLog);

        return null;
    }
}
