package net.build;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import net.AbstractServer;

public interface IBuild {

    IBuild setBossGroupCount(int bossGroupCount);
    IBuild setWorkerGroupCount(int workerGroupCount);
    IBuild setPort(int port);
    IBuild setServerBootstrap(ServerBootstrap serverBootstrap);
    IBuild setBossGroup(NioEventLoopGroup bossGroup);
    IBuild setWorkerGroup(NioEventLoopGroup workerGroup);

    AbstractServer build(AbstractServer.Builder build);
}
