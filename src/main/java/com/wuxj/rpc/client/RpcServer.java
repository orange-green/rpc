package com.wuxj.rpc.client;

import com.wuxj.rpc.serializer.ObjectSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  rpc服务端
 * </p>
 *
 * @author wuxj
 * @since 2024/6/26 16:25:43
 */

public class RpcServer {
    // 服务端socket
    private final ServerSocket serverSocket;

    // 方法列表
    private final Map<String, Method> methodMap = new HashMap<>();

    public RpcServer(int port) throws IOException {
        // 指定监听端口，ServerSocket仅支持监听本机
        this.serverSocket = new ServerSocket(port);
    }

    // 初始化有哪些可供调用的方法
    public void initMethodMap(Class<?>[] classes){
        for (Class clazz: classes) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                methodMap.put(method.getName(), method);
            }
        }
        System.out.println("方法注册完成");
    }


    private Object invoke(RpcRequest request)  {
        try{
            String className = request.getClassName();
            Class<?> aClass = Class.forName(className);
            Object instance = aClass.newInstance();
            Method method = methodMap.get(request.getMethodName());

            Object result = method.invoke(instance, request.getParams());
            return result;
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void start() {
        try{
          // 开始socket监听
          System.out.println("socket监听开始");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try{
                        // 获取监听到的socket数据
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        byte[] data = (byte[]) objectInputStream.readObject();
                        // 反序列化,预定请求体安装RpcRequest格式组织数据，所以能反序列化为RpcRequest对象
                        RpcRequest request = (RpcRequest)ObjectSerializer.deserialize(data);
                        System.out.println("收到消息：" + request.toString());
                        // 调用方法
                        Object result = invoke(request);

                        // 发送方法调用的结果
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(request);

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        catch (Exception e) {

        }
    }


    public void close() throws IOException {

        serverSocket.close();

    }
}
