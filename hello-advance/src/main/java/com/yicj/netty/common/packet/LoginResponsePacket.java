package com.yicj.netty.common.packet;

import com.yicj.netty.common.Command;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private Boolean success ;
    private String reason ;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
