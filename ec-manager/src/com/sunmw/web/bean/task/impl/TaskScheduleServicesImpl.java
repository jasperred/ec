package com.sunmw.web.bean.task.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.quartz.SchedulerException;

import com.sunmw.web.bean.task.TaskDefineThread;
import com.sunmw.web.bean.task.TaskQueueThread;
import com.sunmw.web.bean.task.TaskScheduleServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.dao.IfDefineMapper;
import com.sunmw.web.dao.IfDefinePropMapper;
import com.sunmw.web.dao.TaskDefineMapper;
import com.sunmw.web.dao.TaskQueueMapper;
import com.sunmw.web.domain.TaskDefine;
import com.sunmw.web.domain.TaskDefineExample;
import com.sunmw.web.domain.TaskQueue;
import com.sunmw.web.domain.TaskQueueExample;
import com.sunmw.web.util.WebUtil;


public class TaskScheduleServicesImpl implements TaskScheduleServices {

	private IfDefineMapper ifDefineMapper;
	private IfDefinePropMapper ifDefinePropMapper;
	private TaskDefineMapper taskDefineMapper;
	private TaskQueueMapper taskQueueMapper;
	private ScheduledThreadPoolExecutor taskDefineScheduledThreadPoolExecutor;
	private ScheduledThreadPoolExecutor taskQueueScheduledThreadPoolExecutor;
	
	private Map<Long,TaskDefine> taskDefineMap;//保存已读取的任务定义内容
	private Map<Long,TaskDefine> taskQueueMap;//保存已读取的任务队列内容
	private Map<Long,TaskQueue> taskQueueScheduleMap;//保存队列执行的线程
	private Map<Long,ScheduledFuture> taskDefineScheduleMap;//保存任务定义计划，以便取消任务计划
	private long lastTaskQueueId;
	
	public IfDefineMapper getIfDefineMapper() {
		return ifDefineMapper;
	}

	public void setIfDefineMapper(IfDefineMapper ifDefineMapper) {
		this.ifDefineMapper = ifDefineMapper;
	}

	public IfDefinePropMapper getIfDefinePropMapper() {
		return ifDefinePropMapper;
	}

	public void setIfDefinePropMapper(IfDefinePropMapper ifDefinePropMapper) {
		this.ifDefinePropMapper = ifDefinePropMapper;
	}

	public TaskDefineMapper getTaskDefineMapper() {
		return taskDefineMapper;
	}

	public void setTaskDefineMapper(TaskDefineMapper taskDefineMapper) {
		this.taskDefineMapper = taskDefineMapper;
	}

	public TaskQueueMapper getTaskQueueMapper() {
		return taskQueueMapper;
	}

	public void setTaskQueueMapper(TaskQueueMapper taskQueueMapper) {
		this.taskQueueMapper = taskQueueMapper;
	}
	

	public ScheduledThreadPoolExecutor getTaskDefineScheduledThreadPoolExecutor() {
		return taskDefineScheduledThreadPoolExecutor;
	}

	public void setTaskDefineScheduledThreadPoolExecutor(
			ScheduledThreadPoolExecutor taskDefineScheduledThreadPoolExecutor) {
		this.taskDefineScheduledThreadPoolExecutor = taskDefineScheduledThreadPoolExecutor;
	}

	public Map<Long, TaskDefine> getTaskDefineMap() {
		return taskDefineMap;
	}

	public void setTaskDefineMap(Map<Long, TaskDefine> taskDefineMap) {
		this.taskDefineMap = taskDefineMap;
	}

	public Map<Long, TaskDefine> getTaskQueueMap() {
		return taskQueueMap;
	}

	public void setTaskQueueMap(Map<Long, TaskDefine> taskQueueMap) {
		this.taskQueueMap = taskQueueMap;
	}

	public ScheduledThreadPoolExecutor getTaskQueueScheduledThreadPoolExecutor() {
		return taskQueueScheduledThreadPoolExecutor;
	}

	public void setTaskQueueScheduledThreadPoolExecutor(
			ScheduledThreadPoolExecutor taskQueueScheduledThreadPoolExecutor) {
		this.taskQueueScheduledThreadPoolExecutor = taskQueueScheduledThreadPoolExecutor;
	}

	public Map<Long, TaskQueue> getTaskQueueScheduleMap() {
		return taskQueueScheduleMap;
	}

	public void setTaskQueueScheduleMap(Map<Long, TaskQueue> taskQueueScheduleMap) {
		this.taskQueueScheduleMap = taskQueueScheduleMap;
	}

	public Map<Long, ScheduledFuture> getTaskDefineScheduleMap() {
		return taskDefineScheduleMap;
	}

	public void setTaskDefineScheduleMap(
			Map<Long, ScheduledFuture> taskDefineScheduleMap) {
		this.taskDefineScheduleMap = taskDefineScheduleMap;
	}

	//计算下次执行时间
	//天周期类型H：每小时，M：每分钟，S:每秒钟，O：特定时间（执行一次）
	private long getNextTime(TaskDefine td, Date d) {
		Date dayStartTime = td.getDayStartTime();
		if(dayStartTime==null)
			dayStartTime = WebUtil.toDateForString("00:00:00", "HH:mm:ss");
		else
			dayStartTime = WebUtil.toDateForString(WebUtil.formatDateString(dayStartTime, "HH:mm:ss"),  "HH:mm:ss");
		 long nextTime = 0;
		//每小时
		if(td.getDayCycleType().equals("H"))
		{
			int nextH = Integer.parseInt(WebUtil.formatDateString(d, "H"));
			int startH = Integer.parseInt(WebUtil.formatDateString(dayStartTime, "H"));
			int dayCycleH = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "H"));
			int h = ((nextH-startH)/dayCycleH+1)*dayCycleH;
			nextTime = dayStartTime.getTime()+h*60*60*1000;
		} 
		//每分钟
		else if(td.getDayCycleType().equals("M"))
		{
			long bm =( WebUtil.toDateForString(WebUtil.formatDateString(d, "HH:mm:ss"), "HH:mm:ss").getTime()-dayStartTime.getTime())/(1000*60);
			int dayCycleM = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "M"));
			long m = (bm/dayCycleM+1)*dayCycleM;
			nextTime = dayStartTime.getTime()+m*60*1000;
		} 
		//每秒钟
		else if(td.getDayCycleType().equals("S"))
		{
			long bs =( WebUtil.toDateForString(WebUtil.formatDateString(d, "HH:mm:ss"), "HH:mm:ss").getTime()-dayStartTime.getTime())/(1000);
			int dayCycleS = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "s"));
			long s = (bs/dayCycleS+1)*dayCycleS;
			nextTime = dayStartTime.getTime()+s*1000;
		}
		//特定时间
		else if(td.getDayCycleType().equals("O"))
		{
			nextTime = WebUtil.toDateForString(WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss"),  "HH:mm:ss").getTime();
		}
		return nextTime;
	}

	//计算下次执行日期
	//周期类型D：每天，W：每周，M：每月
	private String getNextDate(TaskDefine td, Date d) {
		String nextDate = null;
		long time = getNextTime(td,d);
		Date dayEndTime = td.getDayEndTime();
		if(dayEndTime==null)
			dayEndTime = WebUtil.toDateForString("23:59:59", "HH:mm:ss");
		else
			dayEndTime = WebUtil.toDateForString(WebUtil.formatDateString(dayEndTime, "HH:mm:ss"),  "HH:mm:ss");
		Date dayStartTime = td.getDayStartTime();
		if(dayStartTime==null)
			dayStartTime = WebUtil.toDateForString("00:00:00", "HH:mm:ss");
		else
			dayStartTime = WebUtil.toDateForString(WebUtil.formatDateString(dayStartTime, "HH:mm:ss"),  "HH:mm:ss");
		boolean isNext = false;//是否进入下一周期
		//如果计算的每天执行时间大于每天结束时间则进入下一周期
		if(time>dayEndTime.getTime())
			isNext = true;
		//每天
		if(td.getCycleType().equals("D"))
		{
			//计算下次执行时间距离开始日期相差的天数
			int diff = WebUtil.getDaysBetween(WebUtil.formatDateString(td.getStartDate(), "yyyy-MM-dd"), WebUtil.formatDateString(d, "yyyy-MM-dd"));
			int bd = diff;
			if(diff%td.getCycle()!=0||isNext)
			{
			 bd = (diff/td.getCycle()+1)*td.getCycle();
			 if(td.getDayCycleType().equals("O"))
					time = WebUtil.toDateForString(WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss"), "HH:mm:ss").getTime();
					else
						time = dayStartTime.getTime();
			}
			nextDate = WebUtil.formatDateString(new Date(td.getStartDate().getTime()+bd*86400000L), "yyyy-MM-dd");
		} 
		//每周
		else if(td.getCycleType().equals("W"))
		{
			int bd = 0;
			int w = WebUtil.dayForWeek(d);
			if(w!=td.getCycle()||isNext)
			{
				bd = 7+td.getCycle()-WebUtil.dayForWeek(d);
				if(td.getDayCycleType().equals("O"))
					time = WebUtil.toDateForString(WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss"), "HH:mm:ss").getTime();
					else
						time = dayStartTime.getTime();
			}
			nextDate = WebUtil.formatDateString(new Date(d.getTime()+bd*86400000L), "yyyy-MM-dd");
		} 
		//每月
		else if(td.getCycleType().equals("M"))
		{
			int m = WebUtil.getMonthsBetween(td.getStartDate(), d);
			if(m%td.getCycle()!=0||isNext)
			{
			nextDate = WebUtil.ofterMonthDate(td.getStartDate(), m/td.getCycle()+1);
			if(td.getDayCycleType().equals("O"))
				time = WebUtil.toDateForString(WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss"), "HH:mm:ss").getTime();
				else
					time = dayStartTime.getTime();
			}
			else
				nextDate = WebUtil.formatDateString(d, "yyyy-MM-dd");
		}
		return nextDate+" "+WebUtil.formatDateString(new Date(time), "HH:mm:ss");
	}
	
	//执行队列
	public void runExecuteQueue()
	{
		while(true)
		{
			System.out.println(WebUtil.formatDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
	}

	@Override
	public void readTaskDefineGenericTaskQueue() {
		
		//读取任务定义，判断是否有效，加入到taskDefineMap中
		//每次执行时查询数据库，状态为可执行(status=1)，时间在开始和结束范围内
		//status=-1不可执行
		TaskDefineExample example = new TaskDefineExample();
		List<TaskDefine> tdl = taskDefineMapper.selectByExample(example);
		if(WebUtil.isNullForList(tdl))
			return;
		//初始化任务定义池
		if(taskDefineMap==null)
			taskDefineMap = new HashMap();
		if(taskDefineScheduleMap==null)
			taskDefineScheduleMap = new HashMap();
		Date currentDate = new Date();//当前时间
		long c = currentDate.getTime();//用来计算delay时间
			for(TaskDefine td:tdl)
			{
				//判断是否是有效任务
				if(td.getStatus()!=1||(td.getEndDate()!=null&&td.getEndDate().before(currentDate)))
				{
					ScheduledFuture sf = taskDefineScheduleMap.get(td.getTaskId());
					if(sf!=null)
						sf.cancel(true);
					taskDefineScheduleMap.remove(td.getTaskId());
					taskDefineMap.remove(td.getTaskId());
					continue;
				}
				TaskDefine t = taskDefineMap.get(td.getTaskId());
				//如果是第一次执行并且下次执行时间无效或者任务参数改变，重新计算
				if((t==null&&(td.getNextExecTime()==null||td.getNextExecTime().before(currentDate)))||(t!=null&&(!checkChange(t,td)||td.getNextExecTime()==null)))
				{
					String nextDate = getNextDate(td,currentDate);
					Date nd = WebUtil.toDateForString(nextDate, "yyyy-MM-dd HH:mm:ss");
					td.setNextExecTime(nd);
					TaskDefine newTd = new TaskDefine();
					newTd.setTaskId(td.getTaskId());
					newTd.setNextExecTime(nd);
					newTd.setMtime(currentDate);
					taskDefineMapper.updateByPrimaryKeySelective(newTd);
				}
				//当天需要执行的任务才做计划
				if(WebUtil.formatDateString(td.getNextExecTime(), "yyyy-MM-dd").equals(WebUtil.formatDateString(currentDate, "yyyy-MM-dd")))
				{					
					if(t!=null)
					{
						//判断参数是否有变化，没变化不需要重新加载
						if(checkChange(t,td))
							continue;
						ScheduledFuture sf = taskDefineScheduleMap.get(td.getTaskId());
						if(sf!=null)
							sf.cancel(true);
						//重新计算下次执行时间
						String nextDate = getNextDate(td,currentDate);
						Date nd = WebUtil.toDateForString(nextDate, "yyyy-MM-dd HH:mm:ss");
						td.setNextExecTime(nd);
						TaskDefine newTd = new TaskDefine();
						newTd.setTaskId(td.getTaskId());
						newTd.setNextExecTime(nd);
						newTd.setMtime(currentDate);
						taskDefineMapper.updateByPrimaryKeySelective(newTd);
					}
					ScheduledFuture sf = null;
					//执行生成队列的线程
					TaskDefineThread thread = new TaskDefineThread();
					thread.setTaskDefineMapper(taskDefineMapper);
					thread.setTaskQueueMapper(taskQueueMapper);
					thread.setTaskDefine(td);
					if(td.getDayCycleType().equals("O"))
					{
						sf = taskDefineScheduledThreadPoolExecutor.schedule(thread, td.getNextExecTime().getTime()-c, TimeUnit.MILLISECONDS);
					}
					else
					{
						long n = 0;
						if(td.getDayCycleType().equals("H"))
							n = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "H"))*60*60*1000L;
						if(td.getDayCycleType().equals("M"))
							n = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "m"))*60*1000L;
						if(td.getDayCycleType().equals("S"))
							n = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "s"))*1000L;
						//提前5秒执行，小于5秒立即执行
						long delay =  td.getNextExecTime().getTime()-c;
						if(delay<5000)
						{
							String nextDate = getNextDate(td,currentDate);
							Date nd = WebUtil.toDateForString(nextDate, "yyyy-MM-dd HH:mm:ss");
							td.setNextExecTime(nd);
							TaskDefine newTd = new TaskDefine();
							newTd.setTaskId(td.getTaskId());
							newTd.setNextExecTime(nd);
							newTd.setMtime(currentDate);
							taskDefineMapper.updateByPrimaryKeySelective(newTd);
						}
						sf = taskDefineScheduledThreadPoolExecutor.scheduleAtFixedRate(thread, delay-5000,n, TimeUnit.MILLISECONDS);
					}
						taskDefineMap.put(td.getTaskId(), td);
						taskDefineScheduleMap.put(td.getTaskId(), sf);
					
				}
				//不是当天任务，清除
				else
				{
					ScheduledFuture sf = taskDefineScheduleMap.get(td.getTaskId());
					if(sf!=null)
						sf.cancel(true);
					taskDefineMap.remove(td.getTaskId());
					taskDefineScheduleMap.remove(td.getTaskId());
				}
			}
	}
	
	//检查任务参数是否有变化
	private boolean checkChange(TaskDefine t,TaskDefine td)
	{
		//开始时间
		if(!t.getStartDate().equals(td.getStartDate()))
			return false;
		//结束时间
		if(!t.getEndDate().equals(td.getEndDate()))
			return false;
		//周期
		if(!t.getCycle().equals(td.getCycle()))
			return false;
		if(!t.getCycleType().equals(td.getCycleType()))
			return false;
		if(!t.getDayCycle().equals(td.getDayCycle()))
			return false;
		if(!t.getDayCycleType().equals("O"))
		{
			if(!t.getDayStartTime().equals(td.getDayStartTime()))
				return false;
			if(t.getDayEndTime()==null&&td.getDayEndTime()==null)
				return true;
			if((t.getDayEndTime()==null&&td.getDayEndTime()!=null)||(t.getDayEndTime()!=null&&td.getDayEndTime()==null)||!t.getDayEndTime().equals(td.getDayEndTime()))
				return false;
		}		
		return true;
	}

	@Override
	public void readTaskQueueAndExecute() {
		//多线程方式执行
		//读取任务队列，状态是未执行，并且执行周期内
		TaskQueueExample example = new TaskQueueExample();
		com.sunmw.web.domain.TaskQueueExample.Criteria c = example.createCriteria();
		//任务状态，1：未执行，2：执行中，3:执行完成
		//c.andStatusEqualTo(1);
		if(lastTaskQueueId==0)
		{
			List l = new ArrayList();
			l.add(1);
			l.add(2);
			c.andStatusIn(l);
		}
		else
			c.andTaskQueueIdGreaterThan(lastTaskQueueId);
		example.setOrderByClause("task_queue_id");
		List<TaskQueue> tdl = taskQueueMapper.selectByExample(example);
		if(WebUtil.isNullForList(tdl))
			return;
		//记录最后读取的队列ID
		lastTaskQueueId = tdl.get(tdl.size()-1).getTaskQueueId();
		//记录过期的队列ID
		List<Long> failureTaskQueueIds = new ArrayList();
		//记录执行中的队列ID
		List<Long> taskQueueIds = new ArrayList();
		//循环队列判断是否可以执行，可以执行先做执行标记，更新队列记录，放到线程中执行
		for(TaskQueue tq:tdl)
		{
			Date currentDate = new Date();
			//判断队列是否有效
			if(tq.getEndTime()!=null&&tq.getEndTime().before(currentDate))
			{
				failureTaskQueueIds.add(tq.getTaskQueueId());
				continue;
			}
			Date start = tq.getStartTime();
			if(start==null||start.before(currentDate))
				start = currentDate;
			TaskQueueThread taskQueueThread = new TaskQueueThread();
			taskQueueThread.setIfDefineMapper(ifDefineMapper);
			taskQueueThread.setIfDefinePropMapper(ifDefinePropMapper);
			taskQueueThread.setTaskDefineMapper(taskDefineMapper);
			taskQueueThread.setTaskQueueMapper(taskQueueMapper);
			taskQueueThread.setTaskQueue(tq);
			taskQueueScheduledThreadPoolExecutor.schedule(taskQueueThread, currentDate.getTime()-start.getTime(), TimeUnit.MILLISECONDS);
			taskQueueIds.add(tq.getTaskQueueId());
		}
		//更新队列状态
		if(failureTaskQueueIds.size()>0)
		{
			TaskQueue failureTq = new TaskQueue();
			failureTq.setStatus(3);
			failureTq.setMtime(new Date());
			TaskQueueExample failureTqe = new TaskQueueExample();
			com.sunmw.web.domain.TaskQueueExample.Criteria fc = failureTqe.createCriteria();
			fc.andTaskQueueIdIn(failureTaskQueueIds);
			taskQueueMapper.updateByExampleSelective(failureTq, failureTqe);
		}
		if(taskQueueIds.size()>0)
		{
		TaskQueue newTq = new TaskQueue();
		newTq.setStatus(2);
		newTq.setMtime(new Date());
		TaskQueueExample newTqe = new TaskQueueExample();
		com.sunmw.web.domain.TaskQueueExample.Criteria fc = newTqe.createCriteria();
		fc.andTaskQueueIdIn(taskQueueIds);
		fc.andStatusEqualTo(1);
		taskQueueMapper.updateByExampleSelective(newTq, newTqe);
		}
		
		//线程执行时根据执行结果回写执行情况，如果执行失败判断是否要重试，重试次数及延迟执行时间
		

	}

}
