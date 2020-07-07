package net;


import handler.SessionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RpcServer extends AbstractServer {

    private final Logger LOG = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer(Builder builder){
        super(builder);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class);

        // 设置tcp 参数
        this.setOption();

        // 设置处理消息的handler
        this.initHandler();
    }

    @Override
    public void initHandler() {
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 2, -2, 0, 2, true));
                pipeline.addLast(new LengthFieldPrepender(2, true));

                pipeline.addLast(new SessionHandler());
            }
        });
    }

    public void start(int port){
        try {
            Future future = serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            LOG.error("service start failed");
        }
    }

    public void setOption(){
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
    }

    public void stop() {
        io.netty.util.concurrent.Future<?> bf = this.bossGroup.shutdownGracefully();
        io.netty.util.concurrent.Future wf = this.workerGroup.shutdownGracefully();

        try {
            bf.get(5000L, TimeUnit.MILLISECONDS);
            wf.get(5000L, TimeUnit.MILLISECONDS);
        } catch (Exception var4) {
            LOG.info("Netty服务器关闭失败", var4);
        }

        LOG.info("Netty Server on port:{} is closed", this.port);
    }
}
