package com.yicj.netty.chat.client;

import com.yicj.netty.common.packet.*;
import com.yicj.netty.common.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}：客户端开始登录", new Date());
        // 模拟连续登录10次
        for (int i = 0 ; i< 10; i ++){
            // 创建登录对象
            LoginRequestPacket packet = new LoginRequestPacket() ;
            packet.setUserId(UUID.randomUUID().toString());
            packet.setUsername("flash["+(i + 1)+"]");
            packet.setPassword("pwd["+(i + 1)+"]");
            // 编码
            // ctx.alloc() 获取的是与当前连接相关的ByteBuf分配器,建议这样使用
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);
            ctx.channel().writeAndFlush(byteBuf) ;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg ;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet == null){
            return;
        }
        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet ;
            if (responsePacket.getSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
                log.info("{}：客户端登录成功!", new Date());
            }else {
                log.info("{}：客户端登录失败，原因：{}", new Date(), responsePacket.getReason());
            }
        }else if (packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet ;
            log.info("{}：收到服务端的消息：{}", new Date(), messageResponsePacket.getMessage());
        }
    }
}
