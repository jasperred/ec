package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_order"
 */

public abstract class BaseTbOrder  implements Serializable {

	public static String REF = "TbOrder";
	public static String PROP_TOTAL_FEE = "TotalFee";
	public static String PROP_TIMEOUT_ACTION_TIME = "TimeoutActionTime";
	public static String PROP_SKU_ID = "SkuId";
	public static String PROP_SELLER_RATE = "SellerRate";
	public static String PROP_OID = "Oid";
	public static String PROP_OUTER_IID = "OuterIid";
	public static String PROP_PAYMENT = "Payment";
	public static String PROP_SNAPSHOT = "Snapshot";
	public static String PROP_NUM_IID = "NumIid";
	public static String PROP_BUYER_NICK = "BuyerNick";
	public static String PROP_TRADE_ID = "TradeId";
	public static String PROP_CREATED = "Created";
	public static String PROP_PIC_PATH = "PicPath";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_BUYER_RATE = "BuyerRate";
	public static String PROP_CID = "Cid";
	public static String PROP_SELLER_TYPE = "SellerType";
	public static String PROP_SKU_PROPERTIES_NAME = "SkuPropertiesName";
	public static String PROP_ADJUST_FEE = "AdjustFee";
	public static String PROP_REFUND_STATUS = "RefundStatus";
	public static String PROP_REFUND_ID = "RefundId";
	public static String PROP_SELLER_NICK = "SellerNick";
	public static String PROP_DISCOUNT_FEE = "DiscountFee";
	public static String PROP_NUM = "Num";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_ITEM_MEAL_NAME = "ItemMealName";
	public static String PROP_TITLE = "Title";
	public static String PROP_STATUS = "Status";
	public static String PROP_SNAPSHOT_URL = "SnapshotUrl";
	public static String PROP_PRICE = "Price";
	public static String PROP_IID = "Iid";
	public static String PROP_ID = "Id";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_IS_OVERSOLD = "IsOversold";
	public static String PROP_OUTER_SKU_ID = "OuterSkuId";


	// constructors
	public BaseTbOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbOrder (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.math.BigDecimal totalFee;
	private java.math.BigDecimal discountFee;
	private java.math.BigDecimal adjustFee;
	private java.lang.String iid;
	private java.lang.String skuId;
	private java.lang.String skuPropertiesName;
	private java.lang.String itemMealName;
	private java.math.BigDecimal num;
	private java.lang.String title;
	private java.math.BigDecimal price;
	private java.lang.String picPath;
	private java.lang.String sellerNick;
	private java.lang.String buyerNick;
	private java.util.Date created;
	private java.lang.String refundStatus;
	private java.math.BigDecimal oid;
	private java.lang.String outerIid;
	private java.lang.String outerSkuId;
	private java.math.BigDecimal payment;
	private java.lang.String status;
	private java.lang.String snapshotUrl;
	private java.lang.String snapshot;
	private java.util.Date timeoutActionTime;
	private boolean buyerRate;
	private boolean sellerRate;
	private java.math.BigDecimal refundId;
	private java.lang.String sellerType;
	private java.util.Date modified;
	private java.math.BigDecimal numIid;
	private java.math.BigDecimal cid;
	private boolean isOversold;
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
	 * Return the value associated with the column: total_fee
	 */
	public java.math.BigDecimal getTotalFee () {
		return totalFee;
	}

	/**
	 * Set the value related to the column: total_fee
	 * @param totalFee the total_fee value
	 */
	public void setTotalFee (java.math.BigDecimal totalFee) {
		this.totalFee = totalFee;
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
	 * Return the value associated with the column: adjust_fee
	 */
	public java.math.BigDecimal getAdjustFee () {
		return adjustFee;
	}

	/**
	 * Set the value related to the column: adjust_fee
	 * @param adjustFee the adjust_fee value
	 */
	public void setAdjustFee (java.math.BigDecimal adjustFee) {
		this.adjustFee = adjustFee;
	}



	/**
	 * Return the value associated with the column: iid
	 */
	public java.lang.String getIid () {
		return iid;
	}

	/**
	 * Set the value related to the column: iid
	 * @param iid the iid value
	 */
	public void setIid (java.lang.String iid) {
		this.iid = iid;
	}



	/**
	 * Return the value associated with the column: sku_id
	 */
	public java.lang.String getSkuId () {
		return skuId;
	}

	/**
	 * Set the value related to the column: sku_id
	 * @param skuId the sku_id value
	 */
	public void setSkuId (java.lang.String skuId) {
		this.skuId = skuId;
	}



	/**
	 * Return the value associated with the column: sku_properties_name
	 */
	public java.lang.String getSkuPropertiesName () {
		return skuPropertiesName;
	}

	/**
	 * Set the value related to the column: sku_properties_name
	 * @param skuPropertiesName the sku_properties_name value
	 */
	public void setSkuPropertiesName (java.lang.String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}



	/**
	 * Return the value associated with the column: item_meal_name
	 */
	public java.lang.String getItemMealName () {
		return itemMealName;
	}

	/**
	 * Set the value related to the column: item_meal_name
	 * @param itemMealName the item_meal_name value
	 */
	public void setItemMealName (java.lang.String itemMealName) {
		this.itemMealName = itemMealName;
	}



	/**
	 * Return the value associated with the column: num
	 */
	public java.math.BigDecimal getNum () {
		return num;
	}

	/**
	 * Set the value related to the column: num
	 * @param num the num value
	 */
	public void setNum (java.math.BigDecimal num) {
		this.num = num;
	}



	/**
	 * Return the value associated with the column: title
	 */
	public java.lang.String getTitle () {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * @param title the title value
	 */
	public void setTitle (java.lang.String title) {
		this.title = title;
	}



	/**
	 * Return the value associated with the column: price
	 */
	public java.math.BigDecimal getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: price
	 * @param price the price value
	 */
	public void setPrice (java.math.BigDecimal price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: pic_path
	 */
	public java.lang.String getPicPath () {
		return picPath;
	}

	/**
	 * Set the value related to the column: pic_path
	 * @param picPath the pic_path value
	 */
	public void setPicPath (java.lang.String picPath) {
		this.picPath = picPath;
	}



	/**
	 * Return the value associated with the column: seller_nick
	 */
	public java.lang.String getSellerNick () {
		return sellerNick;
	}

	/**
	 * Set the value related to the column: seller_nick
	 * @param sellerNick the seller_nick value
	 */
	public void setSellerNick (java.lang.String sellerNick) {
		this.sellerNick = sellerNick;
	}



	/**
	 * Return the value associated with the column: buyer_nick
	 */
	public java.lang.String getBuyerNick () {
		return buyerNick;
	}

	/**
	 * Set the value related to the column: buyer_nick
	 * @param buyerNick the buyer_nick value
	 */
	public void setBuyerNick (java.lang.String buyerNick) {
		this.buyerNick = buyerNick;
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
	 * Return the value associated with the column: refund_status
	 */
	public java.lang.String getRefundStatus () {
		return refundStatus;
	}

	/**
	 * Set the value related to the column: refund_status
	 * @param refundStatus the refund_status value
	 */
	public void setRefundStatus (java.lang.String refundStatus) {
		this.refundStatus = refundStatus;
	}



	/**
	 * Return the value associated with the column: oid
	 */
	public java.math.BigDecimal getOid () {
		return oid;
	}

	/**
	 * Set the value related to the column: oid
	 * @param oid the oid value
	 */
	public void setOid (java.math.BigDecimal oid) {
		this.oid = oid;
	}



	/**
	 * Return the value associated with the column: outer_iid
	 */
	public java.lang.String getOuterIid () {
		return outerIid;
	}

	/**
	 * Set the value related to the column: outer_iid
	 * @param outerIid the outer_iid value
	 */
	public void setOuterIid (java.lang.String outerIid) {
		this.outerIid = outerIid;
	}



	/**
	 * Return the value associated with the column: outer_sku_id
	 */
	public java.lang.String getOuterSkuId () {
		return outerSkuId;
	}

	/**
	 * Set the value related to the column: outer_sku_id
	 * @param outerSkuId the outer_sku_id value
	 */
	public void setOuterSkuId (java.lang.String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}



	/**
	 * Return the value associated with the column: payment
	 */
	public java.math.BigDecimal getPayment () {
		return payment;
	}

	/**
	 * Set the value related to the column: payment
	 * @param payment the payment value
	 */
	public void setPayment (java.math.BigDecimal payment) {
		this.payment = payment;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: snapshot_url
	 */
	public java.lang.String getSnapshotUrl () {
		return snapshotUrl;
	}

	/**
	 * Set the value related to the column: snapshot_url
	 * @param snapshotUrl the snapshot_url value
	 */
	public void setSnapshotUrl (java.lang.String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}



	/**
	 * Return the value associated with the column: snapshot
	 */
	public java.lang.String getSnapshot () {
		return snapshot;
	}

	/**
	 * Set the value related to the column: snapshot
	 * @param snapshot the snapshot value
	 */
	public void setSnapshot (java.lang.String snapshot) {
		this.snapshot = snapshot;
	}



	/**
	 * Return the value associated with the column: timeout_action_time
	 */
	public java.util.Date getTimeoutActionTime () {
		return timeoutActionTime;
	}

	/**
	 * Set the value related to the column: timeout_action_time
	 * @param timeoutActionTime the timeout_action_time value
	 */
	public void setTimeoutActionTime (java.util.Date timeoutActionTime) {
		this.timeoutActionTime = timeoutActionTime;
	}



	/**
	 * Return the value associated with the column: buyer_rate
	 */
	public boolean isBuyerRate () {
		return buyerRate;
	}

	/**
	 * Set the value related to the column: buyer_rate
	 * @param buyerRate the buyer_rate value
	 */
	public void setBuyerRate (boolean buyerRate) {
		this.buyerRate = buyerRate;
	}



	/**
	 * Return the value associated with the column: seller_rate
	 */
	public boolean isSellerRate () {
		return sellerRate;
	}

	/**
	 * Set the value related to the column: seller_rate
	 * @param sellerRate the seller_rate value
	 */
	public void setSellerRate (boolean sellerRate) {
		this.sellerRate = sellerRate;
	}



	/**
	 * Return the value associated with the column: refund_id
	 */
	public java.math.BigDecimal getRefundId () {
		return refundId;
	}

	/**
	 * Set the value related to the column: refund_id
	 * @param refundId the refund_id value
	 */
	public void setRefundId (java.math.BigDecimal refundId) {
		this.refundId = refundId;
	}



	/**
	 * Return the value associated with the column: seller_type
	 */
	public java.lang.String getSellerType () {
		return sellerType;
	}

	/**
	 * Set the value related to the column: seller_type
	 * @param sellerType the seller_type value
	 */
	public void setSellerType (java.lang.String sellerType) {
		this.sellerType = sellerType;
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
	 * Return the value associated with the column: cid
	 */
	public java.math.BigDecimal getCid () {
		return cid;
	}

	/**
	 * Set the value related to the column: cid
	 * @param cid the cid value
	 */
	public void setCid (java.math.BigDecimal cid) {
		this.cid = cid;
	}



	/**
	 * Return the value associated with the column: is_oversold
	 */
	public boolean isIsOversold () {
		return isOversold;
	}

	/**
	 * Set the value related to the column: is_oversold
	 * @param isOversold the is_oversold value
	 */
	public void setIsOversold (boolean isOversold) {
		this.isOversold = isOversold;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbOrder)) return false;
		else {
			com.sunmw.taobao.entity.TbOrder tbOrder = (com.sunmw.taobao.entity.TbOrder) obj;
			if (null == this.getId() || null == tbOrder.getId()) return false;
			else return (this.getId().equals(tbOrder.getId()));
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