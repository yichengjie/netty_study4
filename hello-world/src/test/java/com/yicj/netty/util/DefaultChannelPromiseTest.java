package com.yicj.netty.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import org.junit.Test;

/**
 * @author yicj
 * @date 2023年08月05日 12:49
 */
public class DefaultChannelPromiseTest {

    @Test
    public void hello(){
        Channel channel = null;
        ChannelPromise channelPromise = new DefaultChannelPromise(channel);
    }
}
