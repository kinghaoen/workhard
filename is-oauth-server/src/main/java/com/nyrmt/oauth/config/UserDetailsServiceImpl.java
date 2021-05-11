package com.nyrmt.oauth.config;

import com.nyrmt.oauth.bean.User;
import com.nyrmt.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username就是你的账号 全数据库唯一的;
        // 表id默认使用自增的就行
        // username唯一 手机号唯一
        // 登录的时候使用手机号 也可以查询出唯一的一份账户 username也是
        User user = new User();
        user.setUsername(username);
        //用户必须启用
        user.setEnabled(true);
        //用户没有过期
        user.setAccountNonExpired(true);
        //用户没有锁定
        user.setAccountNonLocked(true);

        user.setCredentialsNonExpired(true);

        User getUser = userRepository.findOne(Example.of(user)).orElse(null);
        if(getUser!=null){
            return new org.springframework.security.core.userdetails.User(getUser.getUsername(), getUser.getPassword(), getUser.isEnabled(), getUser.isAccountNonExpired(), getUser.isCredentialsNonExpired(), getUser.isAccountNonLocked(), getAuthorities());
        }else {
            throw new UsernameNotFoundException("用户未找到或已经停用,请联系管理员");
        }


        /*
        return User
                .withUsername(username)
                .password(passwordEncoder.encode("123456"))
                .authorities("ROLE_USER").build();
        */
    }

    //这里也是从数据库中查询出来的
    private Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return list;
    }
}
