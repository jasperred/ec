package com.sunmw.web.bean.task;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public interface SchedulerServices {

	public boolean remove(JobConfig config) throws SchedulerException;
	
	public void schedule(JobConfig config) throws Exception;
	
	public void scheduleNow(JobConfig config,long repeatInterval,int repeatCount) throws Exception;
}
