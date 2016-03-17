package com.game.core.net;

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
    	Message msgMessage = new Message();
    	msgMessage.setName("connected");
    	/* 	final ChannelFuture f = ctx.writeAndFlush(msgMessage); // (3)
  
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
        */
	}

    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
    	
    	Message msg1 = (Message)msg;
    	System.out.println(msg1.getName());
    	Message msgMessage = new Message();
    	msgMessage.setName("from server");
    	ctx.writeAndFlush(msgMessage);
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
