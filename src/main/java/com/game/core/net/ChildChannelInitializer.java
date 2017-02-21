package com.game.core.net;

import com.game.core.coder.MessageDecoder;
import com.game.core.coder.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChildChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new MessageDecoder());
		ch.pipeline().addLast(new MessageEncoder());
		ch.pipeline().addLast(new GameServerHandler());
	}

}
