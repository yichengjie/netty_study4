package com.yicj.netty.chat.servier.messagehandler.impl;

import com.yicj.netty.chat.servier.messagehandler.RequestPacketHandler;
import com.yicj.netty.common.packet.Packet;

/**
 * @author yicj
 * @date 2023年08月01日 11:34
 */
public abstract class AbstractRequestPacketHandler implements RequestPacketHandler {

    protected Class<? extends Packet> clazz ;

    protected AbstractRequestPacketHandler(Class<? extends Packet> clazz){
        this.clazz = clazz ;
    }

    @Override
    public boolean support(Packet packet) {
        return packet.getClass().isAssignableFrom(clazz) ;
    }
}
