package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_postage table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_postage"
 */

public abstract class BaseTbPostage  implements Serializable {

	public static String REF = "TbPostage";
	public static String PROP_NAME = "Name";
	public static String PROP_CREATED = "Created";
	public static String PROP_EXPRESS_PRICE = "ExpressPrice";
	public static String PROP_EMS_PRICE = "EmsPrice";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_EXPRESS_INCREASE = "ExpressIncrease";
	public static String PROP_MEMO = "Memo";
	public static String PROP_EMS_INCREASE = "EmsIncrease";
	public static String PROP_ID = "Id";
	public static String PROP_POSTAGE_ID = "PostageId";
	public static String PROP_POST_INCREASE = "PostIncrease";
	public static String PROP_POST_PRICE = "PostPrice";


	// constructors
	public BaseTbPostage () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbPostage (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long postageId;
	private java.lang.String name;
	private java.lang.String memo;
	private java.util.Date created;
	private java.util.Date modified;
	private java.math.BigDecimal postPrice;
	private java.math.BigDecimal postIncrease;
	private java.math.BigDecimal expressPrice;
	private java.math.BigDecimal expressIncrease;
	private java.math.BigDecimal emsPrice;
	private java.math.BigDecimal emsIncrease;



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
	 * Return the value associated with the column: postage_id
	 */
	public java.lang.Long getPostageId () {
		return postageId;
	}

	/**
	 * Set the value related to the column: postage_id
	 * @param postageId the postage_id value
	 */
	public void setPostageId (java.lang.Long postageId) {
		this.postageId = postageId;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: memo
	 */
	public java.lang.String getMemo () {
		return memo;
	}

	/**
	 * Set the value related to the column: memo
	 * @param memo the memo value
	 */
	public void setMemo (java.lang.String memo) {
		this.memo = memo;
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
	 * Return the value associated with the column: post_price
	 */
	public java.math.BigDecimal getPostPrice () {
		return postPrice;
	}

	/**
	 * Set the value related to the column: post_price
	 * @param postPrice the post_price value
	 */
	public void setPostPrice (java.math.BigDecimal postPrice) {
		this.postPrice = postPrice;
	}



	/**
	 * Return the value associated with the column: post_increase
	 */
	public java.math.BigDecimal getPostIncrease () {
		return postIncrease;
	}

	/**
	 * Set the value related to the column: post_increase
	 * @param postIncrease the post_increase value
	 */
	public void setPostIncrease (java.math.BigDecimal postIncrease) {
		this.postIncrease = postIncrease;
	}



	/**
	 * Return the value associated with the column: express_price
	 */
	public java.math.BigDecimal getExpressPrice () {
		return expressPrice;
	}

	/**
	 * Set the value related to the column: express_price
	 * @param expressPrice the express_price value
	 */
	public void setExpressPrice (java.math.BigDecimal expressPrice) {
		this.expressPrice = expressPrice;
	}



	/**
	 * Return the value associated with the column: express_increase
	 */
	public java.math.BigDecimal getExpressIncrease () {
		return expressIncrease;
	}

	/**
	 * Set the value related to the column: express_increase
	 * @param expressIncrease the express_increase value
	 */
	public void setExpressIncrease (java.math.BigDecimal expressIncrease) {
		this.expressIncrease = expressIncrease;
	}



	/**
	 * Return the value associated with the column: ems_price
	 */
	public java.math.BigDecimal getEmsPrice () {
		return emsPrice;
	}

	/**
	 * Set the value related to the column: ems_price
	 * @param emsPrice the ems_price value
	 */
	public void setEmsPrice (java.math.BigDecimal emsPrice) {
		this.emsPrice = emsPrice;
	}



	/**
	 * Return the value associated with the column: ems_increase
	 */
	public java.math.BigDecimal getEmsIncrease () {
		return emsIncrease;
	}

	/**
	 * Set the value related to the column: ems_increase
	 * @param emsIncrease the ems_increase value
	 */
	public void setEmsIncrease (java.math.BigDecimal emsIncrease) {
		this.emsIncrease = emsIncrease;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbPostage)) return false;
		else {
			com.sunmw.taobao.entity.TbPostage tbPostage = (com.sunmw.taobao.entity.TbPostage) obj;
			if (null == this.getId() || null == tbPostage.getId()) return false;
			else return (this.getId().equals(tbPostage.getId()));
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