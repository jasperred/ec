package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the unit_item_master table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="unit_item_master"
 */

public abstract class BaseUnitItemMaster  implements Serializable {

	public static String REF = "UnitItemMaster";
	public static String PROP_INV_UPDATE_FLAG = "InvUpdateFlag";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_PRICE_UPDATE_TIME = "PriceUpdateTime";
	public static String PROP_ITEM_DESC = "ItemDesc";
	public static String PROP_RECEIVE1 = "Receive1";
	public static String PROP_RECEIVE2 = "Receive2";
	public static String PROP_ITEM_STD_PRICE = "ItemStdPrice";
	public static String PROP_ITEM_CD = "ItemCd";
	public static String PROP_RECEIVE6 = "Receive6";
	public static String PROP_RECEIVE5 = "Receive5";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_RECEIVE4 = "Receive4";
	public static String PROP_ITEM_STATUS = "ItemStatus";
	public static String PROP_RECEIVE3 = "Receive3";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_ITEM_TYPE = "ItemType";
	public static String PROP_ITEM_NAME = "ItemName";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_ITEM_PRICE = "ItemPrice";
	public static String PROP_PRICE_UPDATE_FLAG = "PriceUpdateFlag";
	public static String PROP_INV_UPDATE_TIME = "InvUpdateTime";


	// constructors
	public BaseUnitItemMaster () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUnitItemMaster (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String itemType;
	private java.lang.String itemCd;
	private java.lang.String itemName;
	private java.lang.String itemDesc;
	private java.math.BigDecimal itemPrice;
	private java.math.BigDecimal itemStdPrice;
	private java.lang.String itemStatus;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String receive1;
	private java.lang.String receive2;
	private java.lang.String receive3;
	private java.lang.String receive4;
	private java.lang.String receive5;
	private java.lang.String receive6;
	private java.lang.Integer storeId;
	private java.lang.Integer companyId;
	private java.lang.String invUpdateFlag;
	private java.util.Date invUpdateTime;
	private java.lang.String priceUpdateFlag;
	private java.util.Date priceUpdateTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="item_id"
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
	 * Return the value associated with the column: item_type
	 */
	public java.lang.String getItemType () {
		return itemType;
	}

	/**
	 * Set the value related to the column: item_type
	 * @param itemType the item_type value
	 */
	public void setItemType (java.lang.String itemType) {
		this.itemType = itemType;
	}



	/**
	 * Return the value associated with the column: item_cd
	 */
	public java.lang.String getItemCd () {
		return itemCd;
	}

	/**
	 * Set the value related to the column: item_cd
	 * @param itemCd the item_cd value
	 */
	public void setItemCd (java.lang.String itemCd) {
		this.itemCd = itemCd;
	}



	/**
	 * Return the value associated with the column: item_name
	 */
	public java.lang.String getItemName () {
		return itemName;
	}

	/**
	 * Set the value related to the column: item_name
	 * @param itemName the item_name value
	 */
	public void setItemName (java.lang.String itemName) {
		this.itemName = itemName;
	}



	/**
	 * Return the value associated with the column: item_desc
	 */
	public java.lang.String getItemDesc () {
		return itemDesc;
	}

	/**
	 * Set the value related to the column: item_desc
	 * @param itemDesc the item_desc value
	 */
	public void setItemDesc (java.lang.String itemDesc) {
		this.itemDesc = itemDesc;
	}



	/**
	 * Return the value associated with the column: item_price
	 */
	public java.math.BigDecimal getItemPrice () {
		return itemPrice;
	}

	/**
	 * Set the value related to the column: item_price
	 * @param itemPrice the item_price value
	 */
	public void setItemPrice (java.math.BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}



	/**
	 * Return the value associated with the column: item_std_price
	 */
	public java.math.BigDecimal getItemStdPrice () {
		return itemStdPrice;
	}

	/**
	 * Set the value related to the column: item_std_price
	 * @param itemStdPrice the item_std_price value
	 */
	public void setItemStdPrice (java.math.BigDecimal itemStdPrice) {
		this.itemStdPrice = itemStdPrice;
	}



	/**
	 * Return the value associated with the column: item_status
	 */
	public java.lang.String getItemStatus () {
		return itemStatus;
	}

	/**
	 * Set the value related to the column: item_status
	 * @param itemStatus the item_status value
	 */
	public void setItemStatus (java.lang.String itemStatus) {
		this.itemStatus = itemStatus;
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
	 * Return the value associated with the column: receive1
	 */
	public java.lang.String getReceive1 () {
		return receive1;
	}

	/**
	 * Set the value related to the column: receive1
	 * @param receive1 the receive1 value
	 */
	public void setReceive1 (java.lang.String receive1) {
		this.receive1 = receive1;
	}



	/**
	 * Return the value associated with the column: receive2
	 */
	public java.lang.String getReceive2 () {
		return receive2;
	}

	/**
	 * Set the value related to the column: receive2
	 * @param receive2 the receive2 value
	 */
	public void setReceive2 (java.lang.String receive2) {
		this.receive2 = receive2;
	}



	/**
	 * Return the value associated with the column: receive3
	 */
	public java.lang.String getReceive3 () {
		return receive3;
	}

	/**
	 * Set the value related to the column: receive3
	 * @param receive3 the receive3 value
	 */
	public void setReceive3 (java.lang.String receive3) {
		this.receive3 = receive3;
	}



	/**
	 * Return the value associated with the column: receive4
	 */
	public java.lang.String getReceive4 () {
		return receive4;
	}

	/**
	 * Set the value related to the column: receive4
	 * @param receive4 the receive4 value
	 */
	public void setReceive4 (java.lang.String receive4) {
		this.receive4 = receive4;
	}



	/**
	 * Return the value associated with the column: receive5
	 */
	public java.lang.String getReceive5 () {
		return receive5;
	}

	/**
	 * Set the value related to the column: receive5
	 * @param receive5 the receive5 value
	 */
	public void setReceive5 (java.lang.String receive5) {
		this.receive5 = receive5;
	}



	/**
	 * Return the value associated with the column: receive6
	 */
	public java.lang.String getReceive6 () {
		return receive6;
	}

	/**
	 * Set the value related to the column: receive6
	 * @param receive6 the receive6 value
	 */
	public void setReceive6 (java.lang.String receive6) {
		this.receive6 = receive6;
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
	 * Return the value associated with the column: inv_update_flag
	 */
	public java.lang.String getInvUpdateFlag () {
		return invUpdateFlag;
	}

	/**
	 * Set the value related to the column: inv_update_flag
	 * @param invUpdateFlag the inv_update_flag value
	 */
	public void setInvUpdateFlag (java.lang.String invUpdateFlag) {
		this.invUpdateFlag = invUpdateFlag;
	}



	/**
	 * Return the value associated with the column: inv_update_time
	 */
	public java.util.Date getInvUpdateTime () {
		return invUpdateTime;
	}

	/**
	 * Set the value related to the column: inv_update_time
	 * @param invUpdateTime the inv_update_time value
	 */
	public void setInvUpdateTime (java.util.Date invUpdateTime) {
		this.invUpdateTime = invUpdateTime;
	}



	/**
	 * Return the value associated with the column: price_update_flag
	 */
	public java.lang.String getPriceUpdateFlag () {
		return priceUpdateFlag;
	}

	/**
	 * Set the value related to the column: price_update_flag
	 * @param priceUpdateFlag the price_update_flag value
	 */
	public void setPriceUpdateFlag (java.lang.String priceUpdateFlag) {
		this.priceUpdateFlag = priceUpdateFlag;
	}



	/**
	 * Return the value associated with the column: price_update_time
	 */
	public java.util.Date getPriceUpdateTime () {
		return priceUpdateTime;
	}

	/**
	 * Set the value related to the column: price_update_time
	 * @param priceUpdateTime the price_update_time value
	 */
	public void setPriceUpdateTime (java.util.Date priceUpdateTime) {
		this.priceUpdateTime = priceUpdateTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.UnitItemMaster)) return false;
		else {
			com.sunmw.web.entity.UnitItemMaster unitItemMaster = (com.sunmw.web.entity.UnitItemMaster) obj;
			if (null == this.getId() || null == unitItemMaster.getId()) return false;
			else return (this.getId().equals(unitItemMaster.getId()));
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