package com.example.springdingtalklogin.security;

import cn.hutool.extra.servlet.ServletUtil;
import com.example.springdingtalklogin.util.JackJsonUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 钉钉认证拦截器
 * @author mouxiaoshi
 */
public class DingTalkAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/dt-login", "POST");

    public DingTalkAuthenticationFilter(AuthenticationManager authenticationManager){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        //设置认证管理器
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String body = ServletUtil.getBody(request);
        Map object = JackJsonUtil.getObject(body, Map.class);
        Object code = object.get("code");
        //新建一个AuthenticationToken以便接下来的认证
        DingTalkAuthenticationToken authRequest = new DingTalkAuthenticationToken(code);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
