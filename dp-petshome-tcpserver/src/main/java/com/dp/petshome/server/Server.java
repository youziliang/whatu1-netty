package com.dp.petshome.server;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description netty服务器引导程序/单例
 * @author DU
 */
@Component("server")
public class Server {

	/**
	 * 服务器端口
	 */
	public static int port = 12345;

	@Autowired
	private ServerChannelInitializer serverChannelInitializer;

	@PostConstruct
	public void serverBoot() {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
								.channel(NioServerSocketChannel.class)
								.option(ChannelOption.SO_BACKLOG, 2048)
								.childHandler(serverChannelInitializer);

			// 绑定端口，同步等待成功
			ChannelFuture future = bootstrap.bind(port).sync();

			// 等待服务器监听端口关闭
			future.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
