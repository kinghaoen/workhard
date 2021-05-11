package com.nyrmt.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    //客户端详情服务配置器(那些应用、客户端可以访问认证服务器)
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        clients.inMemory()
                .withClient("chrome-client")
                .secret(passwordEncoder.encode("123456"))
                .scopes("read","write")
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-service")
                .authorizedGrantTypes("password")

                .and()

                .withClient("order-server")
                .secret(passwordEncoder.encode("123456"))
                .scopes("read")
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-service")
                .authorizedGrantTypes("password");
         */
        clients.jdbc(dataSource);

    }

    //授权服务器端点配置器(哪些用户可以访问认证服务器 必须经过认证管理员 认证过后)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore());
    }

    //授权服务器安全配置器(那些资源服务器可以找认证服务器验证令牌)
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //这里针对的是资源服务 拿着token验证token
        security.checkTokenAccess("isAuthenticated()");
    }

}
