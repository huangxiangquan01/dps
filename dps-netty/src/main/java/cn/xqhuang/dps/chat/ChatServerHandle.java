package cn.xqhuang.dps.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Objects;

/**
 * 自定义Handler需要继承netty规定好的某个HandlerAdapter(规范)
 */
public class ChatServerHandle extends SimpleChannelInboundHandler<String> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel channel = ctx.channel();
        channels.add(channel);

        if (!Objects.equals("HelloServer", msg)) {
            System.out.println(channel.remoteAddress() + ":" + msg);
            channels.forEach(ch -> {
                if (ch == channel) {
                    ch.writeAndFlush("自己说:" + msg);
                } else {
                    ch.writeAndFlush(channel.remoteAddress() + "说:" + msg);
                }
            });
        } else {
            channels.forEach(ch -> {
                if (ch != channel) {
                    ch.writeAndFlush(channel.remoteAddress() + "上线了");
                } else {
                    System.out.println(channel.remoteAddress() + "上线了");
                }
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
