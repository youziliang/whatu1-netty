package com.dp.petshome.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description netty客户端启动脚本
 * @author DU
 */
public class ClientBoot {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:spring/applicationContext.xml" });
		context.start();
	}
}
