package com.yicj.netty.chat.servier;

import com.yicj.netty.common.packet.Packet;
import com.yicj.netty.common.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author yicj
 * @date 2023年08月02日 10:06
 */
@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet == null){
            log.info("读取数据为空!!");
            return;
        }
        log.info("读取到数据: {}", packet);
        out.add(packet) ;
    }
}
