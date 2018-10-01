package com.dp.petshome.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description 服务器Channel通道初始化设置
 * @author DU
 */
@Component("serverChannelInitializer")
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	protected ServerHandler serverHandler;

	/**
	 * 加了这个过滤器之后，传输的内容要以"\n"结尾
	 */
	private DelimiterBasedFrameDecoder delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(8192,
			Delimiters.lineDelimiter());

	private StringDecoder decoder = new StringDecoder();

	private StringEncoder encoder = new StringEncoder();

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();

		pipeline.addLast("framer", delimiterBasedFrameDecoder);
		// 字符串解码和编码
		pipeline.addLast("decoder", decoder);
		pipeline.addLast("encoder", encoder);
		// 业务逻辑
		pipeline.addLast("handler", serverHandler);
	}
}
