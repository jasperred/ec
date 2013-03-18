package com.sunmw.paipai.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the pp_order_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="pp_order_item"
 */

public abstract class BasePpOrderItem  implements Serializable {

	public static String REF = "PpOrderItem";
	public static String PROP_ITEM_CODE = "ItemCode";
	public static String PROP_ITEM_LOCAL_CODE = "ItemLocalCode";
	public static String PROP_ACCOUNT = "Account";
	public static String PROP_ITEM_DEAL_PRICE = "ItemDealPrice";
	public static String PROP_STOCK_ATTR = "StockAttr";
	public static String PROP_ITEM_ADJUST_PRICE = "ItemAdjustPrice";
	public static String PROP_ITEM_DETAIL_LINK = "ItemDetailLink";
	public static String PROP_REFUND_STATE_DESC = "RefundStateDesc";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_ITEM_NAME = "ItemName";
	public static String PROP_ITEM_FLAG = "ItemFlag";
	public static String PROP_TRADE_PROPERTY_MASK = "TradePropertyMask";
	public static String PROP_ITEM_DEAL_COUNT = "ItemDealCount";
	public static String PROP_DEAL_CODE = "DealCode";
	public static String PROP_ITEM_DISCOUNT_FEE = "ItemDiscountFee";
	public static String PROP_STOCK_LOCAL_CODE = "StockLocalCode";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_ITEM_PIC80 = "ItemPic80";
	public static String PROP_AVAILABLE_ACTION = "AvailableAction";
	public static String PROP_REFUND_STATE = "RefundState";
	public static String PROP_DEAL_SUB_CODE = "DealSubCode";
	public static String PROP_ITEM_CODE_HISTORY = "ItemCodeHistory";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_ID = "Id";
	public static String PROP_ITEM_RETAIL_PRICE = "ItemRetailPrice";


	// constructors
	public BasePpOrderItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePpOrderItem (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String dealSubCode;
	private java.lang.String itemCode;
	private java.lang.String itemCodeHistory;
	private java.lang.String itemLocalCode;
	private java.lang.String stockLocalCode;
	private java.lang.String stockAttr;
	private java.lang.String itemDetailLink;
	private java.lang.String itemName;
	private java.lang.String itemPic80;
	private java.math.BigDecimal itemRetailPrice;
	private java.math.BigDecimal itemDealPrice;
	private java.math.BigDecimal itemAdjustPrice;
	private java.math.BigDecimal itemDiscountFee;
	private java.lang.Integer itemDealCount;
	private java.lang.String account;
	private java.lang.String itemFlag;
	private java.lang.String refundState;
	private java.lang.String refundStateDesc;
	private java.lang.String tradePropertyMask;
	private java.lang.String availableAction;
	private java.lang.String dealCode;
	private java.lang.Integer storeId;
	private java.util.Date updateTime;
	private java.lang.String updateFlag;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="pp_order_item_id"
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
	 * Return the value associated with the column: deal_sub_code
	 */
	public java.lang.String getDealSubCode () {
		return dealSubCode;
	}

	/**
	 * Set the value related to the column: deal_sub_code
	 * @param dealSubCode the deal_sub_code value
	 */
	public void setDealSubCode (java.lang.String dealSubCode) {
		this.dealSubCode = dealSubCode;
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
	 * Return the value associated with the column: item_code_history
	 */
	public java.lang.String getItemCodeHistory () {
		return itemCodeHistory;
	}

	/**
	 * Set the value related to the column: item_code_history
	 * @param itemCodeHistory the item_code_history value
	 */
	public void setItemCodeHistory (java.lang.String itemCodeHistory) {
		this.itemCodeHistory = itemCodeHistory;
	}



	/**
	 * Return the value associated with the column: item_local_code
	 */
	public java.lang.String getItemLocalCode () {
		return itemLocalCode;
	}

	/**
	 * Set the value related to the column: item_local_code
	 * @param itemLocalCode the item_local_code value
	 */
	public void setItemLocalCode (java.lang.String itemLocalCode) {
		this.itemLocalCode = itemLocalCode;
	}



	/**
	 * Return the value associated with the column: stock_local_code
	 */
	public java.lang.String getStockLocalCode () {
		return stockLocalCode;
	}

	/**
	 * Set the value related to the column: stock_local_code
	 * @param stockLocalCode the stock_local_code value
	 */
	public void setStockLocalCode (java.lang.String stockLocalCode) {
		this.stockLocalCode = stockLocalCode;
	}



	/**
	 * Return the value associated with the column: stock_attr
	 */
	public java.lang.String getStockAttr () {
		return stockAttr;
	}

	/**
	 * Set the value related to the column: stock_attr
	 * @param stockAttr the stock_attr value
	 */
	public void setStockAttr (java.lang.String stockAttr) {
		this.stockAttr = stockAttr;
	}



	/**
	 * Return the value associated with the column: item_detail_link
	 */
	public java.lang.String getItemDetailLink () {
		return itemDetailLink;
	}

	/**
	 * Set the value related to the column: item_detail_link
	 * @param itemDetailLink the item_detail_link value
	 */
	public void setItemDetailLink (java.lang.String itemDetailLink) {
		this.itemDetailLink = itemDetailLink;
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
	 * Return the value associated with the column: item_pic80
	 */
	public java.lang.String getItemPic80 () {
		return itemPic80;
	}

	/**
	 * Set the value related to the column: item_pic80
	 * @param itemPic80 the item_pic80 value
	 */
	public void setItemPic80 (java.lang.String itemPic80) {
		this.itemPic80 = itemPic80;
	}



	/**
	 * Return the value associated with the column: item_retail_price
	 */
	public java.math.BigDecimal getItemRetailPrice () {
		return itemRetailPrice;
	}

	/**
	 * Set the value related to the column: item_retail_price
	 * @param itemRetailPrice the item_retail_price value
	 */
	public void setItemRetailPrice (java.math.BigDecimal itemRetailPrice) {
		this.itemRetailPrice = itemRetailPrice;
	}



	/**
	 * Return the value associated with the column: item_deal_price
	 */
	public java.math.BigDecimal getItemDealPrice () {
		return itemDealPrice;
	}

	/**
	 * Set the value related to the column: item_deal_price
	 * @param itemDealPrice the item_deal_price value
	 */
	public void setItemDealPrice (java.math.BigDecimal itemDealPrice) {
		this.itemDealPrice = itemDealPrice;
	}



	/**
	 * Return the value associated with the column: item_adjust_price
	 */
	public java.math.BigDecimal getItemAdjustPrice () {
		return itemAdjustPrice;
	}

	/**
	 * Set the value related to the column: item_adjust_price
	 * @param itemAdjustPrice the item_adjust_price value
	 */
	public void setItemAdjustPrice (java.math.BigDecimal itemAdjustPrice) {
		this.itemAdjustPrice = itemAdjustPrice;
	}



	/**
	 * Return the value associated with the column: item_discount_fee
	 */
	public java.math.BigDecimal getItemDiscountFee () {
		return itemDiscountFee;
	}

	/**
	 * Set the value related to the column: item_discount_fee
	 * @param itemDiscountFee the item_discount_fee value
	 */
	public void setItemDiscountFee (java.math.BigDecimal itemDiscountFee) {
		this.itemDiscountFee = itemDiscountFee;
	}



	/**
	 * Return the value associated with the column: item_deal_count
	 */
	public java.lang.Integer getItemDealCount () {
		return itemDealCount;
	}

	/**
	 * Set the value related to the column: item_deal_count
	 * @param itemDealCount the item_deal_count value
	 */
	public void setItemDealCount (java.lang.Integer itemDealCount) {
		this.itemDealCount = itemDealCount;
	}



	/**
	 * Return the value associated with the column: account
	 */
	public java.lang.String getAccount () {
		return account;
	}

	/**
	 * Set the value related to the column: account
	 * @param account the account value
	 */
	public void setAccount (java.lang.String account) {
		this.account = account;
	}



	/**
	 * Return the value associated with the column: item_flag
	 */
	public java.lang.String getItemFlag () {
		return itemFlag;
	}

	/**
	 * Set the value related to the column: item_flag
	 * @param itemFlag the item_flag value
	 */
	public void setItemFlag (java.lang.String itemFlag) {
		this.itemFlag = itemFlag;
	}



	/**
	 * Return the value associated with the column: refund_state
	 */
	public java.lang.String getRefundState () {
		return refundState;
	}

	/**
	 * Set the value related to the column: refund_state
	 * @param refundState the refund_state value
	 */
	public void setRefundState (java.lang.String refundState) {
		this.refundState = refundState;
	}



	/**
	 * Return the value associated with the column: refund_state_desc
	 */
	public java.lang.String getRefundStateDesc () {
		return refundStateDesc;
	}

	/**
	 * Set the value related to the column: refund_state_desc
	 * @param refundStateDesc the refund_state_desc value
	 */
	public void setRefundStateDesc (java.lang.String refundStateDesc) {
		this.refundStateDesc = refundStateDesc;
	}



	/**
	 * Return the value associated with the column: trade_property_mask
	 */
	public java.lang.String getTradePropertyMask () {
		return tradePropertyMask;
	}

	/**
	 * Set the value related to the column: trade_property_mask
	 * @param tradePropertyMask the trade_property_mask value
	 */
	public void setTradePropertyMask (java.lang.String tradePropertyMask) {
		this.tradePropertyMask = tradePropertyMask;
	}



	/**
	 * Return the value associated with the column: available_action
	 */
	public java.lang.String getAvailableAction () {
		return availableAction;
	}

	/**
	 * Set the value related to the column: available_action
	 * @param availableAction the available_action value
	 */
	public void setAvailableAction (java.lang.String availableAction) {
		this.availableAction = availableAction;
	}



	/**
	 * Return the value associated with the column: deal_code
	 */
	public java.lang.String getDealCode () {
		return dealCode;
	}

	/**
	 * Set the value related to the column: deal_code
	 * @param dealCode the deal_code value
	 */
	public void setDealCode (java.lang.String dealCode) {
		this.dealCode = dealCode;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.paipai.entity.PpOrderItem)) return false;
		else {
			com.sunmw.paipai.entity.PpOrderItem ppOrderItem = (com.sunmw.paipai.entity.PpOrderItem) obj;
			if (null == this.getId() || null == ppOrderItem.getId()) return false;
			else return (this.getId().equals(ppOrderItem.getId()));
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