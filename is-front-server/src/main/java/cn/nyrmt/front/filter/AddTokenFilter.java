package cn.nyrmt.front.filter;

import cn.nyrmt.front.bean.ResponseToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

//前端服务器请求后端资源需要携带Token
//如果Token过期 则用refresh_token刷新Token
@Component
public class AddTokenFilter extends ZuulFilter {

    @Autowired
    private RestTemplate restTemplate ;
    //RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        ResponseToken tokenInfo = (ResponseToken) request.getSession().getAttribute("TokenInfo");

        if(tokenInfo != null){
            String access_token = tokenInfo.getAccess_token();
            //如果access_token过期了 那么就用refresh_token刷新令牌
            if(tokenInfo.isExpired()){
                HttpHeaders headers = new HttpHeaders();
                headers.setBasicAuth("front-server","123456");

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("refresh_token",tokenInfo.getRefresh_token());
                params.add("grant_type","refresh_token");

                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
                String getTokenUrl = "http://127.0.0.1:8888/token/oauth/token";
                // 加入try catch就是为了refresh_token过期 报错的话 就不再继续往下走了
                // 返回响应码和响应信息 前端过滤器接收响应信息并作出后续处理
                try {
                    ResponseEntity<ResponseToken> responseEntity = restTemplate.exchange(getTokenUrl, HttpMethod.POST, httpEntity, ResponseToken.class);
                    if(responseEntity.getBody() != null){
                        ResponseToken newToken = responseEntity.getBody().init();
                        access_token = newToken.getAccess_token();
                        request.getSession().setAttribute("TokenInfo",newToken);
                    }
                }catch (Exception e){
                    currentContext.setSendZuulResponse(false);

                    currentContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    currentContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    currentContext.getResponse().setContentType("application/json");
                }
            }
            currentContext.addZuulRequestHeader("Authorization","Bearer "+access_token);
        }

        return null;
    }
}
