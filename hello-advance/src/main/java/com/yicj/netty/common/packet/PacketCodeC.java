package com.yicj.netty.common.packet;

import com.yicj.netty.common.Command;
import com.yicj.netty.common.serializer.Serializer;
import com.yicj.netty.common.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {

    private static final  int MAGIC_NUMBER  = 0x12345678 ;

    public static final PacketCodeC INSTANCE = new PacketCodeC() ;

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        //1. 创建ByteBuf对象
        //ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //ByteBuf byteBuf = allocator.ioBuffer();
        //2. 序列化Java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER) ;
        byteBuf.writeByte(packet.getVersion()) ;
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()) ;
        byteBuf.writeByte(packet.getCommand()) ;
        byteBuf.writeInt(bytes.length) ;
        byteBuf.writeBytes(bytes) ;
        return byteBuf ;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4) ;
        // 跳过版本号
        byteBuf.skipBytes(1) ;
        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();
        // 数据包内容
        byte [] bytes = new byte[length] ;
        byteBuf.readBytes(bytes) ;
        // 反序列化
        Class<? extends Packet> requestType = this.getRequestType(command) ;
        Serializer serializer = this.getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null){
            return serializer.deserialize(requestType, bytes) ;
        }
        return null ;
    }


    private Serializer getSerializer(byte serializeAlgorithm) {
        if (SerializerAlgorithm.JSON == serializeAlgorithm){
            return Serializer.DEFAULT ;
        }
        return null ;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if (Command.LOGIN_REQUEST == command){
            return LoginRequestPacket.class ;
        }else if (Command.LOGIN_RESPONSE == command){
            return LoginResponsePacket.class ;
        }else if (Command.MESSAGE_REQUEST == command){
            return MessageRequestPacket.class ;
        }else if (Command.MESSAGE_RESPONSE == command){
            return MessageResponsePacket.class ;
        }
        return null ;
    }
}
