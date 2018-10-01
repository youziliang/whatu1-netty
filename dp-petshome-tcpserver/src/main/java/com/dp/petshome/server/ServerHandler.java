package com.dp.petshome.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dp.petshome.persistence.dto.ChannelCache;
import com.dp.petshome.service.DubboDemoService;
import com.dp.petshome.service.TestTask;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Description 服务端消息处理
 * @author DU
 */
@Component("serverHandler")
@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

	@Autowired
	protected DubboDemoService dubboDemoService;

	@Autowired
	protected ChannelCache channelCache;

	@Autowired
	protected TestTask testTask;

	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

	/**
	 * 获取现有通道，一个通道channel就是一个socket链接在这里
	 */
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * @Description 消息读取有两个方法：channelRead和channelRead0，其中常用的channelRead0可以读取泛型
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		log.info("接收到客户端的信息: " + msg);
		log.info("获取的ChannelHandler上下文对象: " + ctx);
		for (Channel channel : channels) {
			log.info("当前的连接channel: " + channel);
		}

		// TODO 消息转发业务

		// TODO 游戏逻辑业务
		// List<User> selectUsers = dubboDemoService.selectUsers();
		// ctx.channel().writeAndFlush("已收到客户端的消息: " + selectUsers);
		testTask.testTimeTask(ctx.channel().id());
	}

	/**
	 * @Description 新客户端接入
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelActive: " + ctx.channel().remoteAddress());
		channels.add(ctx.channel());

		// 此处保存与客户端的连接
		channelCache.saveChannel(ctx.channel().id(), ctx.channel());
	}

	/**
	 * @Description 客户端断开
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelInactive: " + ctx.channel().remoteAddress());
		channels.remove(ctx.channel());
		channelCache.removeChannel(ctx.channel().id());
	}

	/**
	 * @Description 异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error(cause.getMessage());
	}

}