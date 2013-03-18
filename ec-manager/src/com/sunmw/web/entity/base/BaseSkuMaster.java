package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the sku_master table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="sku_master"
 */

public abstract class BaseSkuMaster  implements Serializable {

	public static String REF = "SkuMaster";
	public static String PROP_SKU_STATUS = "SkuStatus";
	public static String PROP_SKU_DESC = "SkuDesc";
	public static String PROP_BARCODE2 = "Barcode2";
	public static String PROP_SKU_PRICE2 = "SkuPrice2";
	public static String PROP_BARCODE1 = "Barcode1";
	public static String PROP_SKU_PRICE1 = "SkuPrice1";
	public static String PROP_RECEIVE1 = "Receive1";
	public static String PROP_RECEIVE2 = "Receive2";
	public static String PROP_SKU_TYPE = "SkuType";
	public static String PROP_SKU_CD = "SkuCd";
	public static String PROP_RECEIVE6 = "Receive6";
	public static String PROP_RECEIVE5 = "Receive5";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_RECEIVE4 = "Receive4";
	public static String PROP_RECEIVE3 = "Receive3";
	public static String PROP_ITEM_MASTER = "itemMaster";
	public static String PROP_SKU_PROP1 = "SkuProp1";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_SKU_PROP3 = "SkuProp3";
	public static String PROP_SKU_PROP2 = "SkuProp2";
	public static String PROP_RESERVE_INV = "ReserveInv";
	public static String PROP_SKU_PROP4 = "SkuProp4";
	public static String PROP_SKU_NAME = "SkuName";


	// constructors
	public BaseSkuMaster () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSkuMaster (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String skuType;
	private java.lang.String skuCd;
	private java.lang.String skuName;
	private java.lang.String skuDesc;
	private java.lang.String barcode1;
	private java.lang.String barcode2;
	private java.lang.String skuStatus;
	private java.math.BigDecimal skuPrice1;
	private java.math.BigDecimal skuPrice2;
	private java.lang.String skuProp1;
	private java.lang.String skuProp2;
	private java.lang.String skuProp3;
	private java.lang.String skuProp4;
	private java.lang.String receive1;
	private java.lang.String receive2;
	private java.lang.String receive3;
	private java.lang.String receive4;
	private java.lang.String receive5;
	private java.lang.String receive6;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.Integer reserveInv;

	// many to one
	private com.sunmw.web.entity.ItemMaster itemMaster;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="sku_id"
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
	 * Return the value associated with the column: sku_type
	 */
	public java.lang.String getSkuType () {
		return skuType;
	}

	/**
	 * Set the value related to the column: sku_type
	 * @param skuType the sku_type value
	 */
	public void setSkuType (java.lang.String skuType) {
		this.skuType = skuType;
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
	 * Return the value associated with the column: sku_name
	 */
	public java.lang.String getSkuName () {
		return skuName;
	}

	/**
	 * Set the value related to the column: sku_name
	 * @param skuName the sku_name value
	 */
	public void setSkuName (java.lang.String skuName) {
		this.skuName = skuName;
	}



	/**
	 * Return the value associated with the column: sku_desc
	 */
	public java.lang.String getSkuDesc () {
		return skuDesc;
	}

	/**
	 * Set the value related to the column: sku_desc
	 * @param skuDesc the sku_desc value
	 */
	public void setSkuDesc (java.lang.String skuDesc) {
		this.skuDesc = skuDesc;
	}



	/**
	 * Return the value associated with the column: barcode1
	 */
	public java.lang.String getBarcode1 () {
		return barcode1;
	}

	/**
	 * Set the value related to the column: barcode1
	 * @param barcode1 the barcode1 value
	 */
	public void setBarcode1 (java.lang.String barcode1) {
		this.barcode1 = barcode1;
	}



	/**
	 * Return the value associated with the column: barcode2
	 */
	public java.lang.String getBarcode2 () {
		return barcode2;
	}

	/**
	 * Set the value related to the column: barcode2
	 * @param barcode2 the barcode2 value
	 */
	public void setBarcode2 (java.lang.String barcode2) {
		this.barcode2 = barcode2;
	}



	/**
	 * Return the value associated with the column: sku_status
	 */
	public java.lang.String getSkuStatus () {
		return skuStatus;
	}

	/**
	 * Set the value related to the column: sku_status
	 * @param skuStatus the sku_status value
	 */
	public void setSkuStatus (java.lang.String skuStatus) {
		this.skuStatus = skuStatus;
	}



	/**
	 * Return the value associated with the column: sku_price1
	 */
	public java.math.BigDecimal getSkuPrice1 () {
		return skuPrice1;
	}

	/**
	 * Set the value related to the column: sku_price1
	 * @param skuPrice1 the sku_price1 value
	 */
	public void setSkuPrice1 (java.math.BigDecimal skuPrice1) {
		this.skuPrice1 = skuPrice1;
	}



	/**
	 * Return the value associated with the column: sku_price2
	 */
	public java.math.BigDecimal getSkuPrice2 () {
		return skuPrice2;
	}

	/**
	 * Set the value related to the column: sku_price2
	 * @param skuPrice2 the sku_price2 value
	 */
	public void setSkuPrice2 (java.math.BigDecimal skuPrice2) {
		this.skuPrice2 = skuPrice2;
	}



	/**
	 * Return the value associated with the column: sku_prop1
	 */
	public java.lang.String getSkuProp1 () {
		return skuProp1;
	}

	/**
	 * Set the value related to the column: sku_prop1
	 * @param skuProp1 the sku_prop1 value
	 */
	public void setSkuProp1 (java.lang.String skuProp1) {
		this.skuProp1 = skuProp1;
	}



	/**
	 * Return the value associated with the column: sku_prop2
	 */
	public java.lang.String getSkuProp2 () {
		return skuProp2;
	}

	/**
	 * Set the value related to the column: sku_prop2
	 * @param skuProp2 the sku_prop2 value
	 */
	public void setSkuProp2 (java.lang.String skuProp2) {
		this.skuProp2 = skuProp2;
	}



	/**
	 * Return the value associated with the column: sku_prop3
	 */
	public java.lang.String getSkuProp3 () {
		return skuProp3;
	}

	/**
	 * Set the value related to the column: sku_prop3
	 * @param skuProp3 the sku_prop3 value
	 */
	public void setSkuProp3 (java.lang.String skuProp3) {
		this.skuProp3 = skuProp3;
	}



	/**
	 * Return the value associated with the column: sku_prop4
	 */
	public java.lang.String getSkuProp4 () {
		return skuProp4;
	}

	/**
	 * Set the value related to the column: sku_prop4
	 * @param skuProp4 the sku_prop4 value
	 */
	public void setSkuProp4 (java.lang.String skuProp4) {
		this.skuProp4 = skuProp4;
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
	 * Return the value associated with the column: item_id
	 */
	public com.sunmw.web.entity.ItemMaster getItemMaster () {
		return itemMaster;
	}

	/**
	 * Set the value related to the column: item_id
	 * @param itemMaster the item_id value
	 */
	public void setItemMaster (com.sunmw.web.entity.ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.SkuMaster)) return false;
		else {
			com.sunmw.web.entity.SkuMaster skuMaster = (com.sunmw.web.entity.SkuMaster) obj;
			if (null == this.getId() || null == skuMaster.getId()) return false;
			else return (this.getId().equals(skuMaster.getId()));
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