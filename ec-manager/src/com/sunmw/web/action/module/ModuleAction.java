package com.sunmw.web.action.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.module.ModuleServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.ModuleConfig;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class ModuleAction {
	
	private ModuleServices moduleServices;
	
	private int moduleId;
	private int parentModuleId;
	private int sequence;
	private String moduleName;
	private String moduleAlias;
	private String showMenu;
	private String url;
	private Map moduleMap;
	private String message;
	private boolean success;
	private String crumb;
	
	private List menuList;
	
	public ModuleServices getModuleServices() {
		return moduleServices;
	}

	public void setModuleServices(ModuleServices moduleServices) {
		this.moduleServices = moduleServices;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getParentModuleId() {
		return parentModuleId;
	}

	public void setParentModuleId(int parentModuleId) {
		this.parentModuleId = parentModuleId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleAlias() {
		return moduleAlias;
	}

	public void setModuleAlias(String moduleAlias) {
		this.moduleAlias = moduleAlias;
	}

	public String getShowMenu() {
		return showMenu;
	}

	public void setShowMenu(String showMenu) {
		this.showMenu = showMenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List getMenuList() {
		return menuList;
	}

	public void setMenuList(List menuList) {
		this.menuList = menuList;
	}

	public Map getModuleMap() {
		return moduleMap;
	}

	public void setModuleMap(Map moduleMap) {
		this.moduleMap = moduleMap;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	//新增和保存菜单
	public String saveModule()
	{
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			String userNo = ul.getUserNo();		
			if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
			{
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			Map param = new HashMap();
			param.put("moduleId", moduleId);
			param.put("parentModuleId", parentModuleId);
			param.put("sequence", sequence);
			param.put("moduleName", WebUtil.filterSpecialCharacters(moduleName));
			param.put("moduleAlias", WebUtil.filterSpecialCharacters(moduleAlias));
			param.put("showMenu", WebUtil.filterSpecialCharacters(showMenu));
			param.put("url", WebUtil.filterSpecialCharacters(url));
			String s = moduleServices.saveModule(param, ""+ul.getId());
			if("success".equals(s))
			{
				message = null;
				success = true;
			}
			else
			{
				message = s;
				success = false;
			}
		} catch (Exception e) {
			message = e.getMessage();
			success = false;
		}
		return "success";
	}
	
	//删除菜单
	public String deleteModule()
	{
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			String userNo = ul.getUserNo();		
			if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
			{
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			Map<String,String> m = moduleServices.deleteModule(moduleId, ""+ul.getId());
			if("success".equals(m.get("flag")))
			{
				message = null;
				success = true;
			}
			else
			{
				message = m.get("message");
				success = false;
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}
	
	//得到菜单信息
	public String getModuleInfo()
	{
		try {
			if(moduleId==0)
			{
				parentModuleId = 0;
				sequence = 0;
				moduleName = null;
				moduleAlias = null;
				showMenu = null;
				url = null;
			}
			else
			{
				ModuleConfig mc = moduleServices.getModuleConfigInfo(moduleId);
				if(mc!=null)
				{
					parentModuleId = mc.getParentModuleId();
					sequence = mc.getSequence();
					moduleName = mc.getModuleName();
					moduleAlias = mc.getModuleAlias();
					showMenu = mc.getShowMenu();
					url = mc.getUrl();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	//显示菜单页
	public String viewModule()
	{
		return "success";
	}

	//得到菜单列表
	public String menuList()
	{
		try {
			this.menuList = moduleServices.getMenuList();
			moduleMap = jsonModuleList(menuList);
		} catch (Exception e) {
			this.menuList = null;
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
	public Map jsonModuleList(List<Map> menuList) throws Exception
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
				rootMap.put("id",mc.getId());
				rootMap.put("moduleId",mc.getId());
				rootMap.put("parentModuleId",mc.getParentModuleId());
				rootMap.put("moduleName",mc.getModuleName());
				rootMap.put("moduleAlias",mc.getModuleAlias());
				rootMap.put("moduleUrl",mc.getUrl());
				rootMap.put("showMenu",mc.getShowMenu());
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
						leafMap.put("id",lf.getId());
						leafMap.put("moduleId",lf.getId());
						leafMap.put("parentModuleId",lf.getParentModuleId());
						leafMap.put("moduleName",lf.getModuleName());
						leafMap.put("moduleAlias",lf.getModuleAlias());
						leafMap.put("moduleUrl",lf.getUrl());
						leafMap.put("showMenu",lf.getShowMenu());
						leafMap.put("sequence",lf.getSequence());
						leafMap.put("leaf", true);
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
