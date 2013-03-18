package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inventory_log table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inventory_log"
 */

public abstract class BaseInventoryLog  implements Serializable {

	public static String REF = "InventoryLog";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_IO_TYPE = "IoType";
	public static String PROP_INV_TYPE = "InvType";
	public static String PROP_LOG_TYPE = "LogType";
	public static String PROP_SKU_CD = "SkuCd";
	public static String PROP_INV_ID = "InvId";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_WH_ID = "WhId";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_ID = "Id";
	public static String PROP_MUSER = "Muser";
	public static String PROP_BILL_NO = "BillNo";


	// constructors
	public BaseInventoryLog () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInventoryLog (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String ioType;
	private java.lang.String logType;
	private java.lang.String billNo;
	private java.lang.Integer invId;
	private java.lang.String skuCd;
	private java.lang.Integer whId;
	private java.lang.String invType;
	private java.lang.Integer quantity;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="id"
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
	 * Return the value associated with the column: io_type
	 */
	public java.lang.String getIoType () {
		return ioType;
	}

	/**
	 * Set the value related to the column: io_type
	 * @param ioType the io_type value
	 */
	public void setIoType (java.lang.String ioType) {
		this.ioType = ioType;
	}



	/**
	 * Return the value associated with the column: log_type
	 */
	public java.lang.String getLogType () {
		return logType;
	}

	/**
	 * Set the value related to the column: log_type
	 * @param logType the log_type value
	 */
	public void setLogType (java.lang.String logType) {
		this.logType = logType;
	}



	/**
	 * Return the value associated with the column: bill_no
	 */
	public java.lang.String getBillNo () {
		return billNo;
	}

	/**
	 * Set the value related to the column: bill_no
	 * @param billNo the bill_no value
	 */
	public void setBillNo (java.lang.String billNo) {
		this.billNo = billNo;
	}



	/**
	 * Return the value associated with the column: inv_id
	 */
	public java.lang.Integer getInvId () {
		return invId;
	}

	/**
	 * Set the value related to the column: inv_id
	 * @param invId the inv_id value
	 */
	public void setInvId (java.lang.Integer invId) {
		this.invId = invId;
	}



	/**
	 * Return the value associated with the column: sku_cd
	 */
	public java.lang.String getSkuCd () {
		return skuCd;
	}

	/**
	 * Set the value related to the column: sku_cd
	 * @param skuCd the sku_cd value
	 */
	public void setSkuCd (java.lang.String skuCd) {
		this.skuCd = skuCd;
	}



	/**
	 * Return the value associated with the column: wh_id
	 */
	public java.lang.Integer getWhId () {
		return whId;
	}

	/**
	 * Set the value related to the column: wh_id
	 * @param whId the wh_id value
	 */
	public void setWhId (java.lang.Integer whId) {
		this.whId = whId;
	}



	/**
	 * Return the value associated with the column: inv_type
	 */
	public java.lang.String getInvType () {
		return invType;
	}

	/**
	 * Set the value related to the column: inv_type
	 * @param invType the inv_type value
	 */
	public void setInvType (java.lang.String invType) {
		this.invType = invType;
	}



	/**
	 * Return the value associated with the column: quantity
	 */
	public java.lang.Integer getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: quantity
	 * @param quantity the quantity value
	 */
	public void setQuantity (java.lang.Integer quantity) {
		this.quantity = quantity;
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
		if (!(obj instanceof com.sunmw.web.entity.InventoryLog)) return false;
		else {
			com.sunmw.web.entity.InventoryLog inventoryLog = (com.sunmw.web.entity.InventoryLog) obj;
			if (null == this.getId() || null == inventoryLog.getId()) return false;
			else return (this.getId().equals(inventoryLog.getId()));
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