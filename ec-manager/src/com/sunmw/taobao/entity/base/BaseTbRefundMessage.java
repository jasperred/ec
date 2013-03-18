package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_refund_message table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_refund_message"
 */

public abstract class BaseTbRefundMessage  implements Serializable {

	public static String REF = "TbRefundMessage";
	public static String PROP_REFUND_ID = "RefundId";
	public static String PROP_CREATED = "Created";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_OWNER_ID = "OwnerId";
	public static String PROP_ORIG_ID = "OrigId";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_ID = "Id";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_CONTENT = "Content";
	public static String PROP_MESSAGE_TYPE = "MessageType";
	public static String PROP_OWNER_NICK = "OwnerNick";
	public static String PROP_OWNER_ROLE = "OwnerRole";


	// constructors
	public BaseTbRefundMessage () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbRefundMessage (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer origId;
	private java.lang.Integer refundId;
	private java.lang.Integer ownerId;
	private java.lang.String ownerNick;
	private java.lang.String ownerRole;
	private java.lang.String content;
	private java.util.Date created;
	private java.lang.String messageType;
	private java.util.Date updateTime;
	private java.lang.String updateFlag;
	private java.lang.Integer storeId;



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
	 * Return the value associated with the column: id
	 */
	public java.lang.Integer getOrigId () {
		return origId;
	}

	/**
	 * Set the value related to the column: id
	 * @param origId the id value
	 */
	public void setOrigId (java.lang.Integer origId) {
		this.origId = origId;
	}



	/**
	 * Return the value associated with the column: refund_id
	 */
	public java.lang.Integer getRefundId () {
		return refundId;
	}

	/**
	 * Set the value related to the column: refund_id
	 * @param refundId the refund_id value
	 */
	public void setRefundId (java.lang.Integer refundId) {
		this.refundId = refundId;
	}



	/**
	 * Return the value associated with the column: owner_id
	 */
	public java.lang.Integer getOwnerId () {
		return ownerId;
	}

	/**
	 * Set the value related to the column: owner_id
	 * @param ownerId the owner_id value
	 */
	public void setOwnerId (java.lang.Integer ownerId) {
		this.ownerId = ownerId;
	}



	/**
	 * Return the value associated with the column: owner_nick
	 */
	public java.lang.String getOwnerNick () {
		return ownerNick;
	}

	/**
	 * Set the value related to the column: owner_nick
	 * @param ownerNick the owner_nick value
	 */
	public void setOwnerNick (java.lang.String ownerNick) {
		this.ownerNick = ownerNick;
	}



	/**
	 * Return the value associated with the column: owner_role
	 */
	public java.lang.String getOwnerRole () {
		return ownerRole;
	}

	/**
	 * Set the value related to the column: owner_role
	 * @param ownerRole the owner_role value
	 */
	public void setOwnerRole (java.lang.String ownerRole) {
		this.ownerRole = ownerRole;
	}



	/**
	 * Return the value associated with the column: content
	 */
	public java.lang.String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (java.lang.String content) {
		this.content = content;
	}



	/**
	 * Return the value associated with the column: created
	 */
	public java.util.Date getCreated () {
		return created;
	}

	/**
	 * Set the value related to the column: created
	 * @param created the created value
	 */
	public void setCreated (java.util.Date created) {
		this.created = created;
	}



	/**
	 * Return the value associated with the column: message_type
	 */
	public java.lang.String getMessageType () {
		return messageType;
	}

	/**
	 * Set the value related to the column: message_type
	 * @param messageType the message_type value
	 */
	public void setMessageType (java.lang.String messageType) {
		this.messageType = messageType;
	}



	/**
	 * Return the value associated with the column: update_time
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}

	/**
	 * Set the value related to the column: update_time
	 * @param updateTime the update_time value
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}



	/**
	 * Return the value associated with the column: update_flag
	 */
	public java.lang.String getUpdateFlag () {
		return updateFlag;
	}

	/**
	 * Set the value related to the column: update_flag
	 * @param updateFlag the update_flag value
	 */
	public void setUpdateFlag (java.lang.String updateFlag) {
		this.updateFlag = updateFlag;
	}



	/**
	 * Return the value associated with the column: store_id
	 */
	public java.lang.Integer getStoreId () {
		return storeId;
	}

	/**
	 * Set the value related to the column: store_id
	 * @param storeId the store_id value
	 */
	public void setStoreId (java.lang.Integer storeId) {
		this.storeId = storeId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbRefundMessage)) return false;
		else {
			com.sunmw.taobao.entity.TbRefundMessage tbRefundMessage = (com.sunmw.taobao.entity.TbRefundMessage) obj;
			if (null == this.getId() || null == tbRefundMessage.getId()) return false;
			else return (this.getId().equals(tbRefundMessage.getId()));
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