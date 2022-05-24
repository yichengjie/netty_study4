package com.yicj.netty.chat.servier;

import com.yicj.netty.common.packet.LoginRequestPacket;
import com.yicj.netty.common.packet.LoginResponsePacket;
import com.yicj.netty.common.packet.Packet;
import com.yicj.netty.common.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg ;
        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        // 判断是否为登录请求数据包
        if (packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet ;
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
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.writeAndFlush(responseByteBuf) ;
        }

    }

    private boolean validLogin(LoginRequestPacket loginRequestPacket) {
        return true ;
    }
}
