package com.example.springdingtalklogin.sercice;

import com.example.springdingtalklogin.entity.SysUser;

public interface IUserService {
    /**
     * 钉钉登录
     * @param code
     * @return
     */
    SysUser loginWithDingTalk(String code);

    /**
     * 账密登录
     * @param username
     * @param password
     * @return
     */
    SysUser loginWithUsername(String username, String password);
}
