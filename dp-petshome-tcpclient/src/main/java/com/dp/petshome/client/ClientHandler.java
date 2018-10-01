package com.dp.petshome.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description 客户端消息处理
 * @author DU
 */
@Component("clientHandler")
@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		log.info("服务端回复:" + msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		log.info("Client Active: " + ctx.channel().remoteAddress());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		log.info("Client Close: " + ctx.channel().remoteAddress());
	}

}
