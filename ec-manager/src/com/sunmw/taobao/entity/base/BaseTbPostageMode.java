package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_postage_mode table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_postage_mode"
 */

public abstract class BaseTbPostageMode  implements Serializable {

	public static String REF = "TbPostageMode";
	public static String PROP_DESTS = "Dests";
	public static String PROP_TYPE = "Type";
	public static String PROP_INCREASE = "Increase";
	public static String PROP_PRICE = "Price";
	public static String PROP_ID = "Id";
	public static String PROP_POSTAGE_ID = "PostageId";
	public static String PROP_POST_ID = "PostId";


	// constructors
	public BaseTbPostageMode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbPostageMode (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long postageId;
	private java.lang.Long postId;
	private java.lang.String type;
	private java.lang.String dests;
	private java.lang.String price;
	private java.lang.String increase;



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
	 * Return the value associated with the column: post_id
	 */
	public java.lang.Long getPostId () {
		return postId;
	}

	/**
	 * Set the value related to the column: post_id
	 * @param postId the post_id value
	 */
	public void setPostId (java.lang.Long postId) {
		this.postId = postId;
	}



	/**
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: dests
	 */
	public java.lang.String getDests () {
		return dests;
	}

	/**
	 * Set the value related to the column: dests
	 * @param dests the dests value
	 */
	public void setDests (java.lang.String dests) {
		this.dests = dests;
	}



	/**
	 * Return the value associated with the column: price
	 */
	public java.lang.String getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: price
	 * @param price the price value
	 */
	public void setPrice (java.lang.String price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: increase
	 */
	public java.lang.String getIncrease () {
		return increase;
	}

	/**
	 * Set the value related to the column: increase
	 * @param increase the increase value
	 */
	public void setIncrease (java.lang.String increase) {
		this.increase = increase;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbPostageMode)) return false;
		else {
			com.sunmw.taobao.entity.TbPostageMode tbPostageMode = (com.sunmw.taobao.entity.TbPostageMode) obj;
			if (null == this.getId() || null == tbPostageMode.getId()) return false;
			else return (this.getId().equals(tbPostageMode.getId()));
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