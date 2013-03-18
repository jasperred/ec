package com.sunmw.web.bean.task.impl;


import java.util.Date;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.sunmw.web.bean.task.JobConfig;
import com.sunmw.web.bean.task.SchedulerServices;
import com.sunmw.web.bean.task.TaskJob;

/**
 * Quartz任务操作
 * 删除作业
 * 派发作业
 * 立即派发作业
 * @author jasper
 *
 */
public class SchedulerServicesImpl implements SchedulerServices {
	private Scheduler quartzScheduler;


	public Scheduler getQuartzScheduler() {
		return quartzScheduler;
	}

	public void setQuartzScheduler(Scheduler quartzScheduler) {
		this.quartzScheduler = quartzScheduler;
	}

	/**
     * 移除作业
     * @param config
     * @return
     * @throws SchedulerException
     */
    public boolean remove(JobConfig config) throws SchedulerException {
        if(config == null) {
            return false;
        }
        return removeJob(config.getJobName(), config.getJobGroup());
    }

    /**
     * 派发作业
     * @param config
     * @throws Exception
     */
    public void schedule(JobConfig config) throws Exception {
        String triggerName = config.getTriggerName();
        String triggerGroup = config.getTriggerGroup();
        String cronStr = config.getCronStr();
        
        String jobName = config.getJobName();
        String jobGroup = config.getJobGroup();
        try {
			removeJob(jobName, jobGroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
        JobDetail jobDetail = new JobDetail(jobName, jobGroup, TaskJob.class);
        Map jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("TaskId", config.getTaskId());
        jobDataMap.put("TargetType", config.getTargetType());
        jobDataMap.put("TargetName", config.getTargetName());
        jobDataMap.put("TargetMethod", config.getTargetMethod());
        jobDataMap.put("Arguments", config.getArguments());
        schedule(triggerName, triggerGroup, cronStr, jobDetail,config.getStartTime(),config.getEndTime());
    }
    
    /**
     * 派发作业
     * @param config
     * @throws Exception
     */
    public void scheduleNow(JobConfig config,long repeatInterval,int repeatCount) throws Exception {
        String triggerName = config.getTriggerName()+"-now";
        String triggerGroup = config.getTriggerGroup()+"-now";
        
        String jobName = config.getJobName()+"-now";
        String jobGroup = config.getJobGroup()+"-now";
        try {
			removeJob(jobName, jobGroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
        JobDetail jobDetail = new JobDetail(jobName, jobGroup, TaskJob.class);
        Map jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("TaskId", config.getTaskId());
        jobDataMap.put("TargetType", config.getTargetType());
        jobDataMap.put("TargetName", config.getTargetName());
        jobDataMap.put("TargetMethod", config.getTargetMethod());
        jobDataMap.put("Arguments", config.getArguments());
        SimpleTrigger simpleTrigger = new SimpleTrigger(triggerName, triggerGroup);
        simpleTrigger.setRepeatInterval(repeatInterval);
        simpleTrigger.setRepeatCount(repeatCount);
        quartzScheduler.scheduleJob(jobDetail, simpleTrigger);
    }

    /**
     * 派发作业
     * @param name
     * @param group
     * @param cronStr
     * @param jobDtl
     * @throws Exception
     */
    private void schedule(String name, String group, String cronStr, JobDetail jobDtl,Date startTime,Date endTime)
        throws Exception {
        CronTrigger cronTrigger = new CronTrigger(name, group, jobDtl.getName(), jobDtl.getGroup(),
            cronStr);
        if(startTime!=null)
        	cronTrigger.setStartTime(startTime);
        if(endTime!=null)
        	cronTrigger.setEndTime(endTime);
        quartzScheduler.scheduleJob(jobDtl, cronTrigger);
    }

    /**
     * 移除作业
     * @param jobName
     * @param group
     * @return
     * @throws SchedulerException
     */
    private boolean removeJob(String jobName, String group) throws SchedulerException {
    	quartzScheduler.pauseJob(jobName, group);
        return quartzScheduler.deleteJob(jobName, group);
    }
}
