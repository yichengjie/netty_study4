package com.yicj.netty.common.packet;

import com.yicj.netty.common.Command;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String userId ;
    private String username ;
    private String password ;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
