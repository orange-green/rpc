package com.wuxj.rpc.service;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/6/26 15:34:11
 */

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        return "hello "+ name;
    }
}
