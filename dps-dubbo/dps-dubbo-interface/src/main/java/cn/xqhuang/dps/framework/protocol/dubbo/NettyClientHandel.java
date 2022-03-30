package cn.xqhuang.dps.framework.protocol.dubbo;

import cn.xqhuang.dps.framework.Invocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class NettyClientHandel extends ChannelInboundHandlerAdapter implements Callable<String>{

    private Channel channel;
    private Invocation invocation;
    private String res;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public NettyClientHandel(Invocation invocation) {
        this.invocation = invocation;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        channel.writeAndFlush(invocation);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.res = (String) msg;
        countDownLatch.countDown();
    }

    @Override
    public String call() throws Exception {
        countDownLatch.await();
        return res;
    }
}
