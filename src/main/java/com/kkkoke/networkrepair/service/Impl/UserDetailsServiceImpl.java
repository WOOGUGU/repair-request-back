package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.pojo.Role;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.pojo.helper.UserResult;
import com.kkkoke.networkrepair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 UserDetailsService 的实现
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.查询用户
        User user = userService.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("username has not existed");
        }
        // 2.查询权限信息
        List<Role> roles = userService.getRolesByUid(user.getId());
        user.setRoles(roles);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(grantedAuthorities).build();
//        return new UserResult(user.getId(),  user.getName(), (org.springframework.security.core.userdetails.User) userDetails, grantedAuthorities);
        return new UserResult(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getTel(), grantedAuthorities);
    }
}
