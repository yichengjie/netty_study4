package com.yicj.netty.chat.servier;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * @author: yicj
 * @date: 2023/8/5 16:49
 */
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // super.write(ctx, msg, promise);
        ReferenceCountUtil.release(msg) ;
        promise.setSuccess() ;
    }
}
