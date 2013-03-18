package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the security_permission table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="security_permission"
 */

public abstract class BaseSecurityPermission  implements Serializable {

	public static String REF = "SecurityPermission";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_PERMISSION_TYPE = "PermissionType";
	public static String PROP_MUSER = "Muser";
	public static String PROP_DISP_INDEX = "DispIndex";
	public static String PROP_PARENT_PERMISSION_ID = "ParentPermissionId";


	// constructors
	public BaseSecurityPermission () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSecurityPermission (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String permissionType;
	private java.lang.String parentPermissionId;
	private java.lang.String description;
	private java.lang.Integer dispIndex;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="PERMISSION_ID"
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
	 * Return the value associated with the column: permission_type
	 */
	public java.lang.String getPermissionType () {
		return permissionType;
	}

	/**
	 * Set the value related to the column: permission_type
	 * @param permissionType the permission_type value
	 */
	public void setPermissionType (java.lang.String permissionType) {
		this.permissionType = permissionType;
	}



	/**
	 * Return the value associated with the column: parent_permission_id
	 */
	public java.lang.String getParentPermissionId () {
		return parentPermissionId;
	}

	/**
	 * Set the value related to the column: parent_permission_id
	 * @param parentPermissionId the parent_permission_id value
	 */
	public void setParentPermissionId (java.lang.String parentPermissionId) {
		this.parentPermissionId = parentPermissionId;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: disp_index
	 */
	public java.lang.Integer getDispIndex () {
		return dispIndex;
	}

	/**
	 * Set the value related to the column: disp_index
	 * @param dispIndex the disp_index value
	 */
	public void setDispIndex (java.lang.Integer dispIndex) {
		this.dispIndex = dispIndex;
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
		if (!(obj instanceof com.sunmw.web.entity.SecurityPermission)) return false;
		else {
			com.sunmw.web.entity.SecurityPermission securityPermission = (com.sunmw.web.entity.SecurityPermission) obj;
			if (null == this.getId() || null == securityPermission.getId()) return false;
			else return (this.getId().equals(securityPermission.getId()));
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