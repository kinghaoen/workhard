package cn.nyrmt.user.config;

import cn.nyrmt.user.interceptor.AuditLogInterceptor;
import cn.nyrmt.user.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInterceptor auditLogInterceptor;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(authorizationInterceptor);
    }

    //写到cn/nyrmt/user/config/AuditorAwareConfig.java里
//    @Bean
//    public AuditorAware<String> auditorAware(){
//        return () -> Optional.of("xxx");
//    }
}
