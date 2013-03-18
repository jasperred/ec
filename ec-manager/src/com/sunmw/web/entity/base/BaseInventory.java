package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inventory table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inventory"
 */

public abstract class BaseInventory  implements Serializable {

	public static String REF = "Inventory";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_INV_TYPE = "InvType";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_SKU_CD = "SkuCd";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_LOCAL_QTY = "LocalQty";
	public static String PROP_INV_STATUS = "InvStatus";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_WH_ID = "WhId";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_ID = "Id";
	public static String PROP_VERSION = "Version";
	public static String PROP_AVAILABLE_QUANTITY = "AvailableQuantity";


	// constructors
	public BaseInventory () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInventory (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String invType;
	private java.lang.String skuCd;
	private java.math.BigDecimal quantity;
	private java.math.BigDecimal availableQuantity;
	private java.lang.String invStatus;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.Integer whId;
	private java.lang.Integer version;
	private java.math.BigDecimal localQty;
	private java.util.Date updateTime;
	private java.lang.String updateFlag;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="inv_id"
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
	 * Return the value associated with the column: quantity
	 */
	public java.math.BigDecimal getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: quantity
	 * @param quantity the quantity value
	 */
	public void setQuantity (java.math.BigDecimal quantity) {
		this.quantity = quantity;
	}



	/**
	 * Return the value associated with the column: available_quantity
	 */
	public java.math.BigDecimal getAvailableQuantity () {
		return availableQuantity;
	}

	/**
	 * Set the value related to the column: available_quantity
	 * @param availableQuantity the available_quantity value
	 */
	public void setAvailableQuantity (java.math.BigDecimal availableQuantity) {
		this.availableQuantity = availableQuantity;
	}



	/**
	 * Return the value associated with the column: inv_status
	 */
	public java.lang.String getInvStatus () {
		return invStatus;
	}

	/**
	 * Set the value related to the column: inv_status
	 * @param invStatus the inv_status value
	 */
	public void setInvStatus (java.lang.String invStatus) {
		this.invStatus = invStatus;
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
	 * Return the value associated with the column: version
	 */
	public java.lang.Integer getVersion () {
		return version;
	}

	/**
	 * Set the value related to the column: version
	 * @param version the version value
	 */
	public void setVersion (java.lang.Integer version) {
		this.version = version;
	}



	/**
	 * Return the value associated with the column: local_qty
	 */
	public java.math.BigDecimal getLocalQty () {
		return localQty;
	}

	/**
	 * Set the value related to the column: local_qty
	 * @param localQty the local_qty value
	 */
	public void setLocalQty (java.math.BigDecimal localQty) {
		this.localQty = localQty;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.Inventory)) return false;
		else {
			com.sunmw.web.entity.Inventory inventory = (com.sunmw.web.entity.Inventory) obj;
			if (null == this.getId() || null == inventory.getId()) return false;
			else return (this.getId().equals(inventory.getId()));
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