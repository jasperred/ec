package com.sunmw.web.bean.module;

import java.util.List;
import java.util.Map;

import com.sunmw.web.entity.ModuleConfig;

public interface ModuleServices {

	/**
	 * 新增和修改菜单
	 * @param param
	 * @return
	 */
	public String saveModule(Map param,String userLoginId);
	
	/**
	 * 删除菜单
	 * @param moduleId
	 * @return
	 */
	public Map deleteModule(int moduleId,String userLoginId);
	
	/**
	 * 得到菜单信息
	 * @param moduleId
	 * @return
	 */
	public ModuleConfig getModuleConfigInfo(int moduleId);
	
	/**
	 * 得到菜单列表
	 * @return
	 */
	public List getMenuList();
}
