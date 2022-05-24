package com.yicj.netty.chat2.client;

import com.yicj.netty.common.packet.LoginRequestPacket;
import com.yicj.netty.common.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class Chat2ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}：客户端开始登录", new Date());
        // 创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket() ;
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("flash");
        packet.setPassword("pwd");
        // 编码
        // ctx.alloc() 获取的是与当前连接相关的ByteBuf分配器,建议这样使用
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), packet);
        // 写数据
        ctx.channel().writeAndFlush(byteBuf) ;
    }
}
