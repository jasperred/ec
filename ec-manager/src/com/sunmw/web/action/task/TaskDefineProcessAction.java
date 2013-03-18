package com.sunmw.web.action.task;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.task.JobManager;
import com.sunmw.web.bean.task.TaskServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class TaskDefineProcessAction {

	private TaskServices taskServices;

	private String taskId;
	private String ifId;
	private String startDate;
	private String endDate;
	private Integer status;
	private Integer storeId;
	private String cronExpression;

	private boolean success;
	private String message;
	private String crumb;

	public TaskServices getTaskServices() {
		return taskServices;
	}

	public void setTaskServices(TaskServices taskServices) {
		this.taskServices = taskServices;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getIfId() {
		return ifId;
	}

	public void setIfId(String ifId) {
		this.ifId = ifId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
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

	public String saveTaskDefine() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			String userNo = ul.getUserNo();
			if (crumb == null || !VerifyRequest.verifyCrumb(userNo, crumb)) {
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			Map param = new HashMap();
			if (WebUtil.isNotNull(taskId))
				param.put("TaskDefineId", new Long(taskId));
			if (WebUtil.isNotNull(ifId))
				param.put("IfId", new Long(ifId));
			if (WebUtil.isNotNull(startDate))
				param.put("StartDate", WebUtil.toDateForString(startDate, "yyyy-MM-dd"));
			if (WebUtil.isNotNull(endDate))
				param.put("EndDate", WebUtil.toDateForString(endDate, "yyyy-MM-dd"));
			if (WebUtil.isNotNull(status))
				param.put("Status", new Integer(status));
			if (WebUtil.isNotNull(storeId))
				param.put("StoreId", new Integer(storeId));
			if (WebUtil.isNotNull(cronExpression))
				param.put("CronExpression", cronExpression);
			
			Map r = this.taskServices.saveTaskDefine(param);
			message = (String) r.get("Flag");
			if (message == null || !message.equals("success"))
				success = false;
			else
				success = true;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	public String deleteTaskDefine() {
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		String userNo = ul.getUserNo();
		if (crumb == null || !VerifyRequest.verifyCrumb(userNo, crumb)) {
			message = MessageInfo.Verify;
			success = false;
			return "success";
		}
		if (WebUtil.isNull(taskId)) {
			message = "id is null";
			success = false;
			return "success";
		}
		this.message = this.taskServices.deleteTaskDefine(new Long(taskId));
		if (message == null || !message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
	
	public String nowExecTask()
	{
		if(WebUtil.isNull(taskId))
		{
			success = false;
			message = "任务ID未传入";
			return "success";
		}
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		String userNo = ul.getUserNo();
		if (crumb == null || !VerifyRequest.verifyCrumb(userNo, crumb)) {
			message = MessageInfo.Verify;
			success = false;
			return "success";
		}
		JobManager jm = (JobManager)GetBeanServlet.getBean("jobManager");
		Map r = jm.execTask(new Long(taskId));
		String flag = (String) r.get("Flag");
		if(flag.equals("success"))
		{
			success = true;
		}
		else
		{
			success = false;
		}
		message = (String) r.get("Message");
		return "success";
	}
}
