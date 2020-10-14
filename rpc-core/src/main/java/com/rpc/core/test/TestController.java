package com.rpc.core.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther yuegb
 * @Date: 2020-10-14 11:19
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource(type = UserServiceImpl1.class)
    private UserService userService1;

    @Resource(type = UserServiceImpl2.class)
    private UserService userService2;

    @RequestMapping("/userName")
    public String test() {
        System.out.println(userService1.userNmae("zhangsan1"));
        System.out.println(userService2.userNmae("zhangsan2"));
        return "success";
    }
}
