package com.yicj.netty.common.packet;

import com.yicj.netty.common.Command;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String message ;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
