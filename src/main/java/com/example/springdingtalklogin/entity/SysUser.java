package com.example.springdingtalklogin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author mouxiaoshi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private String username;
    private String password;
    private String dingTalkUserId;
    //权限
    private List<String> permissions;
}
