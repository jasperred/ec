package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_refund_remind_timeout table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_refund_remind_timeout"
 */

public abstract class BaseTbRefundRemindTimeout  implements Serializable {

	public static String REF = "TbRefundRemindTimeout";
	public static String PROP_EXIST_TIMEOUT = "ExistTimeout";
	public static String PROP_TIMEOUT = "Timeout";
	public static String PROP_REMIND_TYPE = "RemindType";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTbRefundRemindTimeout () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbRefundRemindTimeout (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer remindType;
	private boolean existTimeout;
	private java.util.Date timeout;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="local_id"
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
	 * Return the value associated with the column: remind_type
	 */
	public java.lang.Integer getRemindType () {
		return remindType;
	}

	/**
	 * Set the value related to the column: remind_type
	 * @param remindType the remind_type value
	 */
	public void setRemindType (java.lang.Integer remindType) {
		this.remindType = remindType;
	}



	/**
	 * Return the value associated with the column: exist_timeout
	 */
	public boolean isExistTimeout () {
		return existTimeout;
	}

	/**
	 * Set the value related to the column: exist_timeout
	 * @param existTimeout the exist_timeout value
	 */
	public void setExistTimeout (boolean existTimeout) {
		this.existTimeout = existTimeout;
	}



	/**
	 * Return the value associated with the column: timeout
	 */
	public java.util.Date getTimeout () {
		return timeout;
	}

	/**
	 * Set the value related to the column: timeout
	 * @param timeout the timeout value
	 */
	public void setTimeout (java.util.Date timeout) {
		this.timeout = timeout;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbRefundRemindTimeout)) return false;
		else {
			com.sunmw.taobao.entity.TbRefundRemindTimeout tbRefundRemindTimeout = (com.sunmw.taobao.entity.TbRefundRemindTimeout) obj;
			if (null == this.getId() || null == tbRefundRemindTimeout.getId()) return false;
			else return (this.getId().equals(tbRefundRemindTimeout.getId()));
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