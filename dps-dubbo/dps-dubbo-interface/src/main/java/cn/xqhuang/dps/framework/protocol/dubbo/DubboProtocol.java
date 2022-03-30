package cn.xqhuang.dps.framework.protocol.dubbo;

import cn.xqhuang.dps.framework.Invocation;
import cn.xqhuang.dps.framework.protocol.Protocol;

import java.net.URL;

public class DubboProtocol implements Protocol {

    @Override
    public String send(URL url, Invocation invocation) {
        return new NettyClient().send(url, invocation);
    }

    @Override
    public void start(URL url) {
        new NettyServer().start(url);
    }
}
