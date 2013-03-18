package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_promotion_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_promotion_detail"
 */

public abstract class BaseTbPromotionDetail  implements Serializable {

	public static String REF = "TbPromotionDetail";
	public static String PROP_TB_PROMOTION_DETAIL_ID = "TbPromotionDetailId";
	public static String PROP_TRADE_ID = "TradeId";
	public static String PROP_DISCOUNT_FEE = "DiscountFee";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_ID = "Id";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_GIFT_ITEM_NAME = "GiftItemName";
	public static String PROP_PROMOTION_NAME = "PromotionName";


	// constructors
	public BaseTbPromotionDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbPromotionDetail (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbPromotionDetailId;
	private java.lang.String promotionName;
	private java.math.BigDecimal discountFee;
	private java.lang.String giftItemName;
	private java.math.BigDecimal tradeId;
	private java.util.Date updateTime;
	private java.lang.String updateFlag;
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
	 * Return the value associated with the column: tb_promotion_detail_id
	 */
	public java.lang.Long getTbPromotionDetailId () {
		return tbPromotionDetailId;
	}

	/**
	 * Set the value related to the column: tb_promotion_detail_id
	 * @param tbPromotionDetailId the tb_promotion_detail_id value
	 */
	public void setTbPromotionDetailId (java.lang.Long tbPromotionDetailId) {
		this.tbPromotionDetailId = tbPromotionDetailId;
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



	/**
	 * Return the value associated with the column: trade_id
	 */
	public java.math.BigDecimal getTradeId () {
		return tradeId;
	}

	/**
	 * Set the value related to the column: trade_id
	 * @param tradeId the trade_id value
	 */
	public void setTradeId (java.math.BigDecimal tradeId) {
		this.tradeId = tradeId;
	}



	/**
	 * Return the value associated with the column: update_time
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}

	/**
	 * Set the value related to the column: update_time
	 * @param updateTime the update_time value
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}



	/**
	 * Return the value associated with the column: update_flag
	 */
	public java.lang.String getUpdateFlag () {
		return updateFlag;
	}

	/**
	 * Set the value related to the column: update_flag
	 * @param updateFlag the update_flag value
	 */
	public void setUpdateFlag (java.lang.String updateFlag) {
		this.updateFlag = updateFlag;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbPromotionDetail)) return false;
		else {
			com.sunmw.taobao.entity.TbPromotionDetail tbPromotionDetail = (com.sunmw.taobao.entity.TbPromotionDetail) obj;
			if (null == this.getId() || null == tbPromotionDetail.getId()) return false;
			else return (this.getId().equals(tbPromotionDetail.getId()));
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