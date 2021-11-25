package com.example.springdingtalklogin.security;

import com.example.springdingtalklogin.entity.SysUser;
import com.example.springdingtalklogin.sercice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SysUser sysUser = userService.loginWithUsername((String) authentication.getPrincipal(),(String)authentication.getCredentials());
        //此处可抛出用户相关异常，如账号禁用等
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new UsernamePasswordAuthenticationToken(sysUser,authentication.getCredentials(), this.createAuthorityList(sysUser.getPermissions().toArray(new String[]{})));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public List<GrantedAuthority> createAuthorityList(String... authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities.length);
        String[] var2 = authorities;
        int var3 = authorities.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String authority = var2[var4];
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        return grantedAuthorities;
    }
}
