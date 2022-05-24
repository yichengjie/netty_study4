package com.yicj.netty.simple.server;

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
                        // 指定连接数据读写逻辑
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new FirstServerHandler()) ;
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
