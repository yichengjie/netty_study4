package com.yicj.netty.util;

import com.yicj.netty.service.AbstractHello;
import com.yicj.netty.service.impl.HelloA;
import io.netty.util.internal.TypeParameterMatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TypeParameterMatcherTest {

    @Test
    public void match(){
        AbstractHello hello = new HelloA() ;
        TypeParameterMatcher matcher = TypeParameterMatcher.find(hello, AbstractHello.class, "I");
        boolean flag = matcher.match("张三");
        log.info("flag : {}", flag);
    }

}
