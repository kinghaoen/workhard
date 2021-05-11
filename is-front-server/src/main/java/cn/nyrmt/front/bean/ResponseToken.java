package cn.nyrmt.front.bean;

import java.time.LocalDateTime;

//用户认证成功后 用来接收Token令牌
public class ResponseToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private String scope;

    private LocalDateTime expiresTime;

    public ResponseToken init(){
        this.expiresTime = LocalDateTime.now().plusSeconds(expires_in - 3);
        return this;
    }

    //true 就是过期了
    //false 就是没有过期
    public boolean isExpired(){
        //11:00过期 现在是12:00
        return this.expiresTime.isBefore(LocalDateTime.now());
    }

    public ResponseToken() {
    }

    public ResponseToken(String access_token, String token_type, String refresh_token, Long expires_in, String scope, LocalDateTime expiresTime) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.expiresTime = expiresTime;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LocalDateTime getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(LocalDateTime expiresTime) {
        this.expiresTime = expiresTime;
    }
}
