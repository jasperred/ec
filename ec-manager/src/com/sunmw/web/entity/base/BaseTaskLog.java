package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the task_log table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="task_log"
 */

public abstract class BaseTaskLog  implements Serializable {

	public static String REF = "TaskLog";
	public static String PROP_LOG_START_TIME = "LogStartTime";
	public static String PROP_LOG_CONTENT = "LogContent";
	public static String PROP_LOG_END_TIME = "LogEndTime";
	public static String PROP_ID = "Id";
	public static String PROP_TASK_ID = "TaskId";


	// constructors
	public BaseTaskLog () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTaskLog (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long taskId;
	private java.util.Date logStartTime;
	private java.util.Date logEndTime;
	private java.lang.String logContent;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="task_log_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: task_id
	 */
	public java.lang.Long getTaskId () {
		return taskId;
	}

	/**
	 * Set the value related to the column: task_id
	 * @param taskId the task_id value
	 */
	public void setTaskId (java.lang.Long taskId) {
		this.taskId = taskId;
	}



	/**
	 * Return the value associated with the column: log_start_time
	 */
	public java.util.Date getLogStartTime () {
		return logStartTime;
	}

	/**
	 * Set the value related to the column: log_start_time
	 * @param logStartTime the log_start_time value
	 */
	public void setLogStartTime (java.util.Date logStartTime) {
		this.logStartTime = logStartTime;
	}



	/**
	 * Return the value associated with the column: log_end_time
	 */
	public java.util.Date getLogEndTime () {
		return logEndTime;
	}

	/**
	 * Set the value related to the column: log_end_time
	 * @param logEndTime the log_end_time value
	 */
	public void setLogEndTime (java.util.Date logEndTime) {
		this.logEndTime = logEndTime;
	}



	/**
	 * Return the value associated with the column: log_content
	 */
	public java.lang.String getLogContent () {
		return logContent;
	}

	/**
	 * Set the value related to the column: log_content
	 * @param logContent the log_content value
	 */
	public void setLogContent (java.lang.String logContent) {
		this.logContent = logContent;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.TaskLog)) return false;
		else {
			com.sunmw.web.entity.TaskLog taskLog = (com.sunmw.web.entity.TaskLog) obj;
			if (null == this.getId() || null == taskLog.getId()) return false;
			else return (this.getId().equals(taskLog.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}