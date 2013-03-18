package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the Sku table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="Sku"
 */

public abstract class BaseSku  implements Serializable {

	public static String REF = "Sku";
	public static String PROP_ITEM_ID = "ItemId";
	public static String PROP_OUTER_ID = "OuterId";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_SIZE_NAME = "SizeName";
	public static String PROP_COLOR_NAME = "ColorName";
	public static String PROP_PUBLISH_TIME = "PublishTime";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_PRICE_UPDATE_TIME = "PriceUpdateTime";
	public static String PROP_PROPERTIES = "Properties";
	public static String PROP_PRICE = "Price";
	public static String PROP_CREATE_TIME = "CreateTime";
	public static String PROP_TB_SKU_ID = "TbSkuId";
	public static String PROP_ID = "Id";
	public static String PROP_INV_UPDATE_TIME = "InvUpdateTime";


	// constructors
	public BaseSku () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSku (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbSkuId;
	private java.lang.Long itemId;
	private java.lang.String properties;
	private java.lang.String sizeName;
	private java.lang.String colorName;
	private java.lang.Integer quantity;
	private java.math.BigDecimal price;
	private java.lang.String outerId;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private java.util.Date publishTime;
	private java.util.Date invUpdateTime;
	private java.util.Date priceUpdateTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Sku_id"
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
	 * Return the value associated with the column: Tb_sku_id
	 */
	public java.lang.Long getTbSkuId () {
		return tbSkuId;
	}

	/**
	 * Set the value related to the column: Tb_sku_id
	 * @param tbSkuId the Tb_sku_id value
	 */
	public void setTbSkuId (java.lang.Long tbSkuId) {
		this.tbSkuId = tbSkuId;
	}



	/**
	 * Return the value associated with the column: Item_id
	 */
	public java.lang.Long getItemId () {
		return itemId;
	}

	/**
	 * Set the value related to the column: Item_id
	 * @param itemId the Item_id value
	 */
	public void setItemId (java.lang.Long itemId) {
		this.itemId = itemId;
	}



	/**
	 * Return the value associated with the column: properties
	 */
	public java.lang.String getProperties () {
		return properties;
	}

	/**
	 * Set the value related to the column: properties
	 * @param properties the properties value
	 */
	public void setProperties (java.lang.String properties) {
		this.properties = properties;
	}



	/**
	 * Return the value associated with the column: size_name
	 */
	public java.lang.String getSizeName () {
		return sizeName;
	}

	/**
	 * Set the value related to the column: size_name
	 * @param sizeName the size_name value
	 */
	public void setSizeName (java.lang.String sizeName) {
		this.sizeName = sizeName;
	}



	/**
	 * Return the value associated with the column: color_name
	 */
	public java.lang.String getColorName () {
		return colorName;
	}

	/**
	 * Set the value related to the column: color_name
	 * @param colorName the color_name value
	 */
	public void setColorName (java.lang.String colorName) {
		this.colorName = colorName;
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
	 * Return the value associated with the column: price
	 */
	public java.math.BigDecimal getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: price
	 * @param price the price value
	 */
	public void setPrice (java.math.BigDecimal price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: outer_id
	 */
	public java.lang.String getOuterId () {
		return outerId;
	}

	/**
	 * Set the value related to the column: outer_id
	 * @param outerId the outer_id value
	 */
	public void setOuterId (java.lang.String outerId) {
		this.outerId = outerId;
	}



	/**
	 * Return the value associated with the column: Update_time
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}

	/**
	 * Set the value related to the column: Update_time
	 * @param updateTime the Update_time value
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}



	/**
	 * Return the value associated with the column: Create_time
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: Create_time
	 * @param createTime the Create_time value
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}



	/**
	 * Return the value associated with the column: Publish_time
	 */
	public java.util.Date getPublishTime () {
		return publishTime;
	}

	/**
	 * Set the value related to the column: Publish_time
	 * @param publishTime the Publish_time value
	 */
	public void setPublishTime (java.util.Date publishTime) {
		this.publishTime = publishTime;
	}



	/**
	 * Return the value associated with the column: Inv_Update_Time
	 */
	public java.util.Date getInvUpdateTime () {
		return invUpdateTime;
	}

	/**
	 * Set the value related to the column: Inv_Update_Time
	 * @param invUpdateTime the Inv_Update_Time value
	 */
	public void setInvUpdateTime (java.util.Date invUpdateTime) {
		this.invUpdateTime = invUpdateTime;
	}



	/**
	 * Return the value associated with the column: Price_Update_Time
	 */
	public java.util.Date getPriceUpdateTime () {
		return priceUpdateTime;
	}

	/**
	 * Set the value related to the column: Price_Update_Time
	 * @param priceUpdateTime the Price_Update_Time value
	 */
	public void setPriceUpdateTime (java.util.Date priceUpdateTime) {
		this.priceUpdateTime = priceUpdateTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.Sku)) return false;
		else {
			com.sunmw.taobao.entity.Sku sku = (com.sunmw.taobao.entity.Sku) obj;
			if (null == this.getId() || null == sku.getId()) return false;
			else return (this.getId().equals(sku.getId()));
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