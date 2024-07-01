package com.wuxj.rpc.client;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxj
 * @since 2024/6/26 16:14:59
 */

public class RpcResponse implements Serializable {

    private Object result;

    public RpcResponse(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "result=" + result +
                '}';
    }
}
