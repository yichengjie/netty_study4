package com.yicj.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap() ;
        //serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024) ;
        //serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue") ;
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup() ;
        serverBootstrap.group(boss, worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                            ctx.newPromise() ;
                            log.info("msg : {}", msg);
                        }
                    });
                }
            });

        // 端口绑定
        bind(serverBootstrap, 8000);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port){
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()){
                log.info("端口{}绑定成功！", port);
            }else {
                log.info("端口{}绑定失败！", port);
                bind(serverBootstrap, port + 1);
            }
        }) ;
    }
}
