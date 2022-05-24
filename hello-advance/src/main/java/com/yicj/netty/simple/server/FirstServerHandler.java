package com.yicj.netty.simple.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 这个方法在接收到客户端发来的数据之后被回调
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1. 接收数据逻辑
        ByteBuf byteBuf = (ByteBuf) msg ;
        log.info(new Date() + " : 服务端读取到数据 -> {}", byteBuf.toString(StandardCharsets.UTF_8));
        //2. 返回数据到客户端
        log.info(new Date() +" : 服务端写数据！");
        ByteBuf out = this.getByteBuf(ctx) ;
        ctx.writeAndFlush(out) ;
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte [] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》！".getBytes(StandardCharsets.UTF_8) ;
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes) ;
        return buffer ;
    }
}
