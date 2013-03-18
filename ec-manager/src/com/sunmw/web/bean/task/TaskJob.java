package com.sunmw.web.bean.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.dao.TaskDefineMapper;
import com.sunmw.web.dao.TaskLogMapper;
import com.sunmw.web.domain.TaskDefine;
import com.sunmw.web.domain.TaskLog;
import com.sunmw.web.domain.TaskLogExample;

public class TaskJob implements Job {
	static Logger logger = Logger.getLogger(TaskJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			logger.info("job 开始执行,JobName:" + context.getJobDetail().getName());
			Date date = new Date();
			Map jobData = context.getJobDetail().getJobDataMap();
			if (jobData == null) {
				logger.error("JobGroup:" + context.getJobDetail().getName()
						+ ",JobName:" + context.getJobDetail().getGroup()
						+ "[JobDataMap] is null");
				return;
			}
			TaskDefineMapper taskDefineMapper = (TaskDefineMapper) GetBeanServlet
					.getBean("taskDefineMapper");
			Long taskId = (Long) jobData.get("TaskId");
			TaskLogMapper taskLogMapper = (TaskLogMapper) GetBeanServlet
					.getBean("taskLogMapper");
			// 任务日志插入
			TaskLog log = new TaskLog();
			log.setLogStartTime(date);
			log.setTaskId(taskId);
			log.setLogContent("接口开始执行。。。");
			taskLogMapper.insertSelective(log);
			TaskLog updatelog = new TaskLog();
			TaskLogExample tle = new TaskLogExample();
			TaskLogExample.Criteria tlec = tle.createCriteria();
			tlec.andTaskIdEqualTo(taskId);
			tlec.andLogStartTimeEqualTo(date);
			if (taskDefineMapper != null) {
				TaskDefine td = new TaskDefine();
				td.setTaskId(taskId);
				td.setLastStartTime(date);
				taskDefineMapper.updateByPrimaryKeySelective(td);
			}
			String targetType = (String) jobData.get("TargetType");
			if (targetType == null)
				targetType = "spring";
			// 用反射机制执行类
			if (targetType.toUpperCase().equals("SPRING")) {
				Object o = null;
				try {
					o = GetBeanServlet.getBean((String) jobData
							.get("TargetName"));
				} catch (Exception e1) {
					logger.error(e1.getMessage());
					// 更新日志
					updatelog.setLogEndTime(new Date());
					updatelog.setLogContent("执行失败,"+e1.getMessage()+","+e1.getCause().getMessage());
					taskLogMapper.updateByExampleSelective(updatelog, tle);
				}
				if (o == null) {
					// 更新日志
					updatelog.setLogEndTime(new Date());
					updatelog.setLogContent("执行失败,执行类未配置");
					taskLogMapper.updateByExampleSelective(updatelog, tle);

					return;
				}
				Class c = o.getClass();
				try {
					Object[] args = (Object[]) jobData.get("Arguments");
					if (taskDefineMapper != null) {
						if (args != null && args.length > 0 && args[0] != null) {

							TaskDefine td = taskDefineMapper
									.selectByPrimaryKey(taskId);
							if (td != null) {
								Map param = (Map) args[0];
								param.put("LastExecTime", td.getLastExecTime());
								args = new Object[] { param };
							}
						}
					}

					Method m = c.getMethod(
							(String) jobData.get("TargetMethod"), Map.class);
					Map result = (Map) m.invoke(o, args);
					if (result != null && result.get("Flag") != null
							&& result.get("Flag").equals("success")) {
						if (taskDefineMapper != null) {
							TaskDefine td = new TaskDefine();
							td.setTaskId(taskId);
							td.setLastExecTime(date);
							td.setNextExecTime(context.getNextFireTime());
							td.setLastEndTime(new Date());
							td.setIsLastSuccess("1");
							taskDefineMapper.updateByPrimaryKeySelective(td);
						}

						// 更新日志
						updatelog.setLogEndTime(new Date());
						updatelog.setLogContent("任务执行成功");
						taskLogMapper.updateByExampleSelective(updatelog, tle);
						return;
					} else {
						if (taskDefineMapper != null) {
							TaskDefine td = new TaskDefine();
							td.setTaskId(taskId);
							td.setLastExecTime(date);
							td.setNextExecTime(context.getNextFireTime());
							td.setLastEndTime(new Date());
							td.setIsLastSuccess("0");
							taskDefineMapper.updateByPrimaryKeySelective(td);
						}

						// 更新日志
						if (result == null) {
							updatelog.setLogContent("任务执行失败");
						} else
							updatelog.setLogContent("任务执行失败["
									+ result.get("Message") + "]");
						updatelog.setLogEndTime(new Date());
						taskLogMapper.updateByExampleSelective(updatelog, tle);
						return;
					}
				} catch (SecurityException e) {
					logger.error(e.getMessage());

					// 更新日志
					updatelog.setLogContent("执行失败,"+e.getMessage()+","+e.getCause().getMessage());
					updatelog.setLogEndTime(new Date());
					taskLogMapper.updateByExampleSelective(updatelog, tle);
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());

					// 更新日志
					updatelog.setLogContent("执行失败,"+e.getMessage()+","+e.getCause().getMessage());
					updatelog.setLogEndTime(new Date());
					taskLogMapper.updateByExampleSelective(updatelog, tle);
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage());

					// 更新日志
					updatelog.setLogContent("执行失败,"+e.getMessage()+","+e.getCause().getMessage());
					updatelog.setLogEndTime(new Date());
					taskLogMapper.updateByExampleSelective(updatelog, tle);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());

					// 更新日志
					updatelog.setLogContent("执行失败,"+e.getMessage()+","+e.getCause().getMessage());
					updatelog.setLogEndTime(new Date());
					taskLogMapper.updateByExampleSelective(updatelog, tle);
				} catch (InvocationTargetException e) {
					logger.error(e.getTargetException());

					// 更新日志
					updatelog.setLogContent("执行失败,"+e.getMessage()+","+e.getCause().getMessage());
					updatelog.setLogEndTime(new Date());
					taskLogMapper.updateByExampleSelective(updatelog, tle);
				}
				TaskDefine td = new TaskDefine();
				td.setTaskId(taskId);
				td.setLastExecTime(date);
				td.setNextExecTime(context.getNextFireTime());
				td.setLastEndTime(new Date());
				td.setIsLastSuccess("0");
				taskDefineMapper.updateByPrimaryKeySelective(td);
			}
		} catch (RuntimeException e) {
			logger.error("任务执行失败,"+e.getMessage()+","+e.getCause().getMessage());
		}
	}

}
