package com.xing.smart.main;

import java.net.InetSocketAddress;

import com.xing.smart.exporter.RpcImport;
import com.xing.smart.importer.RpcExporter;
import com.xing.smart.service.EchoService;
import com.xing.smart.service.impl.EchoServiceImpl;

/**
 * 介绍完毕
 * 
 * @author xingliangbo
 * @version $Id: Application.java, v 0.1 2016年1月25日 下午11:46:35 xingliangbo Exp $
 */
public class Application {

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    RpcExporter.exporter("localhost", 8080);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        RpcImport<EchoService> importer = new RpcImport<EchoService>();
        EchoService echoService = importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8080));
        System.out.println(echoService.echo("哥帅不帅 ? "));
    }
}
