package com.yicj.hello.http;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yicj
 * @date 2023年07月17日 11:19
 */
@Slf4j
public class HelloTest {

    public static void main(String[] args) {
        String body = "{\n" +
                "\"username\":\"yicj\",\n" +
                "\"address\":\"bjs\"\n" +
                "}" ;
        String name = "username" ;
        Object value = JsonPath.read(body, name);
        System.out.println(value);
    }
}
