package com.xing.smart.importer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 讲客户端发送的反序列化成对象，调用方法处理结果写回客户端。关闭连接 结束 ok了
 * 
 * @author xingliangbo
 * @version $Id: ExporterTask.java, v 0.1 2016年1月25日 下午11:33:04 xingliangbo Exp $
 */
public class ExporterTask implements Runnable {

    Socket client = null;

    public ExporterTask(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;

        try {
            input = new ObjectInputStream(client.getInputStream());
            String interfaceName = input.readUTF();
            Class<?> service = Class.forName(interfaceName);
            String methodName = input.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
            Object[] parameters = (Object[]) input.readObject();
            Method method = service.getMethod(methodName, parameterTypes);
            Object result = method.invoke(service.newInstance(), parameters);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
