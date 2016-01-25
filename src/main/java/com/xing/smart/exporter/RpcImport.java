package com.xing.smart.exporter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
/**
 * 生成动态代理 创建客户端 连接到服务提供者请求服务，发送参数 请求响应 
 * 比如 你要做什么特殊服务，妹子的三围，年龄 等等 总要告诉 夜总会把（服务暴露），然后他安排具体的美女给你特殊服务
 * 
 * @author xingliangbo
 * @version $Id: RpcImport.java, v 0.1 2016年1月25日 下午11:40:11 xingliangbo Exp $
 */
public class RpcImport<S> {

    @SuppressWarnings("unchecked")
    public S importer(final Class<?> serviceClass, final InetSocketAddress addr) {
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(),
            new Class<?>[] { serviceClass.getInterfaces()[0] }, new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Socket socket = null;
                    ObjectOutputStream output = null;
                    ObjectInputStream input = null;
                    try {
                        socket = new Socket();
                        socket.connect(addr);
                        output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeUTF(serviceClass.getName());
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);
                        input = new ObjectInputStream(socket.getInputStream());
                        return input.readObject();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (socket != null) {
                            socket.close();
                        }
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    }
                    return null;
                }
            });
    }
}
