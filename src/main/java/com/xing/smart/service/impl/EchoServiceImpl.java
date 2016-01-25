package com.xing.smart.service.impl;

import com.xing.smart.service.EchoService;

public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String ping) {
        return ping != null ? ping + " -- > 帅，非常帅 " : "帅";
    }

}
