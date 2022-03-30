package cn.xqhuang.dps.framework.protocol.dubbo;

import cn.xqhuang.dps.framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    public NettyClientHandel handel = null;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(URL url, Invocation invocation) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            handel = new NettyClientHandel(invocation);
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())));
                            channel.pipeline().addLast("encoder", new ObjectEncoder());
                            channel.pipeline().addLast(handel);
                        }
                    });
            System.out.println("netty client start");
            bootstrap.connect(url.getHost(), url.getPort()).sync();
        }catch (Exception e) {

        }
    }

    public String send(URL url, Invocation invocation){
        if (handel == null) {
            start(url, invocation);
        }
        try {
            return executorService.submit(handel).get();
        }catch (Exception e) {

        }
        return null;
    }

}
