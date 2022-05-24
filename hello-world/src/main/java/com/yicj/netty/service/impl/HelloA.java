package com.yicj.netty.service.impl;

import com.yicj.netty.service.AbstractHello;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloA extends AbstractHello<String> {

    @Override
    public void hello(String input) {
        log.info("hello {}", input);
    }
}
