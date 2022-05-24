package com.yicj.netty.chat2.client;

import com.yicj.netty.chat2.PacketEncode;
import com.yicj.netty.chat2.PaketDecoder;
import com.yicj.netty.common.packet.MessageRequestPacket;
import com.yicj.netty.common.packet.PacketCodeC;
import com.yicj.netty.common.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Chat2NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap() ;
        NioEventLoopGroup group = new NioEventLoopGroup() ;
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 指定连接数据读写逻辑
                    ch.pipeline().addLast(new PaketDecoder()) ;
                    ch.pipeline().addLast(new LoginResponseHandler());
                    ch.pipeline().addLast(new MessageResponseHandler()) ;
                    ch.pipeline().addLast(new Chat2ClientHandler()) ;
                    ch.pipeline().addLast(new PacketEncode()) ;
                }
            }) ;

        ChannelFuture connect = bootstrap.connect("127.0.0.1", 8000)
            .addListener(future -> {
                if (future.isSuccess()){
                    log.info("连接成功!");
                    ChannelFuture channelFuture = (ChannelFuture) future ;
                    Channel channel = channelFuture.channel();
                    startConsoleThread(channel) ;
                }else {
                    log.error("连接失败！");
                }
            });
        Channel channel = connect.channel();
        
    }

    private static void startConsoleThread(Channel channel){
        Runnable task = () ->{
            while (!Thread.interrupted()){
                if (LoginUtil.hasLogin(channel)){
                    log.info("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in) ;
                    String line = sc.nextLine();
                    MessageRequestPacket packet = new MessageRequestPacket() ;
                    packet.setMessage(line);
                    channel.writeAndFlush(packet) ;
                }else {
                    log.info("未登录。。。");
                }
            }
        } ;
        new Thread(task).start() ;
    }
}
