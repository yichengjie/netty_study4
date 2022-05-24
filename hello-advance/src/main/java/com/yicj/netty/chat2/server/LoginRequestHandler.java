package com.yicj.netty.chat2.server;

import com.yicj.netty.common.packet.LoginRequestPacket;
import com.yicj.netty.common.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        // 登录校验
        LoginResponsePacket responsePacket = new LoginResponsePacket() ;
        if (this.validLogin(loginRequestPacket)){
            // 校验成功
            responsePacket.setSuccess(true);
        }else {
            // 校验失败
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码校验失败！");
        }
        // 编码
        ctx.writeAndFlush(responsePacket) ;
    }

    private boolean validLogin(LoginRequestPacket loginRequestPacket) {
        return true ;
    }
}
