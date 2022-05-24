package com.yicj.netty.codec;

public interface Command {
    // 登录请求
    Byte LOGIN_REQUEST = 1;
    // 登录返回
    Byte LOGIN_RESPONSE = 2;
}
