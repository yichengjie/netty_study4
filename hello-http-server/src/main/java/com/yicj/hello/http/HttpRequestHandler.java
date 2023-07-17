package com.yicj.hello.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import static io.netty.handler.codec.http.HttpUtil.is100ContinueExpected;

/**
 * @author yicj
 * @date 2023年07月17日 10:06
 */
@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    // ChannelInboundHandlerAdapter
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("channelReadComplete ... ");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        //100 Continue
        if (is100ContinueExpected(req)) {
            ctx.write(new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.CONTINUE));
        }
        // 获取请求的uri
        String uri = req.uri();
        String body = req.content().toString(Charset.defaultCharset());
        QueryStringDecoder paramDecoder = new QueryStringDecoder(body,false);
        Map<String, List<String>> parameters = paramDecoder.parameters();

        //log.info("body : {}", body);
        log.info("parameters : {}", parameters);

        parameters.forEach((key, value) -> {
            log.info("---> key : {}", key);
            log.info("---> value : {}", value);
        });

//        Map<String,String> resMap = new HashMap<>();
//        resMap.put("method",req.method().name());
//        resMap.put("uri",uri);
        String msg = "<html><head><title>test</title></head><body>你请求uri为：" + uri+" <br/> <p>"+body+"</p></body></html>";

        // 创建http响应
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        // 设置头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        //response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        // 将html write到客户端
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
