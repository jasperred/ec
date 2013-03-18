package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_sku table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_sku"
 */

public abstract class BaseTbSku  implements Serializable {

	public static String REF = "TbSku";
	public static String PROP_STATUS = "Status";
	public static String PROP_OUTER_ID = "OuterId";
	public static String PROP_CREATED = "Created";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_SKU_ID = "SkuId";
	public static String PROP_PRICE = "Price";
	public static String PROP_ID = "Id";
	public static String PROP_PROPERTIES = "Properties";
	public static String PROP_NUM_IID = "NumIid";


	// constructors
	public BaseTbSku () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbSku (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.math.BigDecimal skuId;
	private java.math.BigDecimal numIid;
	private java.lang.String properties;
	private java.lang.Long quantity;
	private java.math.BigDecimal price;
	private java.lang.String outerId;
	private java.util.Date created;
	private java.util.Date modified;
	private java.lang.String status;
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
	 * Return the value associated with the column: sku_id
	 */
	public java.math.BigDecimal getSkuId () {
		return skuId;
	}

	/**
	 * Set the value related to the column: sku_id
	 * @param skuId the sku_id value
	 */
	public void setSkuId (java.math.BigDecimal skuId) {
		this.skuId = skuId;
	}



	/**
	 * Return the value associated with the column: num_iid
	 */
	public java.math.BigDecimal getNumIid () {
		return numIid;
	}

	/**
	 * Set the value related to the column: num_iid
	 * @param numIid the num_iid value
	 */
	public void setNumIid (java.math.BigDecimal numIid) {
		this.numIid = numIid;
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
	 * Return the value associated with the column: quantity
	 */
	public java.lang.Long getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: quantity
	 * @param quantity the quantity value
	 */
	public void setQuantity (java.lang.Long quantity) {
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
	 * Return the value associated with the column: modified
	 */
	public java.util.Date getModified () {
		return modified;
	}

	/**
	 * Set the value related to the column: modified
	 * @param modified the modified value
	 */
	public void setModified (java.util.Date modified) {
		this.modified = modified;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbSku)) return false;
		else {
			com.sunmw.taobao.entity.TbSku tbSku = (com.sunmw.taobao.entity.TbSku) obj;
			if (null == this.getId() || null == tbSku.getId()) return false;
			else return (this.getId().equals(tbSku.getId()));
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