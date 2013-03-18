package com.sunmw.web.bean.task;

import java.util.List;
import java.util.Map;

public interface TaskServices {
	
	/**
	 * 接口列表
	 * @return
	 */
	public List getIfDefine();
	
	/**
	 * 接口树列表
	 * @return
	 */
	public List getIfDefineByTree();
	/**
	 * 查询接口信息
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchIfDefine(Map param, int currentPage, int pageRow);
	
	/**
	 * 保存接口
	 * @param param
	 * @return
	 */
	public Map saveIfDefine(Map param);
	
	/**
	 * 删除接口
	 * @param ifDefineId
	 * @return
	 */
	public String deleteIfDefine(int ifDefineId);
	
	/**
	 * 查询任务
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchTaskDefine(Map param, int currentPage, int pageRow);
	
	/**
	 * 保存任务
	 * @param param
	 * @return
	 */
	public Map saveTaskDefine(Map param);
	
	/**
	 * 删除任务
	 * @param taskDefineId
	 * @return
	 */
	public String deleteTaskDefine(long taskDefineId);
	
	/**
	 * 查询任务队列
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchTaskQueue(Map param, int currentPage, int pageRow);
	
	/**
	 * 保存任务队列
	 * @param param
	 * @return
	 */
	public Map saveTaskQueue(Map param);
	
	/**
	 * 删除任务队列
	 * @param taskQueueId
	 * @return
	 */
	public String deleteTaskQueue(long taskQueueId);

	/**
	 * 查询任务日志
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchTaskLog(Map param, int currentPage, int pageRow);
	
}
