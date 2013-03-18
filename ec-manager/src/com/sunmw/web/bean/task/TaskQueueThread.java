package com.sunmw.web.bean.task;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.dao.IfDefineMapper;
import com.sunmw.web.dao.IfDefinePropMapper;
import com.sunmw.web.dao.TaskDefineMapper;
import com.sunmw.web.dao.TaskQueueMapper;
import com.sunmw.web.domain.IfDefine;
import com.sunmw.web.domain.TaskDefine;
import com.sunmw.web.domain.TaskQueue;
import com.sunmw.web.util.WebUtil;

public class TaskQueueThread extends Thread {
	static Logger logger = Logger.getLogger(TaskQueueThread.class);
	private IfDefineMapper ifDefineMapper;
	private IfDefinePropMapper ifDefinePropMapper;
	private TaskDefineMapper taskDefineMapper;
	private TaskQueueMapper taskQueueMapper;

	private TaskQueue taskQueue;

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

	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	public void setTaskQueue(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	public void run() {
		Date d = new Date();
		logger.info("begin:[task:"+taskQueue.getTaskId()+",queue:"+taskQueue.getTaskQueueId()+",start:"+WebUtil.formatDateString(taskQueue.getStartTime(), "yyyy-MM-dd HH:mm:ss")+"]");
		if (!(taskQueue.getStatus() == 1 || taskQueue.getStatus() == 2))
			return;
		if (taskQueue.getEndTime() != null && taskQueue.getEndTime().before(d))
			return;
		// 准备更新任务开始时间
		TaskDefine updatetd = new TaskDefine();
		updatetd.setTaskId(taskQueue.getTaskId());
		updatetd.setLastStartTime(new Date());
		taskDefineMapper.updateByPrimaryKeySelective(updatetd);
		// 得到IF配置
		TaskDefine td = taskDefineMapper.selectByPrimaryKey(taskQueue
				.getTaskId());
		if (td == null) {
			return;
		}
		IfDefine ifd = ifDefineMapper.selectByPrimaryKey(td.getIfId());
		if (ifd == null) {
			return;
		}
		String classType = ifd.getClassType();
		if(classType==null)
			classType = "spring";
		// 用反射机制执行类
		if (classType.toUpperCase().equals("SPRING")) {
			Object o = null;
			try {
				o = GetBeanServlet.getBean(ifd.getClassName());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
			if (o == null) {
				Date mtime = new Date();
				TaskQueue tq = new TaskQueue();
				tq.setTaskQueueId(taskQueue.getTaskQueueId());
				tq.setStatus(3);
				tq.setIsSuccess(0);
				tq.setExecNum(0);
				tq.setMtime(mtime);
				taskQueueMapper.updateByPrimaryKeySelective(tq);
				//更新任务最后执行时间和执行状态
				TaskDefine ntd = new TaskDefine();
				ntd.setTaskId(taskQueue.getTaskId());
				ntd.setLastEndTime(mtime);
				ntd.setIsLastSuccess("0");
				ntd.setMtime(mtime);
				taskDefineMapper.updateByPrimaryKeySelective(ntd);
				return;
			}
			Map param = new HashMap();
			param.put("StoreId", td.getStoreId());
			param.put("LastExecTime", td.getLastExecTime());
			Class c = o.getClass();
			try {
				Integer retrytimes = ifd.getErrorRetryTimes();
				if (retrytimes == null)
					retrytimes = 0;
				Integer waitSeconds = ifd.getRetryWaitSeconds();
				if (waitSeconds == null)
					waitSeconds = 0;
				int num = 1;
				for (int i = 0; i < retrytimes + 1; i++) {
					Method m = c.getMethod(ifd.getRunMethod(), Map.class);
					Map result = null;
					try {
						result = (Map) m.invoke(o, param);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String flag = "error";
					if(result!=null)
						flag = (String) result.get("Flag");
					Date mtime = new Date();
					// 执行成功
					if (flag.equals("success")) {
						//更新队列状态
						TaskQueue tq = new TaskQueue();
						tq.setTaskQueueId(taskQueue.getTaskQueueId());
						tq.setStatus(3);
						tq.setIsSuccess(1);
						tq.setExecNum(num);
						tq.setMtime(mtime);
						taskQueueMapper.updateByPrimaryKeySelective(tq);
						//更新任务最后执行时间和执行状态
						TaskDefine ntd = new TaskDefine();
						ntd.setTaskId(taskQueue.getTaskId());
						ntd.setLastEndTime(mtime);
						ntd.setLastExecTime(updatetd.getLastStartTime());
						ntd.setIsLastSuccess("1");
						ntd.setMtime(mtime);
						taskDefineMapper.updateByPrimaryKeySelective(ntd);
						logger.info("end:[task:"+taskQueue.getTaskId()+",queue:"+taskQueue.getTaskQueueId()+"]");
						return;
					}
					// 执行失败
					else {
						//更新队列状态
						TaskQueue tq = new TaskQueue();
						tq.setTaskQueueId(taskQueue.getTaskQueueId());
						tq.setStatus(3);
						tq.setIsSuccess(0);
						tq.setExecNum(num);
						tq.setMtime(mtime);
						taskQueueMapper.updateByPrimaryKeySelective(tq);
						//更新任务最后执行时间和执行状态
						TaskDefine ntd = new TaskDefine();
						ntd.setTaskId(taskQueue.getTaskId());
						ntd.setLastEndTime(mtime);
						ntd.setIsLastSuccess("0");
						ntd.setMtime(mtime);
						taskDefineMapper.updateByPrimaryKeySelective(ntd);
						Thread.sleep(ifd.getRetryWaitSeconds() * 1000);
						logger.info("error:[task:"+taskQueue.getTaskId()+",queue:"+taskQueue.getTaskQueueId()+"]");
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
