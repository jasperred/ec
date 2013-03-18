package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the check_repeat_no table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="check_repeat_no"
 */

public abstract class BaseCheckRepeatNo  implements Serializable {

	public static String REF = "CheckRepeatNo";


	// constructors
	public BaseCheckRepeatNo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCheckRepeatNo (
		java.lang.String no,
		java.lang.String type) {

		this.setNo(no);
		this.setType(type);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key

	private java.lang.String no;

	private java.lang.String type;



	/**
     * @hibernate.property
     *  column=no
	 * not-null=true
	 */
	public java.lang.String getNo () {
		return this.no;
	}

	/**
	 * Set the value related to the column: no
	 * @param no the no value
	 */
	public void setNo (java.lang.String no) {
		this.no = no;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
     * @hibernate.property
     *  column=type
	 * not-null=true
	 */
	public java.lang.String getType () {
		return this.type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
		this.hashCode = Integer.MIN_VALUE;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.CheckRepeatNo)) return false;
		else {
			com.sunmw.web.entity.CheckRepeatNo checkRepeatNo = (com.sunmw.web.entity.CheckRepeatNo) obj;
			if (null != this.getNo() && null != checkRepeatNo.getNo()) {
				if (!this.getNo().equals(checkRepeatNo.getNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getType() && null != checkRepeatNo.getType()) {
				if (!this.getType().equals(checkRepeatNo.getType())) {
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
			if (null != this.getNo()) {
				sb.append(this.getNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getType()) {
				sb.append(this.getType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}