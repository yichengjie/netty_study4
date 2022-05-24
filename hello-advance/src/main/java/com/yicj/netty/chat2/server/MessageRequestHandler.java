package com.yicj.netty.chat2.server;

import com.yicj.netty.common.packet.MessageRequestPacket;
import com.yicj.netty.common.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 处理消息
        log.info("{}：收到客户端消息：{}",new Date(), messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket() ;
        messageResponsePacket.setMessage("服务端回复【"+messageRequestPacket.getMessage()+"】");
        ctx.channel().writeAndFlush(messageResponsePacket) ;
    }
}
