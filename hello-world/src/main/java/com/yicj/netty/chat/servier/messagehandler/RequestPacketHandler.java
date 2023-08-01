package com.yicj.netty.chat.servier.messagehandler;

import com.yicj.netty.common.packet.Packet;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author yicj
 * @date 2023年08月01日 11:29
 */
public interface RequestPacketHandler {

    boolean handle(ChannelHandlerContext ctx, Packet packet) ;

    boolean support(Packet packet) ;
}
