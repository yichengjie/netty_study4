package com.yicj.netty.chat2.client;

import com.yicj.netty.common.packet.LoginResponsePacket;
import com.yicj.netty.common.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) throws Exception {
        if (responsePacket.getSuccess()){
            LoginUtil.markAsLogin(ctx.channel());
            log.info("{}：客户端登录成功!", new Date());
        }else {
            log.info("{}：客户端登录失败，原因：{}", new Date(), responsePacket.getReason());
        }
    }
}
