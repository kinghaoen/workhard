package cn.nyrmt.gateway.filter;

import cn.nyrmt.gateway.bean.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//鉴权
@Component
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        /*
        鉴权流程：
            首先判断是否需要鉴权
            true   需要鉴权 默认需要鉴权
            false  不需要鉴权 直接过
                获取TokenInfo,判断TokenInfo是否为空 或者 未激活状态
                true    为空或者未激活 返回401 (如果以/token开头的请求 也过)
                false   不为空 鉴权 hasPermission()
                    是否有权限
                    true    过
                    false   没有权限 返回403
         */

        if(isNeedAuthorization()){
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("TokenInfo");
            if(tokenInfo == null || !tokenInfo.isActive()){
                if(!StringUtils.startsWithIgnoreCase(request.getRequestURI(),"/token")){
                    handlerAuthorizationError(HttpStatus.UNAUTHORIZED.value(),currentContext);
                }
            }else{
                if(!hasPermission(tokenInfo)){
                    handlerAuthorizationError(HttpStatus.FORBIDDEN.value(),currentContext);
                }
                currentContext.addZuulRequestHeader("username",tokenInfo.getUser_name());
            }
        }

        return null;
    }

    private void handlerAuthorizationError(int httpStatus,RequestContext currentContext) {
        currentContext.getResponse().setContentType("application/json");
        currentContext.setResponseStatusCode(httpStatus);
        currentContext.setResponseBody("Authorization Failed");

        currentContext.setSendZuulResponse(false); //请求到此结束
    }

    //是否需要鉴权 默认所有请求都需要鉴权
    private boolean isNeedAuthorization() {
        return true;
    }

    //判断是否拥有访问权限
    private boolean hasPermission(TokenInfo tokenInfo) {
        System.out.println(tokenInfo.toString());
        return true;
    }
}
