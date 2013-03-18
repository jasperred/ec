package com.sunmw.web.bean.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sunmw.web.dao.TaskDefineMapper;
import com.sunmw.web.dao.TaskQueueMapper;
import com.sunmw.web.domain.TaskDefine;
import com.sunmw.web.domain.TaskQueue;
import com.sunmw.web.domain.TaskQueueExample;
import com.sunmw.web.util.WebUtil;

public class TaskDefineThread extends Thread {
	private TaskQueueMapper taskQueueMapper;
	private TaskDefineMapper taskDefineMapper;
	private TaskDefine taskDefine;
	
	public TaskQueueMapper getTaskQueueMapper() {
		return taskQueueMapper;
	}

	public void setTaskQueueMapper(TaskQueueMapper taskQueueMapper) {
		this.taskQueueMapper = taskQueueMapper;
	}

	public TaskDefineMapper getTaskDefineMapper() {
		return taskDefineMapper;
	}

	public void setTaskDefineMapper(TaskDefineMapper taskDefineMapper) {
		this.taskDefineMapper = taskDefineMapper;
	}

	public TaskDefine getTaskDefine() {
		return taskDefine;
	}

	public void setTaskDefine(TaskDefine taskDefine) {
		this.taskDefine = taskDefine;
	}

	public void run()
	{
		Date currentDate = new Date();
		String time = WebUtil.formatDateString(currentDate, "HH:mm:ss");
		String dayEndTime = "23:59:59";
		if(taskDefine.getDayEndTime()!=null)
			dayEndTime = WebUtil.formatDateString(taskDefine.getDayEndTime(), "HH:mm:ss");
		String dayStartTime = "00:00:00";
		if(taskDefine.getDayStartTime()!=null)
			dayStartTime = WebUtil.formatDateString(taskDefine.getDayStartTime(), "HH:mm:ss");
		//超过当天结束时间不执行
		if(time.compareToIgnoreCase(dayEndTime)>0||time.compareToIgnoreCase(dayStartTime)<0)
			return;
		//检查是否有相同时间执行的队列，有则不执行
		TaskQueueExample example = new TaskQueueExample();
		TaskQueueExample.Criteria c = example.createCriteria();
		c.andTaskIdEqualTo(taskDefine.getTaskId());
		c.andStartTimeGreaterThanOrEqualTo(taskDefine.getNextExecTime());
		List statusList = new ArrayList();
		statusList.add(1);
		statusList.add(2);
		c.andStatusIn(statusList);
		int count = taskQueueMapper.countByExample(example);
		if(count>0)
			return;
		//生成TaskQueue
		TaskQueue tq = new TaskQueue();
		tq.setCtime(currentDate);
		tq.setExecNum(0);
		tq.setStatus(1);
		tq.setTaskId(taskDefine.getTaskId());
		tq.setStartTime(taskDefine.getNextExecTime());
		if(taskDefine.getDayCycleType().equals("H"))
		{
			long end = Integer.parseInt(WebUtil.formatDateString(taskDefine.getDayCycle(), "H"))*60*60*1000L;
			tq.setEndTime(new Date(taskDefine.getNextExecTime().getTime()+end));
		} 
		//每分钟
		else if(taskDefine.getDayCycleType().equals("M"))
		{
			long end = Integer.parseInt(WebUtil.formatDateString(taskDefine.getDayCycle(), "m"))*60*1000L;
			tq.setEndTime(new Date(taskDefine.getNextExecTime().getTime()+end));
		} 
		//每秒钟
		else if(taskDefine.getDayCycleType().equals("S"))
		{
			long end = Integer.parseInt(WebUtil.formatDateString(taskDefine.getDayCycle(), "d"))*1000L;
			tq.setEndTime(new Date(taskDefine.getNextExecTime().getTime()+end));
		}
		//特定时间,因为只执行一次，所以无结束时间
		else if(taskDefine.getDayCycleType().equals("O"))
		{
			tq.setEndTime(null);
		}
		taskQueueMapper.insert(tq);
		//非执行一次情况，计算下次执行时间
		if(!taskDefine.getDayCycleType().equals("O"))
		{
			long n = 0;
			if(taskDefine.getDayCycleType().equals("H"))
				n = Integer.parseInt(WebUtil.formatDateString(taskDefine.getDayCycle(), "H"))*60*60*1000L;
			if(taskDefine.getDayCycleType().equals("M"))
				n = Integer.parseInt(WebUtil.formatDateString(taskDefine.getDayCycle(), "m"))*60*1000L;
			if(taskDefine.getDayCycleType().equals("S"))
				n = Integer.parseInt(WebUtil.formatDateString(taskDefine.getDayCycle(), "s"))*1000L;
			Date nextDate = new Date(taskDefine.getNextExecTime().getTime()+n);
			//如果下次执行时间小于当天的最后一次执行时间，保存下次执行时间
			if(WebUtil.formatDateString(nextDate, "yyyy-MM-dd HH:mm:ss").compareTo(WebUtil.formatDateString(currentDate, "yyyy-MM-dd")+" "+dayEndTime)<=0)
			{
				TaskDefine newTd = new TaskDefine();
				newTd.setTaskId(taskDefine.getTaskId());
				newTd.setNextExecTime(nextDate);
				newTd.setMtime(currentDate);
				taskDefineMapper.updateByPrimaryKeySelective(newTd);
				taskDefine.setNextExecTime(nextDate);
			}
		}
	}

}
