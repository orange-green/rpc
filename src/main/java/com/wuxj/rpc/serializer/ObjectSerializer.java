package com.wuxj.rpc.serializer;

import java.io.*;

/**
 * <p>
 *  序列化类
 * </p>
 *
 */

public class ObjectSerializer {

    // 序列化， java对象 -> 字节数据
    public static byte[] serialize(Object object) throws IOException {
        // 构建内存到磁盘/网络的输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(object);
        return bos.toByteArray();
    }


    // 反序列化 字节数据 -> java对象
    public static  Object deserialize(byte[] data) throws IOException, ClassNotFoundException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();


        return object;
    }

}
