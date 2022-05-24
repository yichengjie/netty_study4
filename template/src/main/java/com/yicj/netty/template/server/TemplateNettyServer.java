package com.yicj.netty.template.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateNettyServer {

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
                        pipeline.addLast(new ServerHandler()) ;
                    }
                }) ;

        bootstrap.bind(8000)
            .addListener(new GenericFutureListener(){
                @Override
                public void operationComplete(Future future) throws Exception {
                    if (future.isSuccess()){
                        log.info("成功绑定端口8000");
                    }else {
                        log.info("绑定8000端口失败！");
                    }
                }
            });

    }
}
