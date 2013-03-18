package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the if_define table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="if_define"
 */

public abstract class BaseIfDefine  implements Serializable {

	public static String REF = "IfDefine";
	public static String PROP_IF_TYPE = "IfType";
	public static String PROP_ERROR_RETRY_TIMES = "ErrorRetryTimes";
	public static String PROP_IF_CAT = "IfCat";
	public static String PROP_IF_CODE = "IfCode";
	public static String PROP_CLASS_TYPE = "ClassType";
	public static String PROP_STATUS = "Status";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_IF_NAME = "IfName";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_CLASS_NAME = "ClassName";
	public static String PROP_RUN_METHOD = "RunMethod";
	public static String PROP_RETRY_WAIT_SECONDS = "RetryWaitSeconds";


	// constructors
	public BaseIfDefine () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseIfDefine (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.String ifCode;
	private java.lang.String ifName;
	private java.lang.String ifCat;
	private java.lang.Integer status;
	private java.lang.Integer errorRetryTimes;
	private java.lang.Integer retryWaitSeconds;
	private java.lang.String runMethod;
	private java.lang.String className;
	private java.lang.String ifType;
	private java.lang.String classType;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="IF_id"
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
	 * Return the value associated with the column: IF_code
	 */
	public java.lang.String getIfCode () {
		return ifCode;
	}

	/**
	 * Set the value related to the column: IF_code
	 * @param ifCode the IF_code value
	 */
	public void setIfCode (java.lang.String ifCode) {
		this.ifCode = ifCode;
	}



	/**
	 * Return the value associated with the column: IF_name
	 */
	public java.lang.String getIfName () {
		return ifName;
	}

	/**
	 * Set the value related to the column: IF_name
	 * @param ifName the IF_name value
	 */
	public void setIfName (java.lang.String ifName) {
		this.ifName = ifName;
	}



	/**
	 * Return the value associated with the column: IF_cat
	 */
	public java.lang.String getIfCat () {
		return ifCat;
	}

	/**
	 * Set the value related to the column: IF_cat
	 * @param ifCat the IF_cat value
	 */
	public void setIfCat (java.lang.String ifCat) {
		this.ifCat = ifCat;
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
	 * Return the value associated with the column: Error_retry_times
	 */
	public java.lang.Integer getErrorRetryTimes () {
		return errorRetryTimes;
	}

	/**
	 * Set the value related to the column: Error_retry_times
	 * @param errorRetryTimes the Error_retry_times value
	 */
	public void setErrorRetryTimes (java.lang.Integer errorRetryTimes) {
		this.errorRetryTimes = errorRetryTimes;
	}



	/**
	 * Return the value associated with the column: Retry_wait_seconds
	 */
	public java.lang.Integer getRetryWaitSeconds () {
		return retryWaitSeconds;
	}

	/**
	 * Set the value related to the column: Retry_wait_seconds
	 * @param retryWaitSeconds the Retry_wait_seconds value
	 */
	public void setRetryWaitSeconds (java.lang.Integer retryWaitSeconds) {
		this.retryWaitSeconds = retryWaitSeconds;
	}



	/**
	 * Return the value associated with the column: Run_method
	 */
	public java.lang.String getRunMethod () {
		return runMethod;
	}

	/**
	 * Set the value related to the column: Run_method
	 * @param runMethod the Run_method value
	 */
	public void setRunMethod (java.lang.String runMethod) {
		this.runMethod = runMethod;
	}



	/**
	 * Return the value associated with the column: Class_name
	 */
	public java.lang.String getClassName () {
		return className;
	}

	/**
	 * Set the value related to the column: Class_name
	 * @param className the Class_name value
	 */
	public void setClassName (java.lang.String className) {
		this.className = className;
	}



	/**
	 * Return the value associated with the column: If_type
	 */
	public java.lang.String getIfType () {
		return ifType;
	}

	/**
	 * Set the value related to the column: If_type
	 * @param ifType the If_type value
	 */
	public void setIfType (java.lang.String ifType) {
		this.ifType = ifType;
	}



	/**
	 * Return the value associated with the column: Class_type
	 */
	public java.lang.String getClassType () {
		return classType;
	}

	/**
	 * Set the value related to the column: Class_type
	 * @param classType the Class_type value
	 */
	public void setClassType (java.lang.String classType) {
		this.classType = classType;
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
		if (!(obj instanceof com.sunmw.web.entity.IfDefine)) return false;
		else {
			com.sunmw.web.entity.IfDefine ifDefine = (com.sunmw.web.entity.IfDefine) obj;
			if (null == this.getId() || null == ifDefine.getId()) return false;
			else return (this.getId().equals(ifDefine.getId()));
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