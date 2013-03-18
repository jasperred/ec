package com.sunmw.web.entity.base;

import java.io.Serializable;


public abstract class BaseUserLoginHistoryPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Integer userId;
	private java.util.Date fromDate;


	public BaseUserLoginHistoryPK () {}
	
	public BaseUserLoginHistoryPK (
		java.lang.Integer userId,
		java.util.Date fromDate) {

		this.setUserId(userId);
		this.setFromDate(fromDate);
	}


	/**
	 * Return the value associated with the column: USER_ID
	 */
	public java.lang.Integer getUserId () {
		return userId;
	}

	/**
	 * Set the value related to the column: USER_ID
	 * @param userId the USER_ID value
	 */
	public void setUserId (java.lang.Integer userId) {
		this.userId = userId;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.UserLoginHistoryPK)) return false;
		else {
			com.sunmw.web.entity.UserLoginHistoryPK mObj = (com.sunmw.web.entity.UserLoginHistoryPK) obj;
			if (null != this.getUserId() && null != mObj.getUserId()) {
				if (!this.getUserId().equals(mObj.getUserId())) {
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
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getUserId()) {
				sb.append(this.getUserId().hashCode());
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
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}