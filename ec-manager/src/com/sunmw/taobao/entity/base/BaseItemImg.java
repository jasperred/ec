package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the ItemImg table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="ItemImg"
 */

public abstract class BaseItemImg  implements Serializable {

	public static String REF = "ItemImg";
	public static String PROP_ITEM_ID = "ItemId";
	public static String PROP_IMG_URL = "ImgUrl";
	public static String PROP_IMG_INDEX = "ImgIndex";
	public static String PROP_ID = "Id";
	public static String PROP_TB_ITEM_IMG_ID = "TbItemImgId";


	// constructors
	public BaseItemImg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemImg (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbItemImgId;
	private java.lang.Long itemId;
	private java.lang.Integer imgIndex;
	private java.lang.String imgUrl;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Item_Img_ID"
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
	 * Return the value associated with the column: Tb_item_img_id
	 */
	public java.lang.Long getTbItemImgId () {
		return tbItemImgId;
	}

	/**
	 * Set the value related to the column: Tb_item_img_id
	 * @param tbItemImgId the Tb_item_img_id value
	 */
	public void setTbItemImgId (java.lang.Long tbItemImgId) {
		this.tbItemImgId = tbItemImgId;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.ItemImg)) return false;
		else {
			com.sunmw.taobao.entity.ItemImg itemImg = (com.sunmw.taobao.entity.ItemImg) obj;
			if (null == this.getId() || null == itemImg.getId()) return false;
			else return (this.getId().equals(itemImg.getId()));
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