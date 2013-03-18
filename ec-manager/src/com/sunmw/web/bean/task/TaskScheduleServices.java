package com.sunmw.web.bean.task;

public interface TaskScheduleServices {
	
	//产生队列
	public void readTaskDefineGenericTaskQueue();
	//队列执行
	public void readTaskQueueAndExecute();
}
