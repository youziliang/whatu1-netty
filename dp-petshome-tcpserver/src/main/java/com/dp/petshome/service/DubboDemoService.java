package com.dp.petshome.service;

import java.util.List;
import com.dp.petshome.persistence.model.User;

/**
 * @Description 服务器Channel通道初始化设置
 * @author DU
 */
public interface DubboDemoService {

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	Boolean insertUser(User user);

	/**
	 * 查看用户
	 * 
	 * @return
	 */
	List<User> selectUsers();
}
