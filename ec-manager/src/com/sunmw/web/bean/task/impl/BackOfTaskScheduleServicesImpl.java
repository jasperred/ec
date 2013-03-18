package com.sunmw.web.bean.task.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sunmw.web.bean.task.TaskScheduleServices;
import com.sunmw.web.dao.IfDefineMapper;
import com.sunmw.web.dao.IfDefinePropMapper;
import com.sunmw.web.dao.TaskDefineMapper;
import com.sunmw.web.dao.TaskQueueMapper;
import com.sunmw.web.domain.TaskDefine;
import com.sunmw.web.domain.TaskDefineExample;
import com.sunmw.web.domain.TaskQueue;
import com.sunmw.web.util.WebUtil;

public class BackOfTaskScheduleServicesImpl implements TaskScheduleServices {

	private IfDefineMapper ifDefineMapper;
	private IfDefinePropMapper ifDefinePropMapper;
	private TaskDefineMapper taskDefineMapper;
	private TaskQueueMapper taskQueueMapper;
	
	private Map<Long,TaskDefine> taskDefineMap;//保存已读取的任务定义内容
	private Map<Long,TaskDefine> taskQueueMap;//保存已读取的任务队列内容
	private Map<Long,TaskDefine> taskQueueThreadMap;//保存队列执行的线程
	private ThreadPoolTaskExecutor taskQueueExecutor;//队列执行的线程池
	
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
	
	//任务生成队列执行
	public void runGenericQueue()
	{
		new Thread(){
			public void run(){
				//死循环执行
				while(true)
				{
					if(WebUtil.isNull(taskDefineMap))
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
					Date currentDate = new Date();
					String currentDateStr = WebUtil.formatDateString(currentDate, "yyyy-MM-dd");
					Set<Entry<Long, TaskDefine>> taskDefineSet = taskDefineMap.entrySet();
					//执行原则：过时不候
					for(Entry e:taskDefineSet)
					{
						TaskDefine td = (TaskDefine) e.getValue();
						//判断是否在有效期内
						//开始日期在当前日期之后或结束日期在当前日期之前无效，以天为单位
						if(td.getStatus()!=1||td.getStartDate().after(WebUtil.toDateForString(currentDateStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss"))||(td.getEndDate()!=null&&td.getEndDate().before(WebUtil.toDateForString(currentDateStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss"))))
						{
							//是否要删除该任务?
							//taskDefineMap.remove(e.getKey());
							continue;
						}
						//判断下次执行日期是否有效（下次执行日期精确到分）						
						//如果无效重新计算下次执行日期，并更新到数据库
						//判断条件：下次执行日期为空或下次执行日期已过当前时间
						if(td.getNextExecTime()==null||WebUtil.formatDateString(td.getNextExecTime(), "yyyy-MM-dd HH:mm").compareToIgnoreCase(WebUtil.formatDateString(currentDate, "yyyy-MM-dd HH:mm"))<0)
						{
							countNextExecTime(td);
						}
						//如果当前时间等于下次执行日期（是否要考虑时间偏移问题），生成任务执行队列
						if(td.getNextExecTime()!=null&&WebUtil.formatDateString(td.getNextExecTime(), "yyyy-MM-dd HH:mm").compareToIgnoreCase(WebUtil.formatDateString(currentDate, "yyyy-MM-dd HH:mm"))==0)
						{
							//生成队列
							TaskQueue tq = new TaskQueue();
							tq.setCtime(currentDate);
							tq.setExecNum(0);
							tq.setStatus(1);
							tq.setTaskId(td.getTaskId());
							tq.setStartTime(td.getNextExecTime());
							if(td.getDayCycleType().equals("H"))
							{
								long end = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "H"))*60*60*1000L;
								tq.setEndTime(new Date(td.getNextExecTime().getTime()+end));
							} 
							//每分钟
							else if(td.getDayCycleType().equals("M"))
							{
								long end = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "m"))*60*1000L;
								tq.setEndTime(new Date(td.getNextExecTime().getTime()+end));
							} 
							//每秒钟
							else if(td.getDayCycleType().equals("S"))
							{
								long end = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "d"))*1000L;
								tq.setEndTime(new Date(td.getNextExecTime().getTime()+end));
							}
							//特定时间,因为只执行一次，所以无结束时间
							else if(td.getDayCycleType().equals("O"))
							{
								tq.setEndTime(null);
							}
							taskQueueMapper.insert(tq);
							//更新下次时间
							countNextExecTime(td);
						}
					}
				}
			}
		}.start();
		
	}
	
	//计算下一次执行时间
	private void countNextExecTime(TaskDefine td)
	{
		Date currentDate = new Date();
		TaskDefine updateTd = new TaskDefine();
		updateTd.setTaskId(td.getTaskId());
		//未设置周期则下一次执行时间为空
		if(td.getCycleType()==null||td.getCycle()==null||td.getCycle()==0||td.getDayCycleType()==null||td.getDayCycle()==null)
		{
			td.setNextExecTime(null);
			updateTd.setNextExecTime(null);
			updateTd.setMtime(currentDate);
			taskDefineMapper.updateByPrimaryKeySelective(updateTd);
			return;
		}
		
		//计算执行日期
		Date d = td.getNextExecTime();
		String nextDate = null;
		String nextTime = null;
		if(d==null)
			d = td.getStartDate();
		if(td.getStartDate().before(currentDate))
			d = currentDate;
		//计算执行时间
		Date dayStartTime = td.getDayStartTime();
		Date dayEndTime = td.getDayEndTime();
		if(dayStartTime==null)
			dayStartTime = WebUtil.toDateForString("00:00:00", "HH:mm:ss");
		if(dayEndTime==null)
			dayEndTime = WebUtil.toDateForString("23:59:59", "HH:mm:ss");
		//未到执行时间
		if(d.after(currentDate))
		{
			return;
		}
		else
		{
			//如果是当天执行，只计算下一次执行时间
			if(WebUtil.formatDateString(d, "yyyy-MM-dd").equalsIgnoreCase(WebUtil.formatDateString(currentDate, "yyyy-MM-dd")))
			{
				nextDate = WebUtil.formatDateString(d, "yyyy-MM-dd");
				nextTime = getNextTime(td,currentDate);
				if(!td.getDayCycleType().equals("O"))
				{
					if(nextTime.compareToIgnoreCase(WebUtil.formatDateString(dayStartTime, "HH:mm:ss"))<0||nextTime.compareToIgnoreCase(WebUtil.formatDateString(dayEndTime, "HH:mm:ss"))>0)
					{
						nextTime = WebUtil.formatDateString(dayStartTime, "HH:mm:ss");
						nextDate = getNextDate(td, d);
					}
				}
			}
			//如果非当天执行，计算机下一次执行日期，下一次执行时间为dayStartTime
			else
			{
				nextDate = getNextDate(td, d);
				if(td.getDayCycleType().equals("O"))
				{
					nextTime = WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss");		
				}
				else
				{
					nextTime = WebUtil.formatDateString(td.getDayStartTime(), "HH:mm:ss");		
				}
			}
		}
		//如果计算日期为空或者超出EndDate，nextDate=EndDate
		if(nextDate==null)
		{
			nextDate = WebUtil.formatDateString(td.getEndDate(), "yyyy-MM-dd");
			
		}
		//如果计算时间为空或者超出dayEndTime，nextTime=dayEndTime
		if(nextTime==null)
			nextTime = "00:00:00";		
		//如果下次执行日期超出结束日期,关闭该任务
		if(td.getEndDate()!=null&&WebUtil.toDateForString(nextDate,  "yyyy-MM-dd").after(td.getEndDate()))
		{
			td.setStatus(-1);
			updateTd.setStatus(-1);
			updateTd.setMtime(currentDate);
			taskDefineMapper.updateByPrimaryKeySelective(updateTd);
			return;
		}
		//更新下次执行时间
		Date  nextExecTime = WebUtil.toDateForString(nextDate+" "+nextTime, "yyyy-MM-dd HH:mm:ss");
		td.setNextExecTime(nextExecTime);
		updateTd.setNextExecTime(nextExecTime);
		updateTd.setMtime(currentDate);
		taskDefineMapper.updateByPrimaryKeySelective(updateTd);
	}

	//计算下次执行时间
	//天周期类型H：每小时，M：每分钟，S:每秒钟，O：特定时间（执行一次）
	private String getNextTime(TaskDefine td, Date d) {
		Date dayStartTime = td.getDayStartTime();
		if(dayStartTime==null)
			dayStartTime = WebUtil.toDateForString("00:00:00", "HH:mm:ss");
		 String nextTime = null;
		//每小时
		if(td.getDayCycleType().equals("H"))
		{
			int nextH = Integer.parseInt(WebUtil.formatDateString(d, "H"));
			int startH = Integer.parseInt(WebUtil.formatDateString(dayStartTime, "H"));
			int dayCycleH = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "H"));
			int h = ((nextH-startH)/dayCycleH+1)*dayCycleH;
			nextTime = WebUtil.formatDateString(new Date(dayStartTime.getTime()+h*60*60*1000), "HH:mm:ss");
		} 
		//每分钟
		else if(td.getDayCycleType().equals("M"))
		{
			long bm =( WebUtil.toDateForString(WebUtil.formatDateString(d, "HH:mm:ss"), "HH:mm:ss").getTime()-dayStartTime.getTime())/(1000*60);
			int dayCycleM = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "M"));
			long m = (bm/dayCycleM+1)*dayCycleM;
			nextTime = WebUtil.formatDateString(new Date(dayStartTime.getTime()+m*60*1000), "HH:mm:ss");
		} 
		//每秒钟
		else if(td.getDayCycleType().equals("S"))
		{
			long bs =( WebUtil.toDateForString(WebUtil.formatDateString(d, "HH:mm:ss"), "HH:mm:ss").getTime()-dayStartTime.getTime())/(1000);
			int dayCycleS = Integer.parseInt(WebUtil.formatDateString(td.getDayCycle(), "s"));
			long s = (bs/dayCycleS+1)*dayCycleS;
			nextTime = WebUtil.formatDateString(new Date(dayStartTime.getTime()+s*1000), "HH:mm:ss");
		}
		//特定时间
		else if(td.getDayCycleType().equals("O"))
		{
			nextTime = WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss");
		}
		return nextTime;
	}

	//计算下次执行日期
	//周期类型D：每天，W：每周，M：每月
	private String getNextDate(TaskDefine td, Date d) {
		String nextDate = null;
		//每天
		if(td.getCycleType().equals("D"))
		{
			//计算下次执行时间距离开始日期相差的天数
			int bd = (WebUtil.getDaysBetween(WebUtil.formatDateString(td.getStartDate(), "yyyy-MM-dd"), WebUtil.formatDateString(d, "yyyy-MM-dd"))/td.getCycle()+1)*td.getCycle();
			nextDate = WebUtil.formatDateString(new Date(td.getStartDate().getTime()+bd*86400000L), "yyyy-MM-dd");
		} 
		//每周
		else if(td.getCycleType().equals("W"))
		{
			int bd = 7+td.getCycle()-WebUtil.dayForWeek(d);
			nextDate = WebUtil.formatDateString(new Date(td.getStartDate().getTime()+bd*86400000L), "yyyy-MM-dd");
		} 
		//每月
		else if(td.getCycleType().equals("M"))
		{
			int m = WebUtil.getMonthsBetween(td.getStartDate(), d);
			nextDate = WebUtil.ofterMonthDate(td.getStartDate(), m/td.getCycle()+1);
		}
		return nextDate;
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
		Date currentDate = new Date();//当前时间
		TaskDefineExample example = new TaskDefineExample();
//		//有结束期
//		com.sunmw.web.entity.TaskDefineExample.Criteria tdc = example.createCriteria();
//		tdc.andStatusEqualTo(1);//状态为可执行
//		tdc.andStartDateLessThanOrEqualTo(currentDate);
//		tdc.andEndDateGreaterThanOrEqualTo(currentDate);
//		com.sunmw.web.entity.TaskDefineExample.Criteria tdc2 = example.or();
//		//无结束期
//		tdc.andStatusEqualTo(1);//状态为可执行
//		tdc.andStartDateLessThanOrEqualTo(currentDate);
//		tdc.andEndDateIsNull();
		List<TaskDefine> tdl = taskDefineMapper.selectByExample(example);
		if(WebUtil.isNullForList(tdl))
			return;
		//初始化任务定义池
		if(taskDefineMap==null)
			taskDefineMap = new HashMap();
		//遍历任务列表，判断是否需要更新taskDefineMap
		for(TaskDefine td:tdl)
		{
			//判断状态和结束时间
			//任务已结束，从taskDefineMap中删除该任务
			if(td.getStatus()!=1||(td.getEndDate()!=null&&td.getEndDate().before(currentDate)))
			{
				if(taskDefineMap.get(td.getTaskId())!=null)
					taskDefineMap.remove(td.getTaskId());				
			}
			//可以执行的任务，未到开始时间的任务也放入
			//如果任务不存在直接放入taskDefineMap
			//如果任务已存在根据ctime,mtime来判断是否更新过，重新放入taskDefineMap
			else
			{
				TaskDefine ctd = taskDefineMap.get(td.getTaskId());
				//直接放入
				if(ctd==null)
					taskDefineMap.put(td.getTaskId(), td);
				else
				{
					//目前直接更新，有可能会导致极限条件下任务冲突问题
					taskDefineMap.put(td.getTaskId(), td);					
				}
			}
		}
	}

	@Override
	public void readTaskQueueAndExecute() {
		//多线程方式执行
		//读取任务队列，状态是未执行，并且执行周期内
		//循环队列判断是否可以执行，可以执行先做执行标记，更新队列记录，放到线程中执行
		//线程执行时根据执行结果回写执行情况，如果执行失败判断是否要重试，重试次数及延迟执行时间
		

	}

}
