package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the ItemPropImg table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="ItemPropImg"
 */

public abstract class BaseItemPropImg  implements Serializable {

	public static String REF = "ItemPropImg";
	public static String PROP_ITEM_ID = "ItemId";
	public static String PROP_PROP_ID = "PropId";
	public static String PROP_IMG_URL = "ImgUrl";
	public static String PROP_ID = "Id";
	public static String PROP_VALUE_ID = "ValueId";
	public static String PROP_TB_ITEM_PROP_IMG_ID = "TbItemPropImgId";


	// constructors
	public BaseItemPropImg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPropImg (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbItemPropImgId;
	private java.lang.Long itemId;
	private java.lang.Long propId;
	private java.lang.Long valueId;
	private java.lang.String imgUrl;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Item_Prop_Img_ID"
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
	 * Return the value associated with the column: Tb_item_prop_img_id
	 */
	public java.lang.Long getTbItemPropImgId () {
		return tbItemPropImgId;
	}

	/**
	 * Set the value related to the column: Tb_item_prop_img_id
	 * @param tbItemPropImgId the Tb_item_prop_img_id value
	 */
	public void setTbItemPropImgId (java.lang.Long tbItemPropImgId) {
		this.tbItemPropImgId = tbItemPropImgId;
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
	 * Return the value associated with the column: Prop_id
	 */
	public java.lang.Long getPropId () {
		return propId;
	}

	/**
	 * Set the value related to the column: Prop_id
	 * @param propId the Prop_id value
	 */
	public void setPropId (java.lang.Long propId) {
		this.propId = propId;
	}



	/**
	 * Return the value associated with the column: Value_id
	 */
	public java.lang.Long getValueId () {
		return valueId;
	}

	/**
	 * Set the value related to the column: Value_id
	 * @param valueId the Value_id value
	 */
	public void setValueId (java.lang.Long valueId) {
		this.valueId = valueId;
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
		if (!(obj instanceof com.sunmw.taobao.entity.ItemPropImg)) return false;
		else {
			com.sunmw.taobao.entity.ItemPropImg itemPropImg = (com.sunmw.taobao.entity.ItemPropImg) obj;
			if (null == this.getId() || null == itemPropImg.getId()) return false;
			else return (this.getId().equals(itemPropImg.getId()));
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