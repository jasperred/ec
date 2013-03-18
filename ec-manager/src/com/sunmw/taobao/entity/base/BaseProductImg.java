package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the ProductImg table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="ProductImg"
 */

public abstract class BaseProductImg  implements Serializable {

	public static String REF = "ProductImg";
	public static String PROP_IMG_URL = "ImgUrl";
	public static String PROP_IMG_INDEX = "ImgIndex";
	public static String PROP_ID = "Id";
	public static String PROP_TB_PRODUCT_IMG_ID = "TbProductImgId";
	public static String PROP_PRODUCT_ID = "ProductId";


	// constructors
	public BaseProductImg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseProductImg (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer productId;
	private java.lang.Integer imgIndex;
	private java.lang.String imgUrl;
	private java.lang.Long tbProductImgId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Product_Img_ID"
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
	 * Return the value associated with the column: Product_id
	 */
	public java.lang.Integer getProductId () {
		return productId;
	}

	/**
	 * Set the value related to the column: Product_id
	 * @param productId the Product_id value
	 */
	public void setProductId (java.lang.Integer productId) {
		this.productId = productId;
	}



	/**
	 * Return the value associated with the column: Img_Index
	 */
	public java.lang.Integer getImgIndex () {
		return imgIndex;
	}

	/**
	 * Set the value related to the column: Img_Index
	 * @param imgIndex the Img_Index value
	 */
	public void setImgIndex (java.lang.Integer imgIndex) {
		this.imgIndex = imgIndex;
	}



	/**
	 * Return the value associated with the column: Img_URL
	 */
	public java.lang.String getImgUrl () {
		return imgUrl;
	}

	/**
	 * Set the value related to the column: Img_URL
	 * @param imgUrl the Img_URL value
	 */
	public void setImgUrl (java.lang.String imgUrl) {
		this.imgUrl = imgUrl;
	}



	/**
	 * Return the value associated with the column: Tb_product_img_id
	 */
	public java.lang.Long getTbProductImgId () {
		return tbProductImgId;
	}

	/**
	 * Set the value related to the column: Tb_product_img_id
	 * @param tbProductImgId the Tb_product_img_id value
	 */
	public void setTbProductImgId (java.lang.Long tbProductImgId) {
		this.tbProductImgId = tbProductImgId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.ProductImg)) return false;
		else {
			com.sunmw.taobao.entity.ProductImg productImg = (com.sunmw.taobao.entity.ProductImg) obj;
			if (null == this.getId() || null == productImg.getId()) return false;
			else return (this.getId().equals(productImg.getId()));
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