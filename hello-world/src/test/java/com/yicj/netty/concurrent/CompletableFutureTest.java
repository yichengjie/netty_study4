package com.yicj.netty.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author yicj
 * @date 2023年08月05日 13:57
 */
@Slf4j
public class CompletableFutureTest {

    @Test
    public void hello() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("----------"));
        future.get() ;
    }
}
