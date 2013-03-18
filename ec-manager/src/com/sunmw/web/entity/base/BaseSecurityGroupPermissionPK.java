package com.sunmw.web.entity.base;

import java.io.Serializable;


public abstract class BaseSecurityGroupPermissionPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String groupId;
	private java.util.Date fromDate;
	private java.lang.String permissionId;


	public BaseSecurityGroupPermissionPK () {}
	
	public BaseSecurityGroupPermissionPK (
		java.lang.String groupId,
		java.util.Date fromDate,
		java.lang.String permissionId) {

		this.setGroupId(groupId);
		this.setFromDate(fromDate);
		this.setPermissionId(permissionId);
	}


	/**
	 * Return the value associated with the column: GROUP_ID
	 */
	public java.lang.String getGroupId () {
		return groupId;
	}

	/**
	 * Set the value related to the column: GROUP_ID
	 * @param groupId the GROUP_ID value
	 */
	public void setGroupId (java.lang.String groupId) {
		this.groupId = groupId;
	}



	/**
	 * Return the value associated with the column: FROM_DATE
	 */
	public java.util.Date getFromDate () {
		return fromDate;
	}

	/**
	 * Set the value related to the column: FROM_DATE
	 * @param fromDate the FROM_DATE value
	 */
	public void setFromDate (java.util.Date fromDate) {
		this.fromDate = fromDate;
	}



	/**
	 * Return the value associated with the column: PERMISSION_ID
	 */
	public java.lang.String getPermissionId () {
		return permissionId;
	}

	/**
	 * Set the value related to the column: PERMISSION_ID
	 * @param permissionId the PERMISSION_ID value
	 */
	public void setPermissionId (java.lang.String permissionId) {
		this.permissionId = permissionId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.SecurityGroupPermissionPK)) return false;
		else {
			com.sunmw.web.entity.SecurityGroupPermissionPK mObj = (com.sunmw.web.entity.SecurityGroupPermissionPK) obj;
			if (null != this.getGroupId() && null != mObj.getGroupId()) {
				if (!this.getGroupId().equals(mObj.getGroupId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getFromDate() && null != mObj.getFromDate()) {
				if (!this.getFromDate().equals(mObj.getFromDate())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getPermissionId() && null != mObj.getPermissionId()) {
				if (!this.getPermissionId().equals(mObj.getPermissionId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getGroupId()) {
				sb.append(this.getGroupId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getFromDate()) {
				sb.append(this.getFromDate().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getPermissionId()) {
				sb.append(this.getPermissionId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}