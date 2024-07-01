package com.wuxj.rpc;

import com.wuxj.rpc.client.RpcClient;
import com.wuxj.rpc.client.RpcResponse;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/6/26 17:03:15
 */

public class RpcClientTest {

  public static void main(String[] args) throws IOException {

      RpcClient client = new RpcClient("localhost", 8081);
      String className = "com.wuxj.rpc.service.HelloServiceImpl";
      String methodName = "sayHello";

      RpcResponse response = client.invoke(className, methodName, new Class[]{String.class}, new Object[]{"test rpc"});
      //String result = (String)response.getResult();
      System.out.println(response);
  }
}
