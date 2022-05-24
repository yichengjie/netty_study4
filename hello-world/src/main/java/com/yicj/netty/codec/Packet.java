package com.yicj.netty.codec;

import lombok.Data;

/**
 * 协议
 * 1. 魔数
 * 2. 版本号
 * 3. 序列化算法
 * 4. 指令
 * 5. 数据长度
 * 6. 数据
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version ;

    /**
     * 指令
     * @return 指令
     */
    public abstract Byte getCommand() ;

}
