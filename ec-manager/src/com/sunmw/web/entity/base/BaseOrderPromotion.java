package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the order_promotion table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="order_promotion"
 */

public abstract class BaseOrderPromotion  implements Serializable {

	public static String REF = "OrderPromotion";
	public static String PROP_DISCOUNT_FEE = "DiscountFee";
	public static String PROP_TYPE = "Type";
	public static String PROP_ORIG_ID = "OrigId";
	public static String PROP_ID = "Id";
	public static String PROP_GIFT_ITEM_NAME = "GiftItemName";
	public static String PROP_PROMOTION_NAME = "PromotionName";


	// constructors
	public BaseOrderPromotion () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrderPromotion (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String type;
	private java.lang.String origId;
	private java.lang.String promotionName;
	private java.math.BigDecimal discountFee;
	private java.lang.String giftItemName;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: orig_id
	 */
	public java.lang.String getOrigId () {
		return origId;
	}

	/**
	 * Set the value related to the column: orig_id
	 * @param origId the orig_id value
	 */
	public void setOrigId (java.lang.String origId) {
		this.origId = origId;
	}



	/**
	 * Return the value associated with the column: promotion_name
	 */
	public java.lang.String getPromotionName () {
		return promotionName;
	}

	/**
	 * Set the value related to the column: promotion_name
	 * @param promotionName the promotion_name value
	 */
	public void setPromotionName (java.lang.String promotionName) {
		this.promotionName = promotionName;
	}



	/**
	 * Return the value associated with the column: discount_fee
	 */
	public java.math.BigDecimal getDiscountFee () {
		return discountFee;
	}

	/**
	 * Set the value related to the column: discount_fee
	 * @param discountFee the discount_fee value
	 */
	public void setDiscountFee (java.math.BigDecimal discountFee) {
		this.discountFee = discountFee;
	}



	/**
	 * Return the value associated with the column: gift_item_name
	 */
	public java.lang.String getGiftItemName () {
		return giftItemName;
	}

	/**
	 * Set the value related to the column: gift_item_name
	 * @param giftItemName the gift_item_name value
	 */
	public void setGiftItemName (java.lang.String giftItemName) {
		this.giftItemName = giftItemName;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.OrderPromotion)) return false;
		else {
			com.sunmw.web.entity.OrderPromotion orderPromotion = (com.sunmw.web.entity.OrderPromotion) obj;
			if (null == this.getId() || null == orderPromotion.getId()) return false;
			else return (this.getId().equals(orderPromotion.getId()));
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