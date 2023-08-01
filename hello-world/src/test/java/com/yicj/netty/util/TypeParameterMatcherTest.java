package com.yicj.netty.util;

import com.yicj.netty.service.AbstractHello;
import com.yicj.netty.service.impl.HelloA;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.TypeParameterMatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TypeParameterMatcherTest {

    @Test
    public void match(){
        AbstractHello hello = new HelloA() ;
        TypeParameterMatcher matcher = TypeParameterMatcher.find(hello, AbstractHello.class, "I");
        boolean flag = matcher.match("张三");
        log.info("flag : {}", flag);
    }

    @Test
    public void hello(){
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8) ;
        ByteBuf copy = buf.copy(0, 14);
        System.out.println(copy.toString(utf8));
        // 更新源 Bytebuf 索引 0 的字节
        buf.setByte(0, 'J') ;
        // false: 源 Bytebuf和拷贝副本互不影响
        System.out.println(buf.toString(utf8));
        System.out.println(copy.toString(utf8));
    }

    @Test
    public void codec(){
        String content = "hello world" ;
        byte[] bytes = content.getBytes();
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(1) ;
        buffer.writeInt(bytes.length) ;
        buffer.writeBytes("hello worl".getBytes()) ;
        log.info("====> readableBytes : {}", buffer.readableBytes());
        byte command = buffer.readByte();
        int length = buffer.readInt();
        log.info("====> command: {}, length: {}, readableBytes :{}", command, length, buffer.readableBytes());
        // reset readIndex
        buffer.resetReaderIndex() ;
        // 继续写入数据
        buffer.writeBytes("d".getBytes()) ;
        // 再次读取
        byte command2 = buffer.readByte();
        int length2 = buffer.readInt();
        byte [] body = new byte[length2] ;
        log.info("====> command2: {}, length2: {}, readableBytes :{}", command2, length2, buffer.readableBytes());
        buffer.readBytes(body) ;
        log.info("body value : {}, readableBytes :{}", new String(body), buffer.readableBytes());
        // 标记读取位置
        buffer.markReaderIndex() ;

    }


}
