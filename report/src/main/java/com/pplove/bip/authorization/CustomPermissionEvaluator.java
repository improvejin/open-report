package com.pplove.bip.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Configuration
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger LOG = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication auth, Object o, Object o1) {
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        SysUser user = (SysUser) auth.getPrincipal();
        /**
         * todo,判断用户对资源是否有访问权限
         */
        return false;
    }

    /**
     * ACL控制
     * @param authentication
     * @param serializable
     * @param s
     * @param o
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        throw new RuntimeException("Id-based permission evaluation not currently supported.");
    }
}
