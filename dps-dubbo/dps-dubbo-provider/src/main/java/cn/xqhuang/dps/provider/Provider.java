package cn.xqhuang.dps.provider;

import cn.xqhuang.dps.framework.protocol.Protocol;
import cn.xqhuang.dps.framework.protocol.ProtocolFactory;
import cn.xqhuang.dps.framework.register.LocalRegister;
import cn.xqhuang.dps.service.HelloService;
import cn.xqhuang.dps.service.impl.HelloServiceImpl;

import java.net.URL;

public class Provider {

    public static void main(String[] args) throws Exception{
        // 1. 注册服务
        // 2. 本地注册
        // 3. 启动tomcat

        // 注册服务
       //  URL url = new URL("localhost", 8080); //NetUtil
       //  RemoteMapRegister.regist(HelloService.class.getName(), url);

        //  服务：实现类
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

        Protocol protocol = ProtocolFactory.getProtocol(ProtocolFactory.ProtocolType.DUBBO);
        URL url = new URL("http", "localhost", 8088, "");

        protocol.start(url);

    }
}
