package cn.xqhuang.dps.framework.protocol;

import cn.xqhuang.dps.framework.Invocation;

import java.net.URL;

public interface Protocol {

    String send(URL url, Invocation invocation);

    void start(URL url);
}
