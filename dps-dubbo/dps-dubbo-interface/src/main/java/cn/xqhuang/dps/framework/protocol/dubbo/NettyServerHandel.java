package cn.xqhuang.dps.framework.protocol.dubbo;

import cn.xqhuang.dps.framework.Invocation;
import cn.xqhuang.dps.framework.register.LocalRegister;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.lang.reflect.Method;

public class NettyServerHandel extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;
        Class clazz = LocalRegister.getClass(invocation.getInterfaceName());
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        String invoke = (String) method.invoke(obj, invocation.getParams());

        Channel channel = ctx.channel();
        channel.writeAndFlush(invoke);
    }
}
