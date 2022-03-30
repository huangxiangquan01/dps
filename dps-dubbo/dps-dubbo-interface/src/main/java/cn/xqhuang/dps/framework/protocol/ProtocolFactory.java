package cn.xqhuang.dps.framework.protocol;

import cn.xqhuang.dps.framework.protocol.dubbo.DubboProtocol;
import cn.xqhuang.dps.framework.protocol.http.HttpProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProtocolFactory {

    public static Protocol getProtocol(ProtocolType type) {
        Protocol protocol = null;
        switch (type) {
            case HTTP:
                protocol = new HttpProtocol();
                break;
            case DUBBO:
                protocol = new DubboProtocol();
                break;
            default:
                protocol = null;
        }
        return protocol;
    }

    @Getter
    @AllArgsConstructor
    public enum ProtocolType{
        HTTP,
        DUBBO
    }
}
