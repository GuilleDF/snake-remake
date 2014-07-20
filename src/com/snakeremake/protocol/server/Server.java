package com.snakeremake.protocol.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	public static Server inst = null;
	public static Server inst(){
		return inst;
	}
	public static void setUpServer(String adress,int port){
		inst = new Server(adress,port);
	}
	private ServerBootstrap server;
	
	public Server(String adress, int port) {
		EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        
        try {
            server = new ServerBootstrap().group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer());            
            server.bind(port).sync().channel().closeFuture().sync();
        } catch (Exception ex) {
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
	}
}
