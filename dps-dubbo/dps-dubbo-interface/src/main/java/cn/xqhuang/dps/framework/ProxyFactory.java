package cn.xqhuang.dps.framework;


import cn.xqhuang.dps.framework.protocol.Protocol;
import cn.xqhuang.dps.framework.protocol.ProtocolFactory;
import java.lang.reflect.Proxy;
import java.net.URL;

public class ProxyFactory {

    public static <T> T getProxy(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass }, (proxy, method, args) -> {
            Invocation invocation = new Invocation(interfaceClass.getName(),
                    method.getName(), method.getParameterTypes(),args);

            Protocol protocol = ProtocolFactory.getProtocol(ProtocolFactory.ProtocolType.DUBBO);
            URL url = new URL("http", "localhost", 8088, "");
            return protocol.send(url, invocation);
        });
    }

}
