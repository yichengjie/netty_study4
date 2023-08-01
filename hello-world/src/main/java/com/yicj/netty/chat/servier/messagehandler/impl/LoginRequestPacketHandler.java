package com.yicj.netty.chat.servier.messagehandler.impl;

import com.yicj.netty.common.packet.LoginRequestPacket;
import com.yicj.netty.common.packet.LoginResponsePacket;
import com.yicj.netty.common.packet.Packet;
import com.yicj.netty.common.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yicj
 * @date 2023年08月01日 11:28
 */
@Slf4j
public class LoginRequestPacketHandler extends AbstractRequestPacketHandler {

    public LoginRequestPacketHandler() {
        super(LoginRequestPacket.class);
    }

    @Override
    public boolean handle(ChannelHandlerContext ctx, Packet packet) {
        log.info("客户端发起登录操作，将进行登录业务逻辑处理!");
        LoginRequestPacket loginPacket = (LoginRequestPacket) packet ;
        // 登录校验
        LoginResponsePacket responsePacket = new LoginResponsePacket() ;
        if (this.validLogin(loginPacket)){
            // 校验成功
            responsePacket.setSuccess(true);
        }else {
            // 校验失败
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码校验失败！");
        }
        // 编码
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
        ctx.writeAndFlush(responseByteBuf) ;
        //
        Channel channel = ctx.channel();
        AttributeKey<Boolean> logStatusAttr = AttributeKey.valueOf("logStatus");
        AttributeKey<String> userIdAttr = AttributeKey.valueOf("userId");
        AttributeKey<String> userNameAttr = AttributeKey.valueOf("userName");
        channel.attr(logStatusAttr).set(true);
        channel.attr(userIdAttr).set(loginPacket.getUserId());
        channel.attr(userNameAttr).set(loginPacket.getUsername());

        return true ;
    }

    private boolean validLogin(LoginRequestPacket loginRequestPacket) {
        return true ;
    }

}
