package com.yicj.hello.http;

/**
 * @author yicj
 * @date 2023年07月17日 10:08
 */
public class Application {
    public static void main(String[] args) throws Exception{
        HttpServer server = new HttpServer(8081);// 8081为启动端口
        server.start();
    }
}
