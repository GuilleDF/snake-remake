package com.snakeremake.protocol.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	//Y esto es el cliente, bastante igual que el servidor solo que este no se queda
	//congelado al conectarse por lo que uso un simple metodo keepAlive() que ahora veras
	public static Client inst = null;
	public static Client inst(){
		return inst;
	}
	
	public static void setUpClient(String adress,int port){
		inst = new Client(adress,port);
		inst.keepAlive();
	}
	
	

	//END OF STATIC STUFF
	private NioEventLoopGroup group;
	private Bootstrap client;
	private Channel channel;
	
	public Client(String address, int port){
		group = new NioEventLoopGroup();
        
        try {
            client = new Bootstrap().group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());
            
            channel = client.connect(address, port).sync().channel();
        } catch (Exception ex) {
        }
	}
	private void keepAlive() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			sendMessage("a");
		}
		
	}
	private void sendMessage(String s){
		channel.writeAndFlush(s+"\r\n");
	}
}
