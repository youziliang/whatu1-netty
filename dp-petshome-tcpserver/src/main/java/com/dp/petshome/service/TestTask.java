package com.dp.petshome.service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dp.petshome.persistence.dto.ChannelCache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @Description 测试用例
 * @author DU
 */
@Component("testTask")
public class TestTask {

	@Autowired
	protected ChannelCache channelCache;

	ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {

		AtomicInteger threadCount = new AtomicInteger(1);
		SecurityManager securityManager = System.getSecurityManager();
		ThreadGroup threadGroup = (securityManager == null) ? Thread.currentThread().getThreadGroup()
				: securityManager.getThreadGroup();

		@Override
		public Thread newThread(Runnable runnable) {
			String name = "thread--" + threadCount.getAndIncrement();
			Thread thread = new Thread(threadGroup, runnable, name, 0);
			thread.setDaemon(false);
			return thread;
		}
	});

	public void testTimeTask(ChannelId channelId) {

		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				Channel channel = channelCache.getChannel(channelId);
				channel.writeAndFlush("服务器5秒钟后主动向客户端发来了这条信息..." + "\r\n");
			}
		}, 5, TimeUnit.SECONDS);
	}

}
