package com.xing.smart.importer;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * 作为服务端 当然得搞个高大上的 多线程了
 * 
 * @author xingliangbo
 * @version $Id: RpcExporter.java, v 0.1 2016年1月25日 下午11:31:07 xingliangbo Exp $
 */
public class RpcExporter {

    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 监听客户端的TCP连接，接收到连接后封装成Task，有线程池执行
     * 
     * @param hostName
     * @param port
     * @throws Exception
     */
    public static void exporter(String hostName, int port) throws Exception {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(hostName, port));
        try {
            while (true) {
                executor.execute(new ExporterTask(server.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }
    
    
}
