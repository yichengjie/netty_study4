package com.yicj.hello.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelPipeline ;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpObjectAggregator ;

/**
 * @author yicj
 * @date 2023年07月17日 10:03
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());// http 编解码
        // http 消息聚合器512*1024为接收的最大contentLength
        pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));
        pipeline.addLast(new HttpRequestHandler());// 请求处理器

    }
}