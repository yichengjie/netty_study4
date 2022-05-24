package com.yicj.netty.common;

public interface Command {
    // 登录请求
    Byte LOGIN_REQUEST = 1;
    // 登录返回
    Byte LOGIN_RESPONSE = 2;
    // 消息请求
    Byte MESSAGE_REQUEST = 3 ;
    // 消息返回
    Byte MESSAGE_RESPONSE = 4 ;
}
