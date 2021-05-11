package cn.nyrmt.gateway.filter;

import cn.nyrmt.gateway.bean.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

//认证过滤器
@Component
public class AuthenticationFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        //1.如果请求的/token 那么不需要做认证 本身就没token 是经过网关找认证中心要token的 所以要放行

        //如果不是请求token的 或者以token开头的?
        //startsWithIgnoreCase 更合适
//        if(!StringUtils.startsWithIgnoreCase(request.getRequestURI(),"/token")){
//        if(!StringUtils.equalsIgnoreCase(request.getRequestURI(),"/token/oauth/token")){
//
//        }
        if(!StringUtils.startsWithIgnoreCase(request.getRequestURI(),"/token")){

            //判断是否携带token
            String authorization = request.getHeader("Authorization");
            if(StringUtils.isNotBlank(authorization)){

                //获取token
                if(StringUtils.startsWithIgnoreCase(authorization,"Bearer ")){
                    String token = StringUtils.substringAfter(authorization, "Bearer ");

                    try {
                        TokenInfo tokenInfo = getTokenInfo(token);

                        request.setAttribute("TokenInfo",tokenInfo);
                    }catch (Exception e){
                        logger.info("获取TokenInfo信息失败");
                    }
                }

            }

        }

        return null;
    }

    private TokenInfo getTokenInfo(String token) {
        //携带token 向认证服务器发送验证请求
        //先组装一个http请求体
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway-server","123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token",token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params,headers);

        String requestURL = "http://127.0.0.1:7777/oauth/check_token";
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(requestURL, HttpMethod.POST, httpEntity, TokenInfo.class);

        //认证之后应该把用户信心放到redis中 方便后边的审计 或者包括授权
        return responseEntity.getBody();
    }
}
