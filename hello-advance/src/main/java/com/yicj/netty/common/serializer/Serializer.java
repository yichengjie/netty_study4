package com.yicj.netty.common.serializer;

public interface Serializer {

    /**
     * JSON 序列化
     */
    byte JSON_SERIALIZER = 1 ;

    Serializer DEFAULT = new JSONSerializer() ;

    /**
     * 序列化算法
     * @return 序列化算法
     */
    byte getSerializerAlgorithm() ;

    /**
     * Java 对象转成二进制数据
     * @param object
     * @return
     */
    byte [] serialize(Object object) ;

    /**
     * 二进制数据转成Java对象
     * @param clazz java对象类型
     * @param bytes 二进制数据
     * @param <T> 参数化类型
     * @return java对象
     */
    <T> T deserialize(Class<T> clazz, byte [] bytes) ;
}
