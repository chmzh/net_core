package com.game.core.net;

import com.game.core.ControllerDispatcher;
import com.game.core.Message;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class GameServerHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
    	
	}

    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
    	
    	Message msg1 = (Message)msg;
    	
    	ControllerDispatcher.getInstance().doDispatcher(msg1.getControllerId(), msg1.getActionId(), msg1.getParams());
    	
/*    	System.out.println(msg1.getControllerId()+":"+msg1.getParams());
    	Message msgMessage = new Message();
    	msgMessage.setParams("from server");
    	ctx.writeAndFlush(msgMessage);*/
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
