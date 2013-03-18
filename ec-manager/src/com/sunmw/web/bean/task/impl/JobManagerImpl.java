package com.sunmw.web.bean.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.task.JobConfig;
import com.sunmw.web.bean.task.JobManager;
import com.sunmw.web.bean.task.SchedulerServices;
import com.sunmw.web.dao.JobConfigMapper;
import com.sunmw.web.dao.TaskDefineMapper;
import com.sunmw.web.domain.TaskDefine;
import com.sunmw.web.domain.TaskDefineExample;
import com.sunmw.web.util.WebUtil;
/**
 * 从库中读取任务并派发给Quartz
 * 将立即执行的任务派发给Quartz
 * @author jasper
 *
 */
public class JobManagerImpl implements JobManager {

	private SchedulerServices schedulerServices;
	private JobConfigMapper jobConfigMapper;
	private TaskDefineMapper taskDefineMapper;

	public SchedulerServices getSchedulerServices() {
		return schedulerServices;
	}
	public void setSchedulerServices(SchedulerServices schedulerServices) {
		this.schedulerServices = schedulerServices;
	}
	public JobConfigMapper getJobConfigMapper() {
		return jobConfigMapper;
	}
	public void setJobConfigMapper(JobConfigMapper jobConfigMapper) {
		this.jobConfigMapper = jobConfigMapper;
	}
	public TaskDefineMapper getTaskDefineMapper() {
		return taskDefineMapper;
	}
	public void setTaskDefineMapper(TaskDefineMapper taskDefineMapper) {
		this.taskDefineMapper = taskDefineMapper;
	}
	public void readTask()
	{
		Map param = new HashMap();
		param.put("IsLoad", "N");
		List<Map> taskList = this.jobConfigMapper.selectNoLoadTask(param);
		if(WebUtil.isNullForList(taskList))
			return;
		List<Long> taskIdList = new ArrayList();
		for(Map m:taskList)
		{
			JobConfig jc = new JobConfig();
			if(WebUtil.isNull(m.get("cronExpression")))
				continue;
			jc.setTargetName((String)m.get("className"));
			jc.setTargetMethod((String)m.get("runMethod"));
			jc.setTargetType((String)m.get("classType"));
			jc.setTaskId((Long)m.get("taskId"));
			jc.setIfId((Long)m.get("ifId"));
			jc.setStoreId((Integer)m.get("storeId"));
			jc.setStartTime((Date)m.get("startDate"));
			jc.setEndTime((Date)m.get("endDate"));
			jc.setIfType((String)m.get("ifType"));
			jc.setJobName("job_"+m.get("ifCode")+"_"+m.get("taskId"));
			jc.setJobGroup("job_"+m.get("ifCode")+"_Group");
			jc.setTriggerName("trigger_"+m.get("ifCode")+"_"+m.get("taskId"));
			jc.setTriggerGroup("trigger_"+m.get("ifCode")+"_Group");
			jc.setCronStr((String)m.get("cronExpression"));
			Map arguments = new HashMap();
			arguments.put("StoreId", m.get("storeId"));
			arguments.put("LastExecTime", m.get("lastExecTime"));
			List<Map> ifPropList = (List) m.get("ifPropList");
			if(!WebUtil.isNullForList(ifPropList))
			{
				for(Map p:ifPropList)
				{
					arguments.put(p.get("propName"), p.get("propValue"));
				}
			}
			jc.setArguments(new Object[]{arguments});
			try {
				schedulerServices.schedule(jc);
				taskIdList.add(jc.getTaskId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//更新任务的IsLoad状态
		if(taskIdList.size()>0)
		{
			TaskDefineExample ex = new TaskDefineExample();
			TaskDefineExample.Criteria c = ex.createCriteria();
			c.andTaskIdIn(taskIdList);
			TaskDefine td = new TaskDefine();
			td.setIsLoad("Y");
			this.taskDefineMapper.updateByExampleSelective(td, ex);
		}
		
	}
	
	public Map execTask(Long taskId)
	{
		Map param = new HashMap();
		param.put("TaskId", taskId);
		List<Map> taskList = this.jobConfigMapper.selectNoLoadTask(param);
		Map result = new HashMap();
		if(WebUtil.isNullForList(taskList))
		{
			result.put("Flag", "error");
			result.put("Message", "接口或任务的状态无效");
			return result;
		}
		Map m = taskList.get(0);
		JobConfig jc = new JobConfig();
		jc.setTargetName((String)m.get("className"));
		jc.setTargetMethod((String)m.get("runMethod"));
		jc.setTargetType((String)m.get("classType"));
		jc.setTaskId((Long)m.get("taskId"));
		jc.setStartTime((Date)m.get("startDate"));
		jc.setEndTime((Date)m.get("endDate"));
		jc.setIfType((String)m.get("ifType"));
		jc.setJobName("job_"+m.get("ifCode")+"_"+m.get("taskId"));
		jc.setJobGroup("job_"+m.get("ifCode")+"_Group");
		jc.setTriggerName("trigger_"+m.get("ifCode")+"_"+m.get("taskId"));
		jc.setTriggerGroup("trigger_"+m.get("ifCode")+"_Group");
		jc.setCronStr((String)m.get("cronExpression"));
		Map arguments = new HashMap();
		arguments.put("StoreId", m.get("storeId"));
		arguments.put("LastExecTime", m.get("lastExecTime"));
		List<Map> ifPropList = (List) m.get("ifPropList");
		if(!WebUtil.isNullForList(ifPropList))
		{
			for(Map p:ifPropList)
			{
				arguments.put(p.get("propName"), p.get("propValue"));
			}
		}
		jc.setArguments(new Object[]{arguments});
		try {
			schedulerServices.scheduleNow(jc,1000,0);
			result.put("Flag", "success");
			result.put("Message", "执行成功");
		} catch (Exception e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		}
		return result;
	}
}
