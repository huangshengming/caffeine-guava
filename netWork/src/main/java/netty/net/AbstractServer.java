package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import net.build.IBuild;

public abstract class AbstractServer {
    public int bossGroupCount;
    public int workerGroupCount;

    public int port;
    public ServerBootstrap serverBootstrap;
    public NioEventLoopGroup bossGroup;
    public NioEventLoopGroup workerGroup;


    public abstract void initHandler();

    public AbstractServer(Builder builder){
        this.bossGroupCount = builder.bossGroupCount;
        this.bossGroup = builder.bossGroup;
        this.port = builder.port;
        this.serverBootstrap = builder.serverBootstrap;
        this.workerGroup = builder.workerGroup;
        this.workerGroupCount = builder.workerGroupCount;
    }


    public class Builder implements IBuild {

        public int bossGroupCount;
        public int workerGroupCount;
        public int port;

        public ServerBootstrap serverBootstrap;
        public NioEventLoopGroup bossGroup;
        public NioEventLoopGroup workerGroup;

        public AbstractServer build(Builder builder){
            return new RpcServer(builder);
        }

        public IBuild setBossGroupCount(int bossGroupCount) {
            this.bossGroupCount = bossGroupCount;
            return this;
        }

        public IBuild setWorkerGroupCount(int workerGroupCount) {
            this.workerGroupCount = workerGroupCount;
            return this;
        }

        public IBuild setPort(int port) {
            this.port = port;
            return this;
        }

        public IBuild setServerBootstrap(ServerBootstrap serverBootstrap) {
            this.serverBootstrap = serverBootstrap;
            return this;
        }

        public IBuild setBossGroup(NioEventLoopGroup bossGroup) {
            this.bossGroup = bossGroup;
            return this;
        }

        public IBuild setWorkerGroup(NioEventLoopGroup workerGroup) {
            this.workerGroup = workerGroup;
            return this;
        }

    }
}
