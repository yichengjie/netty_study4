package com.yicj.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    private static final Integer MAX_RETRY = 5 ;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap() ;
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) ;
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true) ;
        bootstrap.option(ChannelOption.TCP_NODELAY, true) ;
        NioEventLoopGroup group = new NioEventLoopGroup() ;
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler( new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder()) ;
                    }
                }) ;
        ChannelFuture channelFuture = connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
        Channel channel = channelFuture.channel();
        while (true){
            channel.writeAndFlush(new Date() + ": hello world !") ;
            Thread.sleep(2000);
        }
    }


    public static ChannelFuture connect(Bootstrap bootstrap, String host, int port, int retry){
        return bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()){
                        log.info("连接成功！");
                    }else if (retry == 0){
                        log.info("重试次数已用完，放弃连接!");
                    }else {
                        // 第几次连接
                        int order = (MAX_RETRY - retry) + 1 ;
                        // 本次重连时间间隔
                        int delay = 1 << order ;
                        log.info(new Date() + "：连接失败，第{}次重连...", order);
                        int nextRetry = retry -1 ;
                        bootstrap.config().group().schedule(()-> connect(bootstrap, host, port, nextRetry), delay, TimeUnit.SECONDS) ;
                    }
                });
    }

}
