package com.yicj.netty.chat.servier.messagehandler;

import com.yicj.netty.chat.servier.messagehandler.impl.LoginRequestPacketHandler;
import com.yicj.netty.chat.servier.messagehandler.impl.MessageRequestPacketHandler;
import com.yicj.netty.common.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yicj
 * @date 2023年08月01日 11:41
 */
public class RequestPacketHandlerManager {

    private List<RequestPacketHandler> handlerList = new ArrayList<>() ;

    public static RequestPacketHandlerManager getInstance(){
        return RequestPacketHandlerManagerHolder.handlerManager ;
    }

    private RequestPacketHandlerManager(){
        handlerList.add(new LoginRequestPacketHandler()) ;
        handlerList.add(new MessageRequestPacketHandler()) ;
    }

    public boolean handle(ChannelHandlerContext ctx, Packet packet){
        for (RequestPacketHandler handler: handlerList){
            if (handler.support(packet)){
                return handler.handle(ctx, packet);
            }
        }
        return false ;
    }

    private static class RequestPacketHandlerManagerHolder{
        private static RequestPacketHandlerManager handlerManager = new RequestPacketHandlerManager() ;
    }

}
