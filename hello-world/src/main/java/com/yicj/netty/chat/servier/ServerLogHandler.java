package com.yicj.netty.chat.servier;

import com.yicj.netty.common.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author yicj
 * @date 2023年08月01日 11:26
 */
@Slf4j
public class ServerLogHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        AttributeKey<Boolean> logStatusAttr = AttributeKey.valueOf("logStatus");
        AttributeKey<String> userIdAttr = AttributeKey.valueOf("userId");
        AttributeKey<String> userNameAttr = AttributeKey.valueOf("userName");
        if (ctx.channel().attr(logStatusAttr) != null
                && Boolean.TRUE.equals(ctx.channel().attr(logStatusAttr).get())){
            String userId = Optional.ofNullable(ctx.channel().attr(userIdAttr)).map(Attribute::get).orElse(null);
            String userName = Optional.ofNullable(ctx.channel().attr(userNameAttr)).map(Attribute::get).orElse(null);
            log.info("当前用户已登录，userId: {}, userName: {}", userId, userName);
        }else {
            InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
            log.info("当前用户未登录, ip: {}, port: {}", socketAddress.getAddress(), socketAddress.getPort());
        }
        ctx.fireChannelRead(msg);
    }
}
