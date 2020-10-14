package com.rpc.core.test;

import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.rpc.core.annotation.Service;

/**
 * @auther yuegb
 * @Date: 2020-10-14 11:16
 * @Description:
 */
@Service(interfaceClass = UserServiceImpl2.class)
public class UserServiceImpl2 implements UserService {
    @Override
    public String userNmae(String userName) {
        return userName;
    }
}
