package com.wuxj.rpc.client;

import com.wuxj.rpc.serializer.ObjectSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public RpcResponse invoke(String className, String methodName, Class<?>[] paramTypes, Object[] params) {
        RpcResponse response = new RpcResponse(null);

        try{
            // 创建统一网络请求对象
            RpcRequest rpcRequest = new RpcRequest(className, methodName, paramTypes, params);

            // 序列化
            byte[] requestData = ObjectSerializer.serialize(rpcRequest);

            // 通过socket发送数据
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(requestData);
            System.out.println("客户端写入数据");

            // socket接收数据
            //byte[] buffer = new byte[1024];



            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //byte[] data = socket.getInputStream().readAllBytes();

            Object object = objectInputStream.readObject();
            //byte[] data = objectInputStream.readAllBytes();
            //byte[] data = (byte[])objectInputStream.readObject();
            // 反序列化成java对象
            //Object object = ObjectSerializer.deserialize(data);
            response = (RpcResponse) object;
            System.out.println("客户端接收数据:" + response.toString());
            return response;


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    // 合并两个字节数组
    private static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
