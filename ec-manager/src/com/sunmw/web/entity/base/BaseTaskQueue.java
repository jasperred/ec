package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the task_queue table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="task_queue"
 */

public abstract class BaseTaskQueue  implements Serializable {

	public static String REF = "TaskQueue";
	public static String PROP_STATUS = "Status";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_IS_SUCCESS = "IsSuccess";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_END_TIME = "EndTime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_START_TIME = "StartTime";
	public static String PROP_TASK_ID = "TaskId";
	public static String PROP_EXEC_NUM = "ExecNum";


	// constructors
	public BaseTaskQueue () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTaskQueue (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer taskId;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.Integer status;
	private java.lang.Integer isSuccess;
	private java.lang.Integer execNum;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="task_queue_id"
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
	public java.lang.Integer getTaskId () {
		return taskId;
	}

	/**
	 * Set the value related to the column: task_id
	 * @param taskId the task_id value
	 */
	public void setTaskId (java.lang.Integer taskId) {
		this.taskId = taskId;
	}



	/**
	 * Return the value associated with the column: start_time
	 */
	public java.util.Date getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * @param startTime the start_time value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}



	/**
	 * Return the value associated with the column: end_time
	 */
	public java.util.Date getEndTime () {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * @param endTime the end_time value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.Integer getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.Integer status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: is_success
	 */
	public java.lang.Integer getIsSuccess () {
		return isSuccess;
	}

	/**
	 * Set the value related to the column: is_success
	 * @param isSuccess the is_success value
	 */
	public void setIsSuccess (java.lang.Integer isSuccess) {
		this.isSuccess = isSuccess;
	}



	/**
	 * Return the value associated with the column: exec_num
	 */
	public java.lang.Integer getExecNum () {
		return execNum;
	}

	/**
	 * Set the value related to the column: exec_num
	 * @param execNum the exec_num value
	 */
	public void setExecNum (java.lang.Integer execNum) {
		this.execNum = execNum;
	}



	/**
	 * Return the value associated with the column: ctime
	 */
	public java.util.Date getCtime () {
		return ctime;
	}

	/**
	 * Set the value related to the column: ctime
	 * @param ctime the ctime value
	 */
	public void setCtime (java.util.Date ctime) {
		this.ctime = ctime;
	}



	/**
	 * Return the value associated with the column: cuser
	 */
	public java.lang.String getCuser () {
		return cuser;
	}

	/**
	 * Set the value related to the column: cuser
	 * @param cuser the cuser value
	 */
	public void setCuser (java.lang.String cuser) {
		this.cuser = cuser;
	}



	/**
	 * Return the value associated with the column: mtime
	 */
	public java.util.Date getMtime () {
		return mtime;
	}

	/**
	 * Set the value related to the column: mtime
	 * @param mtime the mtime value
	 */
	public void setMtime (java.util.Date mtime) {
		this.mtime = mtime;
	}



	/**
	 * Return the value associated with the column: muser
	 */
	public java.lang.String getMuser () {
		return muser;
	}

	/**
	 * Set the value related to the column: muser
	 * @param muser the muser value
	 */
	public void setMuser (java.lang.String muser) {
		this.muser = muser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.TaskQueue)) return false;
		else {
			com.sunmw.web.entity.TaskQueue taskQueue = (com.sunmw.web.entity.TaskQueue) obj;
			if (null == this.getId() || null == taskQueue.getId()) return false;
			else return (this.getId().equals(taskQueue.getId()));
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