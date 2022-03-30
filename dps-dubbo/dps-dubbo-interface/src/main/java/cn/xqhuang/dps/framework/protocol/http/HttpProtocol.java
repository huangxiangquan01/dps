package cn.xqhuang.dps.framework.protocol.http;

import cn.xqhuang.dps.framework.Invocation;
import cn.xqhuang.dps.framework.protocol.Protocol;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class HttpProtocol implements Protocol {

    @Override
    public String send(URL url, Invocation invocation) {
        return new HttpClient().send(url, invocation);
    }

    @Override
    public void start(URL url) {
        new HttpServer().start(url.getHost(), url.getPort());
    }
}
