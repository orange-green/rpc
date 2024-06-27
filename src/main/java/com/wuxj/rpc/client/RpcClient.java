package com.wuxj.rpc.client;

import com.wuxj.rpc.serializer.ObjectSerializer;

import java.io.*;
import java.net.Socket;

/**
 * <p>
 *  rpc 客户端
 * </p>
 *
 * @author wuxj
 * @since 2024/6/26 16:07:34
 */

public class RpcClient {

    //socket 对象用来进行网络数据传输
    private final Socket socket;

    public RpcClient(String host, int port ) throws IOException {
        this.socket = new Socket(host, port);
    }

    public Object invoke(String className, String methodName, Class<?>[] paramTypes, Object[] params) {
        try{
            // 创建统一网络请求对象
            RpcRequest rpcRequest = new RpcRequest(className, methodName, paramTypes, params);

            // 序列化
            byte[] requestData = ObjectSerializer.serialize(rpcRequest);

            // 通过socket发送数据
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(requestData);

            // socket接收数据
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 指定为字节数组
            byte[] data = (byte[])objectInputStream.readObject();
            // 反序列化成java对象
            Object object = ObjectSerializer.deserialize(data);
            return object;


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
