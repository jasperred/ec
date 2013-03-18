package com.sunmw.web.action.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.login.SecurityPermissionServices;
import com.sunmw.web.entity.ModuleConfig;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.JsonUtil;
import com.sunmw.web.util.WebUtil;
/**
 * 登录用户的菜单列表
 * @author sun
 *
 */
public class MenuAction {
	private SecurityPermissionServices securityPermissionServices;
	private Map userMenuMap;
	private List userMenuList;

	public SecurityPermissionServices getSecurityPermissionServices() {
		return securityPermissionServices;
	}

	public void setSecurityPermissionServices(
			SecurityPermissionServices securityPermissionServices) {
		this.securityPermissionServices = securityPermissionServices;
	}

	public List getUserMenuList() {
		return userMenuList;
	}

	public void setUserMenuList(List userMenuList) {
		this.userMenuList = userMenuList;
	}

	public Map getUserMenuMap() {
		return userMenuMap;
	}

	public void setUserMenuMap(Map userMenuMap) {
		this.userMenuMap = userMenuMap;
	}

	public String userMenu() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			userMenuList = securityPermissionServices.getLoginMenu(param);
			userMenuMap = jsonMenuList(userMenuList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 将菜单列表整理为JSON格式
	 * 2层菜单
	 * @param menuList
	 * @return
	 * @throws Exception 
	 */
	public Map jsonMenuList(List<Map> menuList) throws Exception
	{
		Map jsonMap = new HashMap();
		jsonMap.put("text", ".");
		//根菜单列表，不可点击
		List rootList = new ArrayList();
		if(!WebUtil.isNullForList(menuList))
		{
			int i = 0;
			for(Map root:menuList)
			{
				i++;
				Map rootMap = new HashMap();
				ModuleConfig mc = (ModuleConfig)root.get("info");
				List<Map> subList = (List) root.get("subList");
				rootMap.put("text",mc.getModuleName());
				rootMap.put("sequence",mc.getSequence());
				//第一个菜单展开
				if(i==1)
					rootMap.put("expanded",true);
				List leafList = new ArrayList();
				if(!WebUtil.isNullForList(subList))
				{
					for(Map leaf:subList)
					{
						ModuleConfig lf = (ModuleConfig)leaf.get("info");
						Map leafMap = new HashMap();
						leafMap.put("text", lf.getModuleName());
						leafMap.put("id", lf.getId());
						leafMap.put("leaf", true);
						leafMap.put("iframe", true);
						leafMap.put("url", lf.getUrl()+"?permissionId="+lf.getPermissionId());
						leafMap.put("sequence",lf.getSequence());
						leafList.add(leafMap);
					}
				}
				rootMap.put("children",leafList);				
				rootList.add(rootMap);
			}
		}
		jsonMap.put("children", rootList);		
		return jsonMap;
	}

}
