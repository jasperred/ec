package com.sunmw.web.bean.task;

import java.util.Map;

/**
 * 任务管理接口
 * 读取任务列表
 * 立即执行任务
 * @author jasper
 *
 */
public interface JobManager {
	
	/**
	 * 读取任务列表执行
	 */
	public void readTask();
	
	/**
	 * 立即执行任务
	 * @param taskId
	 * @return
	 */
	public Map execTask(Long taskId);
}
