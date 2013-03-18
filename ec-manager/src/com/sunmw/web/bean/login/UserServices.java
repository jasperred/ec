package com.sunmw.web.bean.login;

import java.util.List;
import java.util.Map;

public interface UserServices {
	/**
	 * 新建用户
	 * 
	 * @param userLogin
	 * @return
	 */
	public Map<String, String> createUserLogin(Map<String, Object> userLogin);

	/**
	 * 用户信息修改
	 * 
	 * @param userLogin
	 * @return
	 */
	public Map<String, String> updateUserLogin(Map<String, Object> userLogin);

	/**
	 * 修改密码
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, String> modifyPassowd(Map param);
	
	/**
	 * 用户查询
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchUser(Map param, int currentPage, int pageRow);
	
	/**
	 * 用户信息
	 * @param userName
	 * @return
	 */
	public Map getUserInfo(int userId);
	
	/**
	 * 根据UserNo修改密码，不需要登录
	 * @param param
	 * @return
	 */
	public Map<String, String> modifyPassowdByUserNo(Map param);
	
	/**
	 * 重设密码
	 * @param param
	 * @return
	 */
	public Map<String, String> resetPassowd(Map param);
	
	/**
	 * 用户导航列表
	 * @param param
	 * @return
	 */
	public List navigateListByUser(Map param);
}
