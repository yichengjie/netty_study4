package com.yicj.netty.codec;

import lombok.Data;

@Data
public class LoginRequestPackage extends Packet {
    private Integer userId ;
    private String username ;
    private String password ;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
