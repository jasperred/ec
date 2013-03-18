package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the user_login_history table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="user_login_history"
 */

public abstract class BaseUserLoginHistory  implements Serializable {

	public static String REF = "UserLoginHistory";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_THRU_DATE = "ThruDate";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_VISIT_ID = "VisitId";
	public static String PROP_SUCCESSFUL_LOGIN = "SuccessfulLogin";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_PASSWORD_USED = "PasswordUsed";


	// constructors
	public BaseUserLoginHistory () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserLoginHistory (com.sunmw.web.entity.UserLoginHistoryPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.sunmw.web.entity.UserLoginHistoryPK id;

	// fields
	private java.lang.String visitId;
	private java.util.Date thruDate;
	private java.lang.String passwordUsed;
	private java.lang.String successfulLogin;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.sunmw.web.entity.UserLoginHistoryPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.sunmw.web.entity.UserLoginHistoryPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: VISIT_ID
	 */
	public java.lang.String getVisitId () {
		return visitId;
	}

	/**
	 * Set the value related to the column: VISIT_ID
	 * @param visitId the VISIT_ID value
	 */
	public void setVisitId (java.lang.String visitId) {
		this.visitId = visitId;
	}



	/**
	 * Return the value associated with the column: THRU_DATE
	 */
	public java.util.Date getThruDate () {
		return thruDate;
	}

	/**
	 * Set the value related to the column: THRU_DATE
	 * @param thruDate the THRU_DATE value
	 */
	public void setThruDate (java.util.Date thruDate) {
		this.thruDate = thruDate;
	}



	/**
	 * Return the value associated with the column: PASSWORD_USED
	 */
	public java.lang.String getPasswordUsed () {
		return passwordUsed;
	}

	/**
	 * Set the value related to the column: PASSWORD_USED
	 * @param passwordUsed the PASSWORD_USED value
	 */
	public void setPasswordUsed (java.lang.String passwordUsed) {
		this.passwordUsed = passwordUsed;
	}



	/**
	 * Return the value associated with the column: SUCCESSFUL_LOGIN
	 */
	public java.lang.String getSuccessfulLogin () {
		return successfulLogin;
	}

	/**
	 * Set the value related to the column: SUCCESSFUL_LOGIN
	 * @param successfulLogin the SUCCESSFUL_LOGIN value
	 */
	public void setSuccessfulLogin (java.lang.String successfulLogin) {
		this.successfulLogin = successfulLogin;
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
		if (!(obj instanceof com.sunmw.web.entity.UserLoginHistory)) return false;
		else {
			com.sunmw.web.entity.UserLoginHistory userLoginHistory = (com.sunmw.web.entity.UserLoginHistory) obj;
			if (null == this.getId() || null == userLoginHistory.getId()) return false;
			else return (this.getId().equals(userLoginHistory.getId()));
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