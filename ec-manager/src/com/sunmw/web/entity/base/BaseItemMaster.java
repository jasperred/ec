package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_master table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_master"
 */

public abstract class BaseItemMaster  implements Serializable {

	public static String REF = "ItemMaster";
	public static String PROP_COMPANY_ID = "CompanyId";
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
	public static String PROP_ITEM_TYPE = "ItemType";
	public static String PROP_ITEM_NAME = "ItemName";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_ITEM_PRICE = "ItemPrice";
	public static String PROP_RESERVE_INV = "ReserveInv";


	// constructors
	public BaseItemMaster () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemMaster (java.lang.Integer id) {
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
	private java.lang.Integer companyId;
	private java.lang.Integer reserveInv;

	// collections
	private java.util.Set<com.sunmw.web.entity.SkuMaster> skuMaster;



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
	 * Return the value associated with the column: reserve_inv
	 */
	public java.lang.Integer getReserveInv () {
		return reserveInv;
	}

	/**
	 * Set the value related to the column: reserve_inv
	 * @param reserveInv the reserve_inv value
	 */
	public void setReserveInv (java.lang.Integer reserveInv) {
		this.reserveInv = reserveInv;
	}



	/**
	 * Return the value associated with the column: skuMaster
	 */
	public java.util.Set<com.sunmw.web.entity.SkuMaster> getSkuMaster () {
		return skuMaster;
	}

	/**
	 * Set the value related to the column: skuMaster
	 * @param skuMaster the skuMaster value
	 */
	public void setSkuMaster (java.util.Set<com.sunmw.web.entity.SkuMaster> skuMaster) {
		this.skuMaster = skuMaster;
	}

	public void addToskuMaster (com.sunmw.web.entity.SkuMaster skuMaster) {
		if (null == getSkuMaster()) setSkuMaster(new java.util.TreeSet<com.sunmw.web.entity.SkuMaster>());
		getSkuMaster().add(skuMaster);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.ItemMaster)) return false;
		else {
			com.sunmw.web.entity.ItemMaster itemMaster = (com.sunmw.web.entity.ItemMaster) obj;
			if (null == this.getId() || null == itemMaster.getId()) return false;
			else return (this.getId().equals(itemMaster.getId()));
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