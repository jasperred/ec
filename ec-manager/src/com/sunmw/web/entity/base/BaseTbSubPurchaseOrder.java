package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_sub_purchase_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_sub_purchase_order"
 */

public abstract class BaseTbSubPurchaseOrder  implements Serializable {

	public static String REF = "TbSubPurchaseOrder";
	public static String PROP_TOTAL_FEE = "TotalFee";
	public static String PROP_SKU_ID = "SkuId";
	public static String PROP_ITEM_OUTER_ID = "ItemOuterId";
	public static String PROP_SKU_OUTER_ID = "SkuOuterId";
	public static String PROP_CREATED = "Created";
	public static String PROP_ORDER200_STATUS = "Order200Status";
	public static String PROP_TC_ORDER_ID = "TcOrderId";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_FENXIAO_ID = "FenxiaoId";
	public static String PROP_ITEM_ID = "ItemId";
	public static String PROP_NUM = "Num";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_OLD_SKU_PROPERTIES = "OldSkuProperties";
	public static String PROP_TITLE = "Title";
	public static String PROP_DISTRIBUTOR_PAYMENT = "DistributorPayment";
	public static String PROP_REFUND_FEE = "RefundFee";
	public static String PROP_STATUS = "Status";
	public static String PROP_SNAPSHOT_URL = "SnapshotUrl";
	public static String PROP_SKU_PROPERTIES = "SkuProperties";
	public static String PROP_PRICE = "Price";
	public static String PROP_BUYER_PAYMENT = "BuyerPayment";
	public static String PROP_SPO_ID = "SpoId";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_ID = "Id";
	public static String PROP_AUCTION_PRICE = "AuctionPrice";


	// constructors
	public BaseTbSubPurchaseOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbSubPurchaseOrder (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String status;
	private java.math.BigDecimal refundFee;
	private java.lang.Long spoId;
	private java.lang.Long fenxiaoId;
	private java.lang.Long skuId;
	private java.lang.Long tcOrderId;
	private java.lang.String order200Status;
	private java.math.BigDecimal auctionPrice;
	private java.lang.Long itemId;
	private java.lang.String oldSkuProperties;
	private java.lang.String itemOuterId;
	private java.lang.String skuOuterId;
	private java.lang.String skuProperties;
	private java.lang.Integer num;
	private java.lang.String title;
	private java.math.BigDecimal price;
	private java.lang.String snapshotUrl;
	private java.util.Date created;
	private java.math.BigDecimal totalFee;
	private java.math.BigDecimal distributorPayment;
	private java.math.BigDecimal buyerPayment;
	private java.lang.Integer storeId;
	private java.lang.String updateFlag;
	private java.util.Date updateTime;



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
	 * Return the value associated with the column: refund_fee
	 */
	public java.math.BigDecimal getRefundFee () {
		return refundFee;
	}

	/**
	 * Set the value related to the column: refund_fee
	 * @param refundFee the refund_fee value
	 */
	public void setRefundFee (java.math.BigDecimal refundFee) {
		this.refundFee = refundFee;
	}



	/**
	 * Return the value associated with the column: spo_id
	 */
	public java.lang.Long getSpoId () {
		return spoId;
	}

	/**
	 * Set the value related to the column: spo_id
	 * @param spoId the spo_id value
	 */
	public void setSpoId (java.lang.Long spoId) {
		this.spoId = spoId;
	}



	/**
	 * Return the value associated with the column: fenxiao_id
	 */
	public java.lang.Long getFenxiaoId () {
		return fenxiaoId;
	}

	/**
	 * Set the value related to the column: fenxiao_id
	 * @param fenxiaoId the fenxiao_id value
	 */
	public void setFenxiaoId (java.lang.Long fenxiaoId) {
		this.fenxiaoId = fenxiaoId;
	}



	/**
	 * Return the value associated with the column: sku_id
	 */
	public java.lang.Long getSkuId () {
		return skuId;
	}

	/**
	 * Set the value related to the column: sku_id
	 * @param skuId the sku_id value
	 */
	public void setSkuId (java.lang.Long skuId) {
		this.skuId = skuId;
	}



	/**
	 * Return the value associated with the column: tc_order_id
	 */
	public java.lang.Long getTcOrderId () {
		return tcOrderId;
	}

	/**
	 * Set the value related to the column: tc_order_id
	 * @param tcOrderId the tc_order_id value
	 */
	public void setTcOrderId (java.lang.Long tcOrderId) {
		this.tcOrderId = tcOrderId;
	}



	/**
	 * Return the value associated with the column: order_200_status
	 */
	public java.lang.String getOrder200Status () {
		return order200Status;
	}

	/**
	 * Set the value related to the column: order_200_status
	 * @param order200Status the order_200_status value
	 */
	public void setOrder200Status (java.lang.String order200Status) {
		this.order200Status = order200Status;
	}



	/**
	 * Return the value associated with the column: auction_price
	 */
	public java.math.BigDecimal getAuctionPrice () {
		return auctionPrice;
	}

	/**
	 * Set the value related to the column: auction_price
	 * @param auctionPrice the auction_price value
	 */
	public void setAuctionPrice (java.math.BigDecimal auctionPrice) {
		this.auctionPrice = auctionPrice;
	}



	/**
	 * Return the value associated with the column: item_id
	 */
	public java.lang.Long getItemId () {
		return itemId;
	}

	/**
	 * Set the value related to the column: item_id
	 * @param itemId the item_id value
	 */
	public void setItemId (java.lang.Long itemId) {
		this.itemId = itemId;
	}



	/**
	 * Return the value associated with the column: old_sku_properties
	 */
	public java.lang.String getOldSkuProperties () {
		return oldSkuProperties;
	}

	/**
	 * Set the value related to the column: old_sku_properties
	 * @param oldSkuProperties the old_sku_properties value
	 */
	public void setOldSkuProperties (java.lang.String oldSkuProperties) {
		this.oldSkuProperties = oldSkuProperties;
	}



	/**
	 * Return the value associated with the column: item_outer_id
	 */
	public java.lang.String getItemOuterId () {
		return itemOuterId;
	}

	/**
	 * Set the value related to the column: item_outer_id
	 * @param itemOuterId the item_outer_id value
	 */
	public void setItemOuterId (java.lang.String itemOuterId) {
		this.itemOuterId = itemOuterId;
	}



	/**
	 * Return the value associated with the column: sku_outer_id
	 */
	public java.lang.String getSkuOuterId () {
		return skuOuterId;
	}

	/**
	 * Set the value related to the column: sku_outer_id
	 * @param skuOuterId the sku_outer_id value
	 */
	public void setSkuOuterId (java.lang.String skuOuterId) {
		this.skuOuterId = skuOuterId;
	}



	/**
	 * Return the value associated with the column: sku_properties
	 */
	public java.lang.String getSkuProperties () {
		return skuProperties;
	}

	/**
	 * Set the value related to the column: sku_properties
	 * @param skuProperties the sku_properties value
	 */
	public void setSkuProperties (java.lang.String skuProperties) {
		this.skuProperties = skuProperties;
	}



	/**
	 * Return the value associated with the column: num
	 */
	public java.lang.Integer getNum () {
		return num;
	}

	/**
	 * Set the value related to the column: num
	 * @param num the num value
	 */
	public void setNum (java.lang.Integer num) {
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
	 * Return the value associated with the column: distributor_payment
	 */
	public java.math.BigDecimal getDistributorPayment () {
		return distributorPayment;
	}

	/**
	 * Set the value related to the column: distributor_payment
	 * @param distributorPayment the distributor_payment value
	 */
	public void setDistributorPayment (java.math.BigDecimal distributorPayment) {
		this.distributorPayment = distributorPayment;
	}



	/**
	 * Return the value associated with the column: buyer_payment
	 */
	public java.math.BigDecimal getBuyerPayment () {
		return buyerPayment;
	}

	/**
	 * Set the value related to the column: buyer_payment
	 * @param buyerPayment the buyer_payment value
	 */
	public void setBuyerPayment (java.math.BigDecimal buyerPayment) {
		this.buyerPayment = buyerPayment;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.TbSubPurchaseOrder)) return false;
		else {
			com.sunmw.web.entity.TbSubPurchaseOrder tbSubPurchaseOrder = (com.sunmw.web.entity.TbSubPurchaseOrder) obj;
			if (null == this.getId() || null == tbSubPurchaseOrder.getId()) return false;
			else return (this.getId().equals(tbSubPurchaseOrder.getId()));
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