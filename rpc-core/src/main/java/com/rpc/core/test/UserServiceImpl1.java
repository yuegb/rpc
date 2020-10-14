package com.rpc.core.test;

import com.rpc.core.annotation.Service;

/**
 * @auther yuegb
 * @Date: 2020-10-14 11:15
 * @Description:
 */
@Service(interfaceClass = UserServiceImpl1.class)
public class UserServiceImpl1 implements UserService {
    @Override
    public String userNmae(String userName) {
        return userName;
    }
}
