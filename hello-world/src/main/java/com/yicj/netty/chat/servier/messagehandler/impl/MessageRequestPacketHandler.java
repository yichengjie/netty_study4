package com.yicj.netty.chat.servier.messagehandler.impl;

import com.yicj.netty.common.packet.MessageRequestPacket;
import com.yicj.netty.common.packet.MessageResponsePacket;
import com.yicj.netty.common.packet.Packet;
import com.yicj.netty.common.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 * @author yicj
 * @date 2023年08月01日 11:28
 */
@Slf4j
public class MessageRequestPacketHandler extends AbstractRequestPacketHandler {

    public MessageRequestPacketHandler() {
        super(MessageRequestPacket.class);
    }

    @Override
    public boolean handle(ChannelHandlerContext ctx, Packet packet) {
        // 处理消息
        MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet ;
        log.info("{}：收到客户端消息：{}",new Date(), messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket() ;
        messageResponsePacket.setMessage("服务端回复【"+messageRequestPacket.getMessage()+"】");
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf) ;
        return true ;
    }
}
