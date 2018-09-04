package com.xiaoyang.netty.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;


/**
 * 非常基本的客户端和服务器
 *
 * @author WXY
 * @date 2018/8/31 16:58
 */
public class EchoServer {
    /**
     * 获取系统配置
     */
    private static final Boolean SSL = System.getProperty("ssl") != null;
    private static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    public static void main(String[] args) throws Exception {
        //SSL配置
        final SslContext sslContext;
        if (SSL) {
            SelfSignedCertificate signedCertificate = new SelfSignedCertificate();
            sslContext = SslContextBuilder.forServer(signedCertificate.certificate(), signedCertificate.privateKey()).build();
        } else {
            sslContext = null;
        }
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EchoServerHandler serverHandler = new EchoServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslContext != null) {
                                p.addLast(sslContext.newHandler(ch.alloc()));
                            }
                            p.addLast(serverHandler);
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
