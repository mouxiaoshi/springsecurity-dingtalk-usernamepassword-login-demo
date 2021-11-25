package com.example.springdingtalklogin.config;

import com.example.springdingtalklogin.entity.SysUser;
import com.example.springdingtalklogin.security.CommonAuthenticationFilter;
import com.example.springdingtalklogin.security.CommonAuthenticationProvider;
import com.example.springdingtalklogin.security.DingTalkAuthenticationFilter;
import com.example.springdingtalklogin.security.DingTalkAuthenticationProvider;
import com.example.springdingtalklogin.util.JackJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DingTalkAuthenticationProvider dingTalkAuthenticationProvider;

    @Autowired
    private CommonAuthenticationProvider commonAuthenticationProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        DingTalkAuthenticationFilter dingTalkAuthenticationFilter = new DingTalkAuthenticationFilter(new ProviderManager(dingTalkAuthenticationProvider));
        dingTalkAuthenticationFilter.setAuthenticationFailureHandler((request, response, ex) -> {
            this.writeResponse(response,ex.getMessage());
        });
        dingTalkAuthenticationFilter.setAuthenticationSuccessHandler((request, response, ex) -> {
            this.writeResponse(response,"登录成功:用户为:"+ JackJsonUtil.toJson(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        });

        CommonAuthenticationFilter commonAuthenticationFilter = new CommonAuthenticationFilter(new ProviderManager(commonAuthenticationProvider));
        commonAuthenticationFilter.setAuthenticationFailureHandler((request, response, ex) -> {
            this.writeResponse(response,ex.getMessage());
        });
        commonAuthenticationFilter.setAuthenticationSuccessHandler((request, response, ex) -> {
            this.writeResponse(response,"登录成功:用户为:"+ JackJsonUtil.toJson(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        });

        httpSecurity
                //禁止跨域校验
                .csrf().disable()
                .authorizeRequests(expressionInterceptUrlRegistry->{
                    expressionInterceptUrlRegistry
                            //通过钉钉登录接口
                            .antMatchers(HttpMethod.POST,"/dt-login").permitAll()
                            .antMatchers(HttpMethod.POST,"/login").permitAll()
                            .anyRequest().authenticated();
                })
//                .authenticationManager()
//                .authenticationProvider(new DingTalkAuthenticationProvider())
                // 添加自定义的钉钉登录过滤器
                .addFilterAt(dingTalkAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(commonAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                //禁止默认表单登录
                .formLogin().disable();

    }

    private void writeResponse(HttpServletResponse response,String msg){
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(msg);
        out.flush();
        out.close();
    }

}
