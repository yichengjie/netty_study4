package com.yicj.netty.simple.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接成功建立后，会调用channelActive方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(new Date() + " : 客户端写出数据!");
        //1. 获取数据
        ByteBuf byteBuf = this.getByteBuf(ctx);
        //2. 写数据
        ctx.channel().writeAndFlush(byteBuf) ;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg ;
        log.info("{} : 客户端读到数据 -> {}", new Date(), byteBuf.toString(StandardCharsets.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        //1. 获取二进制抽象ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        //2. 准备数据，指定字符串的字符集为UTF-8
        byte [] bytes = "你好，闪电侠".getBytes(StandardCharsets.UTF_8) ;
        //3. 填充数据到ByteBuf
        buffer.writeBytes(bytes) ;
        return buffer ;
    }
}
