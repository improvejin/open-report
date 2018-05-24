package com.pplove.bip.authorization;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Autowired
    SysUserRepository userRepository;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        String login = token.getPrincipal().toString();
        Assertion a = token.getAssertion();
        String username = login.toLowerCase();
        //todo
        // save username to db用于权限分配相关操作
        //UserDetails user = userRepository.findOne(username);
        SysUser user = new SysUser();
        user.setUserName(username);
        List<SysRole> roles = new ArrayList<>();
        SysRole r = new SysRole();
        r.setName("ROLE_ADMIN");
        roles.add(r);
        user.setRoles(roles);
        return user;
    }
}
