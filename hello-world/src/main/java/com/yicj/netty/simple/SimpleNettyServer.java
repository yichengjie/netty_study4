package com.yicj.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleNettyServer {

    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap() ;

        NioEventLoopGroup boss = new NioEventLoopGroup() ;
        NioEventLoopGroup work = new NioEventLoopGroup() ;

        bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                    }
                }) ;

        bootstrap.bind(8000) ;

    }
}
