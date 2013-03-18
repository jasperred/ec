package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_item_prop_img table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_item_prop_img"
 */

public abstract class BaseTbItemPropImg  implements Serializable {

	public static String REF = "TbItemPropImg";
	public static String PROP_CREATED = "Created";
	public static String PROP_URL = "Url";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_POSITION = "Position";
	public static String PROP_ID = "Id";
	public static String PROP_TB_ITEM_PROP_IMG_ID = "TbItemPropImgId";
	public static String PROP_PROPERTIES = "Properties";
	public static String PROP_NUM_IID = "NumIid";


	// constructors
	public BaseTbItemPropImg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbItemPropImg (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.math.BigDecimal numIid;
	private java.lang.Long tbItemPropImgId;
	private java.lang.String url;
	private java.lang.String properties;
	private java.lang.Long position;
	private java.util.Date created;
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
	 * Return the value associated with the column: tb_item_prop_img_id
	 */
	public java.lang.Long getTbItemPropImgId () {
		return tbItemPropImgId;
	}

	/**
	 * Set the value related to the column: tb_item_prop_img_id
	 * @param tbItemPropImgId the tb_item_prop_img_id value
	 */
	public void setTbItemPropImgId (java.lang.Long tbItemPropImgId) {
		this.tbItemPropImgId = tbItemPropImgId;
	}



	/**
	 * Return the value associated with the column: url
	 */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
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
	 * Return the value associated with the column: position
	 */
	public java.lang.Long getPosition () {
		return position;
	}

	/**
	 * Set the value related to the column: position
	 * @param position the position value
	 */
	public void setPosition (java.lang.Long position) {
		this.position = position;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbItemPropImg)) return false;
		else {
			com.sunmw.taobao.entity.TbItemPropImg tbItemPropImg = (com.sunmw.taobao.entity.TbItemPropImg) obj;
			if (null == this.getId() || null == tbItemPropImg.getId()) return false;
			else return (this.getId().equals(tbItemPropImg.getId()));
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