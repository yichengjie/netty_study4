package com.yicj.netty.chat.servier;

import com.yicj.netty.common.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
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
        }else if (packet instanceof MessageRequestPacket){
            // 处理消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet ;
            log.info("{}：收到客户端消息：{}",new Date(), messageRequestPacket.getMessage());
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket() ;
            messageResponsePacket.setMessage("服务端回复【"+messageRequestPacket.getMessage()+"】");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf) ;
        }

    }

    private boolean validLogin(LoginRequestPacket loginRequestPacket) {
        return true ;
    }
}
