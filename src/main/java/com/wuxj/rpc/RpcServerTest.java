package com.wuxj.rpc;

import com.wuxj.rpc.client.RpcServer;
import com.wuxj.rpc.service.HelloServiceImpl;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/6/26 17:02:07
 */

public class RpcServerTest {

  public static void main(String[] args) throws IOException {

      RpcServer rpcServer = new RpcServer(8081);
      rpcServer.initMethodMap(new Class[] {HelloServiceImpl.class});
      rpcServer.start();
  }
}
