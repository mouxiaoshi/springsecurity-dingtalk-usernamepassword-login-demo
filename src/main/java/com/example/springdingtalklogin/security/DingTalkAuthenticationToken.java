package com.example.springdingtalklogin.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 钉钉认证
 *
 * @author mouxiaoshi
 */
public class DingTalkAuthenticationToken extends AbstractAuthenticationToken {
    //钉钉登录码
    private Object code;
    private Object principal;

    public DingTalkAuthenticationToken(Object code) {
        super(null);
        this.code = code;
        this.setAuthenticated(false);
    }

    public DingTalkAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
