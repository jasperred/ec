package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the user_login table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="user_login"
 */

public abstract class BaseUserLogin  implements Serializable {

	public static String REF = "UserLogin";
	public static String PROP_USER_TYPE = "UserType";
	public static String PROP_ENABLED = "Enabled";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_CURRENT_PASSWORD = "CurrentPassword";
	public static String PROP_USER_NAME = "UserName";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_TEC_NO = "TecNo";
	public static String PROP_EMAIL = "Email";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_USER_NO = "UserNo";
	public static String PROP_PASSWORD_MODIFY_TIME = "PasswordModifyTime";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";


	// constructors
	public BaseUserLogin () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserLogin (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String userNo;
	private java.lang.String currentPassword;
	private java.lang.String userName;
	private java.lang.String userType;
	private java.lang.String email;
	private java.lang.String enabled;
	private java.lang.Integer companyId;
	private java.util.Date passwordModifyTime;
	private java.lang.String tecNo;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="user_id"
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
	 * Return the value associated with the column: user_no
	 */
	public java.lang.String getUserNo () {
		return userNo;
	}

	/**
	 * Set the value related to the column: user_no
	 * @param userNo the user_no value
	 */
	public void setUserNo (java.lang.String userNo) {
		this.userNo = userNo;
	}



	/**
	 * Return the value associated with the column: current_password
	 */
	public java.lang.String getCurrentPassword () {
		return currentPassword;
	}

	/**
	 * Set the value related to the column: current_password
	 * @param currentPassword the current_password value
	 */
	public void setCurrentPassword (java.lang.String currentPassword) {
		this.currentPassword = currentPassword;
	}



	/**
	 * Return the value associated with the column: user_name
	 */
	public java.lang.String getUserName () {
		return userName;
	}

	/**
	 * Set the value related to the column: user_name
	 * @param userName the user_name value
	 */
	public void setUserName (java.lang.String userName) {
		this.userName = userName;
	}



	/**
	 * Return the value associated with the column: user_type
	 */
	public java.lang.String getUserType () {
		return userType;
	}

	/**
	 * Set the value related to the column: user_type
	 * @param userType the user_type value
	 */
	public void setUserType (java.lang.String userType) {
		this.userType = userType;
	}



	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: ENABLED
	 */
	public java.lang.String getEnabled () {
		return enabled;
	}

	/**
	 * Set the value related to the column: ENABLED
	 * @param enabled the ENABLED value
	 */
	public void setEnabled (java.lang.String enabled) {
		this.enabled = enabled;
	}



	/**
	 * Return the value associated with the column: company_id
	 */
	public java.lang.Integer getCompanyId () {
		return companyId;
	}

	/**
	 * Set the value related to the column: company_id
	 * @param companyId the company_id value
	 */
	public void setCompanyId (java.lang.Integer companyId) {
		this.companyId = companyId;
	}



	/**
	 * Return the value associated with the column: password_modify_time
	 */
	public java.util.Date getPasswordModifyTime () {
		return passwordModifyTime;
	}

	/**
	 * Set the value related to the column: password_modify_time
	 * @param passwordModifyTime the password_modify_time value
	 */
	public void setPasswordModifyTime (java.util.Date passwordModifyTime) {
		this.passwordModifyTime = passwordModifyTime;
	}



	/**
	 * Return the value associated with the column: tec_no
	 */
	public java.lang.String getTecNo () {
		return tecNo;
	}

	/**
	 * Set the value related to the column: tec_no
	 * @param tecNo the tec_no value
	 */
	public void setTecNo (java.lang.String tecNo) {
		this.tecNo = tecNo;
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
		if (!(obj instanceof com.sunmw.web.entity.UserLogin)) return false;
		else {
			com.sunmw.web.entity.UserLogin userLogin = (com.sunmw.web.entity.UserLogin) obj;
			if (null == this.getId() || null == userLogin.getId()) return false;
			else return (this.getId().equals(userLogin.getId()));
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