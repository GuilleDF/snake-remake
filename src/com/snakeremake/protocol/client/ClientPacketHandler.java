package com.snakeremake.protocol.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientPacketHandler extends SimpleChannelInboundHandler<String> {
    

    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
    
        System.out.println("EXCEPTION: " + cause.getMessage());
    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.println("Received from server: "+msg);
	}
	
    
}