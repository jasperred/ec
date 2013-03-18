package com.sunmw.web.action.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.task.TaskServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.IfDefine;
import com.sunmw.web.entity.ModuleConfig;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class IfDefineProcessAction {

	private TaskServices taskServices;
	private String ifDefineId;
    private String ifCode;
    private String ifName;
    private String ifCat;
    private String status;
    private String errorRetryTimes;
    private String retryWaitSeconds;
    private String runMethod;
    private String className;
    private String ifType;
    private String classType;
    private List ifDefineList;
    private Map ifDefineTreeMap;
	private boolean success;
	private String message;
	private String crumb;

	public TaskServices getTaskServices() {
		return taskServices;
	}

	public void setTaskServices(TaskServices taskServices) {
		this.taskServices = taskServices;
	}

	public String getIfDefineId() {
		return ifDefineId;
	}

	public void setIfDefineId(String ifDefineId) {
		this.ifDefineId = ifDefineId;
	}

	public String getIfCode() {
		return ifCode;
	}

	public void setIfCode(String ifCode) {
		this.ifCode = ifCode;
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getIfCat() {
		return ifCat;
	}

	public void setIfCat(String ifCat) {
		this.ifCat = ifCat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorRetryTimes() {
		return errorRetryTimes;
	}

	public void setErrorRetryTimes(String errorRetryTimes) {
		this.errorRetryTimes = errorRetryTimes;
	}

	public String getRetryWaitSeconds() {
		return retryWaitSeconds;
	}

	public void setRetryWaitSeconds(String retryWaitSeconds) {
		this.retryWaitSeconds = retryWaitSeconds;
	}

	public String getRunMethod() {
		return runMethod;
	}

	public void setRunMethod(String runMethod) {
		this.runMethod = runMethod;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIfType() {
		return ifType;
	}

	public void setIfType(String ifType) {
		this.ifType = ifType;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public List getIfDefineList() {
		return ifDefineList;
	}

	public void setIfDefineList(List ifDefineList) {
		this.ifDefineList = ifDefineList;
	}

	public Map getIfDefineTreeMap() {
		return ifDefineTreeMap;
	}

	public void setIfDefineTreeMap(Map ifDefineTreeMap) {
		this.ifDefineTreeMap = ifDefineTreeMap;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String saveIfDefine()
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
			if(WebUtil.isNotNull(ifDefineId))
			param.put("IfDefineId", new Long(ifDefineId));
			param.put("IfCode", ifCode);
			param.put("IfName", ifName);
			param.put("IfCat", ifCat);
			if(WebUtil.isNotNull(status))
			param.put("Status", new Integer(status));
			if(WebUtil.isNotNull(errorRetryTimes))
			param.put("ErrorRetryTimes", new Integer(errorRetryTimes));
			if(WebUtil.isNotNull(retryWaitSeconds))
			param.put("RetryWaitSeconds", new Integer(retryWaitSeconds));
			param.put("RunMethod", runMethod);
			param.put("ClassName", className);
			param.put("IfType", ifType);
			param.put("ClassType", classType);
			Map r = this.taskServices.saveIfDefine(param);
			message = (String)r.get("Flag");
			if(message==null||!message.equals("success"))
				success = false;
			else
				success = true;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	public String deleteIfDefine()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		String userNo = ul.getUserNo();		
		if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
		{
			message = MessageInfo.Verify;
			success = false;
			return "success";
		}
		if(WebUtil.isNull(ifDefineId))
		{
			message = "id is null";
			success = false;
			return "success";
		}
		this.message = this.taskServices.deleteIfDefine(new Integer(ifDefineId));
		if(message==null||!message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
	
	public String ifDefineList() {
		if (this.ifDefineList == null)
			this.ifDefineList = new ArrayList();
		this.ifDefineList.clear();
		List<IfDefine> l = taskServices.getIfDefine();
		if (!WebUtil.isNullForList(l)) {
			for (IfDefine f : l) {
				Map m = new HashMap();
				m.put("name", f.getIfName());
				m.put("value", f.getId());
				this.ifDefineList.add(m);
			}
		}
		return "success";
	}
	
	public String ifDefineTreeList()
	{
		List treeList = taskServices.getIfDefineByTree();
		try {
			this.ifDefineTreeMap = jsonModuleList(treeList);
		} catch (Exception e) {
			this.ifDefineTreeMap = null;
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
				IfDefine mc = (IfDefine)root.get("info");
				List<IfDefine> subList = (List) root.get("subList");
				rootMap.put("id",mc.getId());
				rootMap.put("IfDefineId",mc.getId());
				rootMap.put("ClassName",mc.getClassName());
				rootMap.put("ClassType",mc.getClassType());
				rootMap.put("ErrorRetryTimes",mc.getErrorRetryTimes());
				rootMap.put("IfCat",mc.getIfCat());
				rootMap.put("IfCode",mc.getIfCode());
				rootMap.put("IfName",mc.getIfName());
				rootMap.put("IfType",mc.getIfType());
				rootMap.put("RetryWaitSeconds",mc.getRetryWaitSeconds());
				rootMap.put("RunMethod",mc.getRunMethod());
				rootMap.put("Status",mc.getStatus());
				//第一个菜单展开
				if(i==1)
					rootMap.put("expanded",true);
				List leafList = new ArrayList();
				if(!WebUtil.isNullForList(subList))
				{
					for(IfDefine leaf:subList)
					{
						Map leafMap = new HashMap();
						leafMap.put("id",leaf.getId());
						leafMap.put("IfDefineId",leaf.getId());
						leafMap.put("ClassName",leaf.getClassName());
						leafMap.put("ClassType",leaf.getClassType());
						leafMap.put("ErrorRetryTimes",leaf.getErrorRetryTimes());
						leafMap.put("IfCat",leaf.getIfCat());
						leafMap.put("IfCode",leaf.getIfCode());
						leafMap.put("IfName",leaf.getIfName());
						leafMap.put("IfType",leaf.getIfType());
						leafMap.put("RetryWaitSeconds",leaf.getRetryWaitSeconds());
						leafMap.put("RunMethod",leaf.getRunMethod());
						leafMap.put("Status",leaf.getStatus());
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
