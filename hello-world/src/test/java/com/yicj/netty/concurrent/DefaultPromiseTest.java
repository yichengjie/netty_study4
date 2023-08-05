package com.yicj.netty.concurrent;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

/**
 * @author yicj
 * @date 2023年08月05日 13:04
 */
@Slf4j
public class DefaultPromiseTest {

    @Test
    public void hello() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup() ;
        //EventLoop executor = eventExecutors.next();
        //EventExecutor executor = new DefaultEventExecutor(group);
        EventExecutor executor = new SelfEventExecutor() ;
        DefaultPromise<String> promise = new DefaultPromise<>(executor) ;
        promise.addListener(new FutureListener<String>() {
            @Override
            public void operationComplete(Future<String> future) throws Exception {
                System.out.println("执行完成结果：" + future.get());
            }
        });
        //
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    promise.setSuccess("hello world!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
        promise.sync();
    }


    static class SelfEventExecutor extends SingleThreadEventExecutor {

        public SelfEventExecutor() {
            this(null);
        }

        public SelfEventExecutor(EventExecutorGroup parent) {
            this(parent, new DefaultThreadFactory(SelfEventExecutor.class));
        }

        public SelfEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory) {
            super(parent, threadFactory, true);
        }

        @Override
        protected void run() {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
            }
        }
    }
}
