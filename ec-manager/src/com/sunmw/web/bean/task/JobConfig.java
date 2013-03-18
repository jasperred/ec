package com.sunmw.web.bean.task;

import java.io.Serializable;
import java.util.Date;

/**
 * Quartz执行任务的参数
 * 
 * @author jasper
 *
 */
public class JobConfig implements Serializable{
	private Long taskId;
	private Long ifId;
	private Integer storeId;
	private String targetName;
	private String targetMethod;
	private String targetType;	
	private String ifType;	
	private Object[] arguments;
	private Date startTime;
	private Date endTime;
	private String triggerName;//"trigger_"+IfCode+"_"+TaskId
	private String triggerGroup;//"trigger_"+IfCode+"_"+"Group"
	private String cronStr;
	private String jobName;//"job_"+IfCode+"_"+TaskId
	private String jobGroup;//"job_"+IfCode+"_"+"Group"
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getIfId() {
		return ifId;
	}
	public void setIfId(Long ifId) {
		this.ifId = ifId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTargetMethod() {
		return targetMethod;
	}
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getIfType() {
		return ifType;
	}
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	public Object[] getArguments() {
		return arguments;
	}
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getCronStr() {
		return cronStr;
	}
	public void setCronStr(String cronStr) {
		this.cronStr = cronStr;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

}
