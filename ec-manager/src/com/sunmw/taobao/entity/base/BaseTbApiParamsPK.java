package com.sunmw.taobao.entity.base;

import java.io.Serializable;


public abstract class BaseTbApiParamsPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Integer id;
	private java.lang.String paramName;


	public BaseTbApiParamsPK () {}
	
	public BaseTbApiParamsPK (
		java.lang.Integer id,
		java.lang.String paramName) {

		this.setId(id);
		this.setParamName(paramName);
	}


	/**
	 * Return the value associated with the column: ID
	 */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the value related to the column: ID
	 * @param id the ID value
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
	}



	/**
	 * Return the value associated with the column: PARAM_NAME
	 */
	public java.lang.String getParamName () {
		return paramName;
	}

	/**
	 * Set the value related to the column: PARAM_NAME
	 * @param paramName the PARAM_NAME value
	 */
	public void setParamName (java.lang.String paramName) {
		this.paramName = paramName;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbApiParamsPK)) return false;
		else {
			com.sunmw.taobao.entity.TbApiParamsPK mObj = (com.sunmw.taobao.entity.TbApiParamsPK) obj;
			if (null != this.getId() && null != mObj.getId()) {
				if (!this.getId().equals(mObj.getId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getParamName() && null != mObj.getParamName()) {
				if (!this.getParamName().equals(mObj.getParamName())) {
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
			if (null != this.getId()) {
				sb.append(this.getId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getParamName()) {
				sb.append(this.getParamName().hashCode());
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