package com.dp.petshome.persistence.dto;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @Description 保存客户端和服务器之间的所有连接
 */
@Component("channelCache")
public class ChannelCache {

	// 保存客户端与服务器的channel连接
	private static volatile ConcurrentHashMap<ChannelId, Channel> channelMap = new ConcurrentHashMap<ChannelId, Channel>();

	public ConcurrentHashMap<ChannelId, Channel> saveChannel(ChannelId channelId, Channel channel) {
		channelMap.put(channelId, channel);
		return channelMap;
	}

	public void removeChannel(ChannelId id) {
		channelMap.remove(id);
	}

	public Channel getChannel(ChannelId channelId) {
		return channelMap.get(channelId);
	}
}
