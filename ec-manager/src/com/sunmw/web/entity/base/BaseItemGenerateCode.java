package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_generate_code table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_generate_code"
 */

public abstract class BaseItemGenerateCode  implements Serializable {

	public static String REF = "ItemGenerateCode";
	public static String PROP_BRAND = "Brand";
	public static String PROP_ITEM_CODE = "ItemCode";
	public static String PROP_SEL = "Sel";
	public static String PROP_DEPT = "Dept";
	public static String PROP_SEASON = "Season";
	public static String PROP_IMAGE_URL = "ImageUrl";
	public static String PROP_YEAR = "Year";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_DETAIL = "Detail";
	public static String PROP_SERIAL = "Serial";
	public static String PROP_CAT = "Cat";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_SUB_CAT = "SubCat";
	public static String PROP_ITEM_NAME = "ItemName";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_SEX = "Sex";


	// constructors
	public BaseItemGenerateCode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemGenerateCode (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String brand;
	private java.lang.String cat;
	private java.lang.String subCat;
	private java.lang.String year;
	private java.lang.String season;
	private java.lang.String sex;
	private java.lang.String dept;
	private java.lang.String detail;
	private java.lang.Integer serial;
	private java.lang.String itemCode;
	private java.lang.String itemName;
	private java.lang.String sel;
	private java.lang.String imageUrl;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="id"
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
	 * Return the value associated with the column: brand
	 */
	public java.lang.String getBrand () {
		return brand;
	}

	/**
	 * Set the value related to the column: brand
	 * @param brand the brand value
	 */
	public void setBrand (java.lang.String brand) {
		this.brand = brand;
	}



	/**
	 * Return the value associated with the column: cat
	 */
	public java.lang.String getCat () {
		return cat;
	}

	/**
	 * Set the value related to the column: cat
	 * @param cat the cat value
	 */
	public void setCat (java.lang.String cat) {
		this.cat = cat;
	}



	/**
	 * Return the value associated with the column: sub_cat
	 */
	public java.lang.String getSubCat () {
		return subCat;
	}

	/**
	 * Set the value related to the column: sub_cat
	 * @param subCat the sub_cat value
	 */
	public void setSubCat (java.lang.String subCat) {
		this.subCat = subCat;
	}



	/**
	 * Return the value associated with the column: year
	 */
	public java.lang.String getYear () {
		return year;
	}

	/**
	 * Set the value related to the column: year
	 * @param year the year value
	 */
	public void setYear (java.lang.String year) {
		this.year = year;
	}



	/**
	 * Return the value associated with the column: season
	 */
	public java.lang.String getSeason () {
		return season;
	}

	/**
	 * Set the value related to the column: season
	 * @param season the season value
	 */
	public void setSeason (java.lang.String season) {
		this.season = season;
	}



	/**
	 * Return the value associated with the column: sex
	 */
	public java.lang.String getSex () {
		return sex;
	}

	/**
	 * Set the value related to the column: sex
	 * @param sex the sex value
	 */
	public void setSex (java.lang.String sex) {
		this.sex = sex;
	}



	/**
	 * Return the value associated with the column: dept
	 */
	public java.lang.String getDept () {
		return dept;
	}

	/**
	 * Set the value related to the column: dept
	 * @param dept the dept value
	 */
	public void setDept (java.lang.String dept) {
		this.dept = dept;
	}



	/**
	 * Return the value associated with the column: detail
	 */
	public java.lang.String getDetail () {
		return detail;
	}

	/**
	 * Set the value related to the column: detail
	 * @param detail the detail value
	 */
	public void setDetail (java.lang.String detail) {
		this.detail = detail;
	}



	/**
	 * Return the value associated with the column: serial
	 */
	public java.lang.Integer getSerial () {
		return serial;
	}

	/**
	 * Set the value related to the column: serial
	 * @param serial the serial value
	 */
	public void setSerial (java.lang.Integer serial) {
		this.serial = serial;
	}



	/**
	 * Return the value associated with the column: item_code
	 */
	public java.lang.String getItemCode () {
		return itemCode;
	}

	/**
	 * Set the value related to the column: item_code
	 * @param itemCode the item_code value
	 */
	public void setItemCode (java.lang.String itemCode) {
		this.itemCode = itemCode;
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
	 * Return the value associated with the column: sel
	 */
	public java.lang.String getSel () {
		return sel;
	}

	/**
	 * Set the value related to the column: sel
	 * @param sel the sel value
	 */
	public void setSel (java.lang.String sel) {
		this.sel = sel;
	}



	/**
	 * Return the value associated with the column: image_url
	 */
	public java.lang.String getImageUrl () {
		return imageUrl;
	}

	/**
	 * Set the value related to the column: image_url
	 * @param imageUrl the image_url value
	 */
	public void setImageUrl (java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
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
	 * Return the value associated with the column: cuser
	 */
	public java.lang.String getCuser () {
		return cuser;
	}

	/**
	 * Set the value related to the column: cuser
	 * @param cuser the cuser value
	 */
	public void setCuser (java.lang.String cuser) {
		this.cuser = cuser;
	}



	/**
	 * Return the value associated with the column: muser
	 */
	public java.lang.String getMuser () {
		return muser;
	}

	/**
	 * Set the value related to the column: muser
	 * @param muser the muser value
	 */
	public void setMuser (java.lang.String muser) {
		this.muser = muser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.ItemGenerateCode)) return false;
		else {
			com.sunmw.web.entity.ItemGenerateCode itemGenerateCode = (com.sunmw.web.entity.ItemGenerateCode) obj;
			if (null == this.getId() || null == itemGenerateCode.getId()) return false;
			else return (this.getId().equals(itemGenerateCode.getId()));
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