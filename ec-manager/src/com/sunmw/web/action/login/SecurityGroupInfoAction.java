package com.sunmw.web.action.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.login.SecurityPermissionServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.SecurityPermission;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class SecurityGroupInfoAction {
	private SecurityPermissionServices securityPermissionServices;
	
	private String groupId;
	private String groupName;
	private String companyId;
	private String level;
	private List<SecurityPermission> permissionList;
	private List<Map> securityGroupPermissionList;
	private Map securityGroupPermissionMap;
	private String permissionIds;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public List getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List permissionList) {
		this.permissionList = permissionList;
	}

	public List<Map> getSecurityGroupPermissionList() {
		return securityGroupPermissionList;
	}

	public void setSecurityGroupPermissionList(List<Map> securityGroupPermissionList) {
		this.securityGroupPermissionList = securityGroupPermissionList;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public Map getSecurityGroupPermissionMap() {
		return securityGroupPermissionMap;
	}

	public void setSecurityGroupPermissionMap(Map securityGroupPermissionMap) {
		this.securityGroupPermissionMap = securityGroupPermissionMap;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String securityGroupPermissionList()
	{
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			groupId = WebUtil.filterSpecialCharacters(groupId);
			this.permissionList = this.securityPermissionServices.securityPermissionListByModule(param);
			this.securityGroupPermissionList = this.securityPermissionServices.securityGroupPermissionList(groupId);
			processSecurityGroupPermissionMap();
//			this.permissionIds = "";
//			if(this.securityGroupPermissionList!=null)
//			{
//				for(Map m:this.securityGroupPermissionList)
//				{
//					if(this.permissionIds.length()>0)
//						this.permissionIds += ",";
//					this.permissionIds += m.get("permissionId");
//				}
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	private void processSecurityGroupPermissionMap()
	{
		if(this.securityGroupPermissionMap==null)
			this.securityGroupPermissionMap = new HashMap();
		securityGroupPermissionMap.clear();
		Map mm = new HashMap();
		//整理权限组的已有权限
		if(!WebUtil.isNullForList(securityGroupPermissionList))
		{
			for(Map sgp:securityGroupPermissionList)
			{
				mm.put(sgp.get("permissionId"), sgp);
			}
		}
		//暂时只处理2层菜单
		securityGroupPermissionMap.put("text", ".");
		List rootList = new ArrayList();
		Map<String,Map> rootMap = new HashMap();
		if(!WebUtil.isNullForList(permissionList))
		{
			for(SecurityPermission sp:permissionList)
			{
				if(WebUtil.isNull(sp.getParentPermissionId())||sp.getParentPermissionId().equals("0"))
				{
					Map r = rootMap.get(sp.getId());
					if(r==null)
						r = new HashMap();
					r.put("id", sp.getId());
					r.put("text", sp.getDescription());
					r.put("expanded", true);
					r.put("permissionId", sp.getId());
					r.put("permissionName", sp.getDescription());
					r.put("parentPermissionId", sp.getParentPermissionId());
					r.put("dispIndex", sp.getDispIndex());
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
				else
				{
					Map r = rootMap.get(sp.getParentPermissionId());
					if(r==null)
						r = new HashMap();
					List l = (List) r.get("children");
					if(l==null)
						l = new ArrayList();
					Map lm = new HashMap();
					lm.put("id", sp.getId());
					lm.put("text", sp.getDescription());
					lm.put("permissionId", sp.getId());
					lm.put("permissionName", sp.getDescription());
					lm.put("parentPermissionId", sp.getParentPermissionId());
					lm.put("dispIndex", sp.getDispIndex());
					lm.put("leaf", true);
					if(WebUtil.isNotNull(mm.get(sp.getId())))
					{
						lm.put("checked", true);
					}
					else
					{
						lm.put("checked", false);
					}
					l.add(lm);
					r.put("children", l);
					rootMap.put(sp.getParentPermissionId(), r);
				}
			}
		}
		Iterator iter = rootMap.keySet().iterator();
		while(iter.hasNext())
		{
			rootList.add(rootMap.get(iter.next()));
		}
		securityGroupPermissionMap.put("children", rootList);		
	}
	
	public String saveSecurityGroup()
	{
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin)session.get("LOGIN_INFO");
			if(ul==null)
				return "success";
			String userNo = ul.getUserNo();		
			if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
			{
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			groupId = WebUtil.filterSpecialCharacters(groupId);
			groupName = WebUtil.filterSpecialCharacters(groupName);
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("GroupId", groupId);
			param.put("GroupName", groupName);
			if(WebUtil.isNotNull(companyId))
				param.put("CompanyId", new Integer(companyId));
			String m = this.securityPermissionServices.saveSecurityGroup(param);
			message = m;
			success = true;
		} catch (NumberFormatException e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}
	
	public String saveSecurityGroupPermission()
	{
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin)session.get("LOGIN_INFO");
			if(ul==null)
				return "success";
			String userNo = ul.getUserNo();		
			if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
			{
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			groupId = WebUtil.filterSpecialCharacters(groupId);
			permissionIds = WebUtil.filterSpecialCharacters(permissionIds);
			String m = this.securityPermissionServices.saveSecurityGroupPermission(groupId, permissionIds);
			message = m;
			success = true;
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}
	
	public String viewSecurityGroupInfo()
	{
		if(WebUtil.isNull(groupId))
			groupId = null;
		if(WebUtil.isNull(groupName))
			groupName = null;
		return "success";
	}

}
