package cn.nyrmt.gateway.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareConfig implements AuditorAware<String> {

    //审计日志中
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = "NonNull";
//        if(StringUtils.isNotBlank(authentication.getPrincipal().toString())){
//            username = authentication.getPrincipal().toString();
//        }
//        return Optional.of(username);
        return Optional.of("NonNull");
    }
}
