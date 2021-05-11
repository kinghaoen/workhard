package cn.nyrmt.front.controller;

import cn.nyrmt.front.bean.ResponseToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {

    RestTemplate restTemplate = new RestTemplate();
/*
    @PostMapping("/login")
    public void Login(@RequestBody Credentials credentials, HttpServletRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("front-server","123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username",credentials.getUsername());
        params.add("password",credentials.getPassword());
        params.add("grant_type","password");
        params.add("scope","read write");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        String getTokenUrl = "http://www.gateway-server.com/token/oauth/token";
        ResponseEntity<ResponseToken> responseEntity = restTemplate.exchange(getTokenUrl, HttpMethod.POST, httpEntity, ResponseToken.class);
        ResponseToken responseToken = responseEntity.getBody();
        if(responseToken != null){
            System.out.println(responseToken.toString());
            request.getSession().setAttribute("TokenInfo",responseToken);
        }
    }
*/
    @GetMapping("/oauth/callback")
    public void Login(@RequestParam String code, @RequestParam String state, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("front-server","123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code",code);
        params.add("grant_type","authorization_code");
        params.add("redirect_uri","http://127.0.0.1:8080/oauth/callback");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        String getTokenUrl = "http://127.0.0.1:8888/token/oauth/token";
        ResponseEntity<ResponseToken> responseEntity = restTemplate.exchange(getTokenUrl, HttpMethod.POST, httpEntity, ResponseToken.class);
        ResponseToken responseToken = responseEntity.getBody();
        if(responseToken != null){
            request.getSession().setAttribute("TokenInfo",responseToken.init());
        }

        //NodeJS服务器 需要跳转到用户登录前正在浏览的页面 state变量就是保存了用户浏览的页面
        System.out.println("用户当前正在浏览页面："+state);
        response.sendRedirect("/");
    }

    @GetMapping("/me")
    public ResponseToken isLogin(HttpServletRequest request){
        return (ResponseToken) request.getSession().getAttribute("TokenInfo");
    }

    @GetMapping("/logout")
    public void Logout(HttpServletRequest request){
        request.getSession().invalidate();
    }
}
