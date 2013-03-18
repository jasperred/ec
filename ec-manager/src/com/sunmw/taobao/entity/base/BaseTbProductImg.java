package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_product_img table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_product_img"
 */

public abstract class BaseTbProductImg  implements Serializable {

	public static String REF = "TbProductImg";
	public static String PROP_CREATED = "Created";
	public static String PROP_URL = "Url";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_TB_PRODUCT_ID = "TbProductId";
	public static String PROP_POSITION = "Position";
	public static String PROP_ID = "Id";
	public static String PROP_TB_PRODUCT_IMG_ID = "TbProductImgId";


	// constructors
	public BaseTbProductImg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbProductImg (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbProductImgId;
	private java.lang.Long tbProductId;
	private java.lang.String url;
	private java.lang.Long position;
	private java.util.Date created;
	private java.util.Date modified;



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
	 * Return the value associated with the column: tb_product_img_id
	 */
	public java.lang.Long getTbProductImgId () {
		return tbProductImgId;
	}

	/**
	 * Set the value related to the column: tb_product_img_id
	 * @param tbProductImgId the tb_product_img_id value
	 */
	public void setTbProductImgId (java.lang.Long tbProductImgId) {
		this.tbProductImgId = tbProductImgId;
	}



	/**
	 * Return the value associated with the column: tb_product_id
	 */
	public java.lang.Long getTbProductId () {
		return tbProductId;
	}

	/**
	 * Set the value related to the column: tb_product_id
	 * @param tbProductId the tb_product_id value
	 */
	public void setTbProductId (java.lang.Long tbProductId) {
		this.tbProductId = tbProductId;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbProductImg)) return false;
		else {
			com.sunmw.taobao.entity.TbProductImg tbProductImg = (com.sunmw.taobao.entity.TbProductImg) obj;
			if (null == this.getId() || null == tbProductImg.getId()) return false;
			else return (this.getId().equals(tbProductImg.getId()));
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