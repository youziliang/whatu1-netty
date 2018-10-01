package com.dp.petshome.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description netty的客户端引导程序/单例
 * @author DU
 */
@Component("client")
public class Client {

	/**
	 * 服务器IP地址
	 */
	public static String host = "127.0.0.1";

	/**
	 * 服务器端口
	 */
	public static int port = 12345;

	@Autowired
	private ClientChannelInitializer clientChannelInitializer;

	@PostConstruct
	public void clientBoot() {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(clientChannelInitializer);

			// 连接客户端
			Channel channel = bootstrap.connect(host, port).sync().channel();

			// 控制台输入
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				channel.writeAndFlush(reader.readLine() + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放线程池资源
			group.shutdownGracefully();
		}
	}
}
