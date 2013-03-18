package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the user_login_session table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="user_login_session"
 */

public abstract class BaseUserLoginSession  implements Serializable {

	public static String REF = "UserLoginSession";
	public static String PROP_CREATED_STAMP = "CreatedStamp";
	public static String PROP_LAST_UPDATED_USER = "LastUpdatedUser";
	public static String PROP_SAVED_DATE = "SavedDate";
	public static String PROP_SESSION_DATA = "SessionData";
	public static String PROP_ID = "Id";
	public static String PROP_CREATED_USER = "CreatedUser";
	public static String PROP_LAST_UPDATED_STAMP = "LastUpdatedStamp";


	// constructors
	public BaseUserLoginSession () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserLoginSession (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.util.Date savedDate;
	private java.lang.String sessionData;
	private java.util.Date lastUpdatedStamp;
	private java.lang.String lastUpdatedUser;
	private java.util.Date createdStamp;
	private java.lang.String createdUser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="USER_LOGIN_ID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: SAVED_DATE
	 */
	public java.util.Date getSavedDate () {
		return savedDate;
	}

	/**
	 * Set the value related to the column: SAVED_DATE
	 * @param savedDate the SAVED_DATE value
	 */
	public void setSavedDate (java.util.Date savedDate) {
		this.savedDate = savedDate;
	}



	/**
	 * Return the value associated with the column: SESSION_DATA
	 */
	public java.lang.String getSessionData () {
		return sessionData;
	}

	/**
	 * Set the value related to the column: SESSION_DATA
	 * @param sessionData the SESSION_DATA value
	 */
	public void setSessionData (java.lang.String sessionData) {
		this.sessionData = sessionData;
	}



	/**
	 * Return the value associated with the column: LAST_UPDATED_STAMP
	 */
	public java.util.Date getLastUpdatedStamp () {
		return lastUpdatedStamp;
	}

	/**
	 * Set the value related to the column: LAST_UPDATED_STAMP
	 * @param lastUpdatedStamp the LAST_UPDATED_STAMP value
	 */
	public void setLastUpdatedStamp (java.util.Date lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}



	/**
	 * Return the value associated with the column: LAST_UPDATED_USER
	 */
	public java.lang.String getLastUpdatedUser () {
		return lastUpdatedUser;
	}

	/**
	 * Set the value related to the column: LAST_UPDATED_USER
	 * @param lastUpdatedUser the LAST_UPDATED_USER value
	 */
	public void setLastUpdatedUser (java.lang.String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}



	/**
	 * Return the value associated with the column: CREATED_STAMP
	 */
	public java.util.Date getCreatedStamp () {
		return createdStamp;
	}

	/**
	 * Set the value related to the column: CREATED_STAMP
	 * @param createdStamp the CREATED_STAMP value
	 */
	public void setCreatedStamp (java.util.Date createdStamp) {
		this.createdStamp = createdStamp;
	}



	/**
	 * Return the value associated with the column: CREATED_USER
	 */
	public java.lang.String getCreatedUser () {
		return createdUser;
	}

	/**
	 * Set the value related to the column: CREATED_USER
	 * @param createdUser the CREATED_USER value
	 */
	public void setCreatedUser (java.lang.String createdUser) {
		this.createdUser = createdUser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.UserLoginSession)) return false;
		else {
			com.sunmw.web.entity.UserLoginSession userLoginSession = (com.sunmw.web.entity.UserLoginSession) obj;
			if (null == this.getId() || null == userLoginSession.getId()) return false;
			else return (this.getId().equals(userLoginSession.getId()));
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