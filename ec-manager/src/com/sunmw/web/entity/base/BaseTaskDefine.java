package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the task_define table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="task_define"
 */

public abstract class BaseTaskDefine  implements Serializable {

	public static String REF = "TaskDefine";
	public static String PROP_NEXT_EXEC_TIME = "NextExecTime";
	public static String PROP_DAY_START_TIME = "DayStartTime";
	public static String PROP_CRON_EXPRESSION = "CronExpression";
	public static String PROP_IF_ID = "IfId";
	public static String PROP_DAY_CYCLE = "DayCycle";
	public static String PROP_DAY_CYCLE_TYPE = "DayCycleType";
	public static String PROP_DAY_END_TIME = "DayEndTime";
	public static String PROP_STATUS = "Status";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_END_DATE = "EndDate";
	public static String PROP_LAST_EXEC_TIME = "LastExecTime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_START_DATE = "StartDate";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_CYCLE = "Cycle";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_LAST_END_TIME = "LastEndTime";
	public static String PROP_LAST_SUCCESS = "LastSuccess";
	public static String PROP_CYCLE_TYPE = "CycleType";
	public static String PROP_LAST_START_TIME = "LastStartTime";
	public static String PROP_IS_LOAD = "IsLoad";


	// constructors
	public BaseTaskDefine () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTaskDefine (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTaskDefine (
		java.lang.Long id,
		java.lang.Long ifId,
		java.util.Date startDate,
		java.util.Date endDate) {

		this.setId(id);
		this.setIfId(ifId);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.Long ifId;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private java.util.Date lastStartTime;
	private java.util.Date lastEndTime;
	private java.lang.String lastSuccess;
	private java.lang.String cycleType;
	private java.lang.Integer cycle;
	private java.lang.Integer status;
	private java.lang.Integer storeId;
	private java.util.Date nextExecTime;
	private java.lang.String dayCycleType;
	private java.util.Date dayCycle;
	private java.util.Date dayStartTime;
	private java.util.Date dayEndTime;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;
	private java.util.Date lastExecTime;
	private java.lang.String cronExpression;
	private java.lang.String isLoad;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Task_id"
     */
	public java.lang.Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: IF_id
	 */
	public java.lang.Long getIfId () {
		return ifId;
	}

	/**
	 * Set the value related to the column: IF_id
	 * @param ifId the IF_id value
	 */
	public void setIfId (java.lang.Long ifId) {
		this.ifId = ifId;
	}



	/**
	 * Return the value associated with the column: Start_date
	 */
	public java.util.Date getStartDate () {
		return startDate;
	}

	/**
	 * Set the value related to the column: Start_date
	 * @param startDate the Start_date value
	 */
	public void setStartDate (java.util.Date startDate) {
		this.startDate = startDate;
	}



	/**
	 * Return the value associated with the column: End_date
	 */
	public java.util.Date getEndDate () {
		return endDate;
	}

	/**
	 * Set the value related to the column: End_date
	 * @param endDate the End_date value
	 */
	public void setEndDate (java.util.Date endDate) {
		this.endDate = endDate;
	}



	/**
	 * Return the value associated with the column: Last_start_time
	 */
	public java.util.Date getLastStartTime () {
		return lastStartTime;
	}

	/**
	 * Set the value related to the column: Last_start_time
	 * @param lastStartTime the Last_start_time value
	 */
	public void setLastStartTime (java.util.Date lastStartTime) {
		this.lastStartTime = lastStartTime;
	}



	/**
	 * Return the value associated with the column: Last_end_time
	 */
	public java.util.Date getLastEndTime () {
		return lastEndTime;
	}

	/**
	 * Set the value related to the column: Last_end_time
	 * @param lastEndTime the Last_end_time value
	 */
	public void setLastEndTime (java.util.Date lastEndTime) {
		this.lastEndTime = lastEndTime;
	}



	/**
	 * Return the value associated with the column: Is_last_success
	 */
	public java.lang.String getLastSuccess () {
		return lastSuccess;
	}

	/**
	 * Set the value related to the column: Is_last_success
	 * @param lastSuccess the Is_last_success value
	 */
	public void setLastSuccess (java.lang.String lastSuccess) {
		this.lastSuccess = lastSuccess;
	}



	/**
	 * Return the value associated with the column: Cycle_type
	 */
	public java.lang.String getCycleType () {
		return cycleType;
	}

	/**
	 * Set the value related to the column: Cycle_type
	 * @param cycleType the Cycle_type value
	 */
	public void setCycleType (java.lang.String cycleType) {
		this.cycleType = cycleType;
	}



	/**
	 * Return the value associated with the column: Cycle
	 */
	public java.lang.Integer getCycle () {
		return cycle;
	}

	/**
	 * Set the value related to the column: Cycle
	 * @param cycle the Cycle value
	 */
	public void setCycle (java.lang.Integer cycle) {
		this.cycle = cycle;
	}



	/**
	 * Return the value associated with the column: Status
	 */
	public java.lang.Integer getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: Status
	 * @param status the Status value
	 */
	public void setStatus (java.lang.Integer status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: store_id
	 */
	public java.lang.Integer getStoreId () {
		return storeId;
	}

	/**
	 * Set the value related to the column: store_id
	 * @param storeId the store_id value
	 */
	public void setStoreId (java.lang.Integer storeId) {
		this.storeId = storeId;
	}



	/**
	 * Return the value associated with the column: Next_exec_time
	 */
	public java.util.Date getNextExecTime () {
		return nextExecTime;
	}

	/**
	 * Set the value related to the column: Next_exec_time
	 * @param nextExecTime the Next_exec_time value
	 */
	public void setNextExecTime (java.util.Date nextExecTime) {
		this.nextExecTime = nextExecTime;
	}



	/**
	 * Return the value associated with the column: Day_cycle_type
	 */
	public java.lang.String getDayCycleType () {
		return dayCycleType;
	}

	/**
	 * Set the value related to the column: Day_cycle_type
	 * @param dayCycleType the Day_cycle_type value
	 */
	public void setDayCycleType (java.lang.String dayCycleType) {
		this.dayCycleType = dayCycleType;
	}



	/**
	 * Return the value associated with the column: Day_cycle
	 */
	public java.util.Date getDayCycle () {
		return dayCycle;
	}

	/**
	 * Set the value related to the column: Day_cycle
	 * @param dayCycle the Day_cycle value
	 */
	public void setDayCycle (java.util.Date dayCycle) {
		this.dayCycle = dayCycle;
	}



	/**
	 * Return the value associated with the column: Day_start_time
	 */
	public java.util.Date getDayStartTime () {
		return dayStartTime;
	}

	/**
	 * Set the value related to the column: Day_start_time
	 * @param dayStartTime the Day_start_time value
	 */
	public void setDayStartTime (java.util.Date dayStartTime) {
		this.dayStartTime = dayStartTime;
	}



	/**
	 * Return the value associated with the column: Day_end_time
	 */
	public java.util.Date getDayEndTime () {
		return dayEndTime;
	}

	/**
	 * Set the value related to the column: Day_end_time
	 * @param dayEndTime the Day_end_time value
	 */
	public void setDayEndTime (java.util.Date dayEndTime) {
		this.dayEndTime = dayEndTime;
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



	/**
	 * Return the value associated with the column: Last_exec_time
	 */
	public java.util.Date getLastExecTime () {
		return lastExecTime;
	}

	/**
	 * Set the value related to the column: Last_exec_time
	 * @param lastExecTime the Last_exec_time value
	 */
	public void setLastExecTime (java.util.Date lastExecTime) {
		this.lastExecTime = lastExecTime;
	}



	/**
	 * Return the value associated with the column: cron_expression
	 */
	public java.lang.String getCronExpression () {
		return cronExpression;
	}

	/**
	 * Set the value related to the column: cron_expression
	 * @param cronExpression the cron_expression value
	 */
	public void setCronExpression (java.lang.String cronExpression) {
		this.cronExpression = cronExpression;
	}



	/**
	 * Return the value associated with the column: is_load
	 */
	public java.lang.String getIsLoad () {
		return isLoad;
	}

	/**
	 * Set the value related to the column: is_load
	 * @param isLoad the is_load value
	 */
	public void setIsLoad (java.lang.String isLoad) {
		this.isLoad = isLoad;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.TaskDefine)) return false;
		else {
			com.sunmw.web.entity.TaskDefine taskDefine = (com.sunmw.web.entity.TaskDefine) obj;
			if (null == this.getId() || null == taskDefine.getId()) return false;
			else return (this.getId().equals(taskDefine.getId()));
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