package com.yicj.netty.chat.servier;

import com.yicj.netty.chat.servier.messagehandler.RequestPacketHandlerManager;
import com.yicj.netty.common.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg ;
        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet == null){
            log.info("读取数据为空!!");
            return;
        }
        log.info("---> packet : {}", packet);
        // 模拟业务耗时操作
        //Thread.sleep(1000);
        // 判断是否为登录请求数据包
        RequestPacketHandlerManager handlerManager = RequestPacketHandlerManager.getInstance();
        handlerManager.handle(ctx, packet) ;
    }
}
