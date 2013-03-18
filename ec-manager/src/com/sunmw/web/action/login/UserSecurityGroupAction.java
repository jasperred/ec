package com.sunmw.web.action.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.login.SecurityPermissionServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.SecurityGroup;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class UserSecurityGroupAction {
	
	private SecurityPermissionServices securityPermissionServices;
	private int userId;
	private String groupIds;
	private List<Map> userSecurityGroupList;
	private List<SecurityGroup> securityGroupList;
	private Map userSecurityGroupMap;
	private String message;
	private boolean success;
	private String crumb;
	
	public SecurityPermissionServices getSecurityPermissionServices() {
		return securityPermissionServices;
	}

	public void setSecurityPermissionServices(
			SecurityPermissionServices securityPermissionServices) {
		this.securityPermissionServices = securityPermissionServices;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public Map getUserSecurityGroupMap() {
		return userSecurityGroupMap;
	}

	public void setUserSecurityGroupMap(Map userSecurityGroupMap) {
		this.userSecurityGroupMap = userSecurityGroupMap;
	}

	public List getUserSecurityGroupList() {
		return userSecurityGroupList;
	}


	public List getSecurityGroupList() {
		return securityGroupList;
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

	public void setUserSecurityGroupList(List<Map> userSecurityGroupList) {
		this.userSecurityGroupList = userSecurityGroupList;
	}

	public void setSecurityGroupList(List<SecurityGroup> securityGroupList) {
		this.securityGroupList = securityGroupList;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String userSecurityGroupList()
	{
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			this.userSecurityGroupList = this.securityPermissionServices.userLoginSecurityGroupList(userId);
			groupIds = "";
			if(this.userSecurityGroupList!=null)
			{
				for(Map m:this.userSecurityGroupList)
				{
					if(groupIds.length()>0)
						groupIds += ",";
					groupIds += WebUtil.replaceSpecialCharacters((String)m.get("groupId"));
				}
			}
			this.securityGroupList = this.securityPermissionServices.securityGroupList(ul);
			processSecurityGroupPermissionMap();
		} catch (Exception e) {
			this.userSecurityGroupList = null;
			this.securityGroupList = null;
		}
		return "success";
	}
	private void processSecurityGroupPermissionMap()
	{
		if(this.userSecurityGroupMap==null)
			this.userSecurityGroupMap = new HashMap();
		userSecurityGroupMap.clear();
		Map mm = new HashMap();
		//整理权限组的已有权限
		if(!WebUtil.isNullForList(this.userSecurityGroupList))
		{
			for(Map sgp:userSecurityGroupList)
			{
				mm.put(sgp.get("groupId"), sgp);
			}
		}
		//暂时只处理2层菜单
		userSecurityGroupMap.put("text", ".");
		List rootList = new ArrayList();
		Map<String,Map> rootMap = new HashMap();
		if(!WebUtil.isNullForList(this.securityGroupList))
		{
			for(SecurityGroup sp:securityGroupList)
			{
				
					Map r = rootMap.get(sp.getId());
					if(r==null)
						r = new HashMap();
					r.put("id", sp.getId());
					r.put("text", sp.getDescription());
					r.put("expanded", true);
					r.put("securityGroupId", sp.getId());
					r.put("securityGroupName", sp.getDescription());
					r.put("leaf", true);
					if(WebUtil.isNotNull(mm.get(sp.getId())))
					{
						r.put("checked", true);
					}
					else
					{
						r.put("checked", false);
					}
					rootMap.put(sp.getId(), r);
				
			}
		}
		Iterator iter = rootMap.keySet().iterator();
		while(iter.hasNext())
		{
			rootList.add(rootMap.get(iter.next()));
		}
		userSecurityGroupMap.put("children", rootList);		
	}
	
	public String saveUserSecurityGroup()
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
			String m = this.securityPermissionServices.saveUserSecurityGroup(userId, groupIds);
			message = m;
			success = true;
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}

}
