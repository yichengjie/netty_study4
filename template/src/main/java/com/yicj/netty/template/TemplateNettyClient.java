package com.yicj.netty.template;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TemplateNettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap() ;
        NioEventLoopGroup group = new NioEventLoopGroup() ;
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 指定连接数据读写逻辑
                    }
                }) ;

        ChannelFuture connect = bootstrap.connect("127.0.0.1", 8000);
        Channel channel = connect.channel();
        
    }
}
