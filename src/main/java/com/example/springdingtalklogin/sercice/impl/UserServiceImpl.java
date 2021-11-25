package com.example.springdingtalklogin.sercice.impl;

import com.example.springdingtalklogin.entity.SysUser;
import com.example.springdingtalklogin.sercice.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author mouxiaoshi
 */
@Service
public class UserServiceImpl implements IUserService {

    public static List<SysUser> sysUsers = new ArrayList<>();

    static {
        sysUsers.add(new SysUser("123", "123",
                "123", Arrays.asList("ROLE_admin", "ROLE_admin1")));
        sysUsers.add(new SysUser("456", "456",
                "456", Arrays.asList("ROLE_admin", "ROLE_admin3")));
        sysUsers.add(new SysUser("789", "789",
                "789", Arrays.asList("ROLE_admin2", "ROLE_admin3")));
    }

    @Override
    public SysUser loginWithDingTalk(String code) {
        //模拟获取用户,在正式环境根据钉钉获取钉钉用户id,然后在数据中匹配用户信息
        if (code.length() == 4) {
            return sysUsers.get(new Random().nextInt(3));
        }
        return null;
    }

    @Override
    public SysUser loginWithUsername(String username, String password) {
        return sysUsers.stream()
                .filter(sysUser -> sysUser.getUsername().equals(username) && sysUser.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
