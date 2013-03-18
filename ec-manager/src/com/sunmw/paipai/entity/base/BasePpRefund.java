package com.sunmw.paipai.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the pp_refund table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="pp_refund"
 */

public abstract class BasePpRefund  implements Serializable {

	public static String REF = "PpRefund";
	public static String PROP_SELLER_REFUND_ADDR = "SellerRefundAddr";
	public static String PROP_ITEM_CODE = "ItemCode";
	public static String PROP_ITEM_LOCAL_CODE = "ItemLocalCode";
	public static String PROP_REFUND_TO_BUYER = "RefundToBuyer";
	public static String PROP_BUYER_CONSIGNMENT_DESC = "BuyerConsignmentDesc";
	public static String PROP_REFUND_REASON_TYPE = "RefundReasonType";
	public static String PROP_STOCK_ATTR = "StockAttr";
	public static String PROP_LAST_UPDATE_TIME = "LastUpdateTime";
	public static String PROP_PRE_REFUND_STATE = "PreRefundState";
	public static String PROP_REFUND_REQ_TIME = "RefundReqTime";
	public static String PROP_SELLER_AGREE_MSG = "SellerAgreeMsg";
	public static String PROP_REF_DEAL_CODE = "RefDealCode";
	public static String PROP_DEAL_CREATE_TIME = "DealCreateTime";
	public static String PROP_REFUND_ITEM_STATE = "RefundItemState";
	public static String PROP_REFUND_STATE_DESC = "RefundStateDesc";
	public static String PROP_REFUND_END_TIME = "RefundEndTime";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_TRADE_PROPERTY_MASK = "TradePropertyMask";
	public static String PROP_TIMEOUT_ITEM_FLAG = "TimeoutItemFlag";
	public static String PROP_DEAL_CODE = "DealCode";
	public static String PROP_REFUND_TO_SELLER = "RefundToSeller";
	public static String PROP_SELLER_UIN = "SellerUin";
	public static String PROP_STOCK_LOCAL_CODE = "StockLocalCode";
	public static String PROP_SELLER_AGREE_ITEM_MSG = "SellerAgreeItemMsg";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_REF_DEAL_DETAIL_LINK = "RefDealDetailLink";
	public static String PROP_SELLER_AGREE_GIVEBACK_TIME = "SellerAgreeGivebackTime";
	public static String PROP_REFUND_STATE = "RefundState";
	public static String PROP_DEAL_SUB_CODE = "DealSubCode";
	public static String PROP_REFUND_TO_BUYER_NUM = "RefundToBuyerNum";
	public static String PROP_TRADE_REFUND_ID = "TradeRefundId";
	public static String PROP_SELLER_REFUSE_TIME = "SellerRefuseTime";
	public static String PROP_ITEM_CODE_HISTORY = "ItemCodeHistory";
	public static String PROP_ID = "Id";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_BUYER_CONSIGNMENT_TIME = "BuyerConsignmentTime";
	public static String PROP_BUYER_CONSIGNMENT_WULIU = "BuyerConsignmentWuliu";
	public static String PROP_REFUND_REASON_DESC = "RefundReasonDesc";
	public static String PROP_REFUND_REQITEM_FLAG = "RefundReqitemFlag";


	// constructors
	public BasePpRefund () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePpRefund (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String refDealCode;
	private java.lang.String refDealDetailLink;
	private java.lang.String itemCode;
	private java.lang.String itemCodeHistory;
	private java.lang.String itemLocalCode;
	private java.lang.String stockLocalCode;
	private java.lang.String stockAttr;
	private java.lang.String buyerConsignmentDesc;
	private java.util.Date buyerConsignmentTime;
	private java.lang.String buyerConsignmentWuliu;
	private java.lang.String dealCode;
	private java.util.Date dealCreateTime;
	private java.lang.String dealSubCode;
	private java.util.Date lastUpdateTime;
	private java.lang.String preRefundState;
	private java.util.Date refundEndTime;
	private java.lang.String refundItemState;
	private java.lang.String refundReasonDesc;
	private java.lang.String refundReasonType;
	private java.lang.String refundReqitemFlag;
	private java.util.Date refundReqTime;
	private java.lang.String refundState;
	private java.lang.String refundToBuyer;
	private java.lang.Integer refundToBuyerNum;
	private java.lang.String refundToSeller;
	private java.util.Date sellerAgreeGivebackTime;
	private java.lang.String sellerAgreeItemMsg;
	private java.lang.String sellerAgreeMsg;
	private java.lang.String sellerRefundAddr;
	private java.util.Date sellerRefuseTime;
	private java.lang.String sellerUin;
	private java.lang.Integer timeoutItemFlag;
	private java.lang.String tradePropertyMask;
	private java.lang.Integer tradeRefundId;
	private java.lang.Integer storeId;
	private java.util.Date updateTime;
	private java.lang.String updateFlag;
	private java.lang.String refundStateDesc;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="pp_refund_id"
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
	 * Return the value associated with the column: ref_deal_code
	 */
	public java.lang.String getRefDealCode () {
		return refDealCode;
	}

	/**
	 * Set the value related to the column: ref_deal_code
	 * @param refDealCode the ref_deal_code value
	 */
	public void setRefDealCode (java.lang.String refDealCode) {
		this.refDealCode = refDealCode;
	}



	/**
	 * Return the value associated with the column: ref_deal_detail_link
	 */
	public java.lang.String getRefDealDetailLink () {
		return refDealDetailLink;
	}

	/**
	 * Set the value related to the column: ref_deal_detail_link
	 * @param refDealDetailLink the ref_deal_detail_link value
	 */
	public void setRefDealDetailLink (java.lang.String refDealDetailLink) {
		this.refDealDetailLink = refDealDetailLink;
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
	 * Return the value associated with the column: buyer_consignment_desc
	 */
	public java.lang.String getBuyerConsignmentDesc () {
		return buyerConsignmentDesc;
	}

	/**
	 * Set the value related to the column: buyer_consignment_desc
	 * @param buyerConsignmentDesc the buyer_consignment_desc value
	 */
	public void setBuyerConsignmentDesc (java.lang.String buyerConsignmentDesc) {
		this.buyerConsignmentDesc = buyerConsignmentDesc;
	}



	/**
	 * Return the value associated with the column: buyer_consignment_time
	 */
	public java.util.Date getBuyerConsignmentTime () {
		return buyerConsignmentTime;
	}

	/**
	 * Set the value related to the column: buyer_consignment_time
	 * @param buyerConsignmentTime the buyer_consignment_time value
	 */
	public void setBuyerConsignmentTime (java.util.Date buyerConsignmentTime) {
		this.buyerConsignmentTime = buyerConsignmentTime;
	}



	/**
	 * Return the value associated with the column: buyer_consignment_wuliu
	 */
	public java.lang.String getBuyerConsignmentWuliu () {
		return buyerConsignmentWuliu;
	}

	/**
	 * Set the value related to the column: buyer_consignment_wuliu
	 * @param buyerConsignmentWuliu the buyer_consignment_wuliu value
	 */
	public void setBuyerConsignmentWuliu (java.lang.String buyerConsignmentWuliu) {
		this.buyerConsignmentWuliu = buyerConsignmentWuliu;
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
	 * Return the value associated with the column: deal_create_time
	 */
	public java.util.Date getDealCreateTime () {
		return dealCreateTime;
	}

	/**
	 * Set the value related to the column: deal_create_time
	 * @param dealCreateTime the deal_create_time value
	 */
	public void setDealCreateTime (java.util.Date dealCreateTime) {
		this.dealCreateTime = dealCreateTime;
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
	 * Return the value associated with the column: last_update_time
	 */
	public java.util.Date getLastUpdateTime () {
		return lastUpdateTime;
	}

	/**
	 * Set the value related to the column: last_update_time
	 * @param lastUpdateTime the last_update_time value
	 */
	public void setLastUpdateTime (java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}



	/**
	 * Return the value associated with the column: pre_refund_state
	 */
	public java.lang.String getPreRefundState () {
		return preRefundState;
	}

	/**
	 * Set the value related to the column: pre_refund_state
	 * @param preRefundState the pre_refund_state value
	 */
	public void setPreRefundState (java.lang.String preRefundState) {
		this.preRefundState = preRefundState;
	}



	/**
	 * Return the value associated with the column: refund_end_time
	 */
	public java.util.Date getRefundEndTime () {
		return refundEndTime;
	}

	/**
	 * Set the value related to the column: refund_end_time
	 * @param refundEndTime the refund_end_time value
	 */
	public void setRefundEndTime (java.util.Date refundEndTime) {
		this.refundEndTime = refundEndTime;
	}



	/**
	 * Return the value associated with the column: refund_item_state
	 */
	public java.lang.String getRefundItemState () {
		return refundItemState;
	}

	/**
	 * Set the value related to the column: refund_item_state
	 * @param refundItemState the refund_item_state value
	 */
	public void setRefundItemState (java.lang.String refundItemState) {
		this.refundItemState = refundItemState;
	}



	/**
	 * Return the value associated with the column: refund_reason_desc
	 */
	public java.lang.String getRefundReasonDesc () {
		return refundReasonDesc;
	}

	/**
	 * Set the value related to the column: refund_reason_desc
	 * @param refundReasonDesc the refund_reason_desc value
	 */
	public void setRefundReasonDesc (java.lang.String refundReasonDesc) {
		this.refundReasonDesc = refundReasonDesc;
	}



	/**
	 * Return the value associated with the column: refund_reason_type
	 */
	public java.lang.String getRefundReasonType () {
		return refundReasonType;
	}

	/**
	 * Set the value related to the column: refund_reason_type
	 * @param refundReasonType the refund_reason_type value
	 */
	public void setRefundReasonType (java.lang.String refundReasonType) {
		this.refundReasonType = refundReasonType;
	}



	/**
	 * Return the value associated with the column: refund_reqitem_flag
	 */
	public java.lang.String getRefundReqitemFlag () {
		return refundReqitemFlag;
	}

	/**
	 * Set the value related to the column: refund_reqitem_flag
	 * @param refundReqitemFlag the refund_reqitem_flag value
	 */
	public void setRefundReqitemFlag (java.lang.String refundReqitemFlag) {
		this.refundReqitemFlag = refundReqitemFlag;
	}



	/**
	 * Return the value associated with the column: refund_req_time
	 */
	public java.util.Date getRefundReqTime () {
		return refundReqTime;
	}

	/**
	 * Set the value related to the column: refund_req_time
	 * @param refundReqTime the refund_req_time value
	 */
	public void setRefundReqTime (java.util.Date refundReqTime) {
		this.refundReqTime = refundReqTime;
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
	 * Return the value associated with the column: refund_to_buyer
	 */
	public java.lang.String getRefundToBuyer () {
		return refundToBuyer;
	}

	/**
	 * Set the value related to the column: refund_to_buyer
	 * @param refundToBuyer the refund_to_buyer value
	 */
	public void setRefundToBuyer (java.lang.String refundToBuyer) {
		this.refundToBuyer = refundToBuyer;
	}



	/**
	 * Return the value associated with the column: refund_to_buyer_num
	 */
	public java.lang.Integer getRefundToBuyerNum () {
		return refundToBuyerNum;
	}

	/**
	 * Set the value related to the column: refund_to_buyer_num
	 * @param refundToBuyerNum the refund_to_buyer_num value
	 */
	public void setRefundToBuyerNum (java.lang.Integer refundToBuyerNum) {
		this.refundToBuyerNum = refundToBuyerNum;
	}



	/**
	 * Return the value associated with the column: refund_to_seller
	 */
	public java.lang.String getRefundToSeller () {
		return refundToSeller;
	}

	/**
	 * Set the value related to the column: refund_to_seller
	 * @param refundToSeller the refund_to_seller value
	 */
	public void setRefundToSeller (java.lang.String refundToSeller) {
		this.refundToSeller = refundToSeller;
	}



	/**
	 * Return the value associated with the column: seller_agree_giveback_time
	 */
	public java.util.Date getSellerAgreeGivebackTime () {
		return sellerAgreeGivebackTime;
	}

	/**
	 * Set the value related to the column: seller_agree_giveback_time
	 * @param sellerAgreeGivebackTime the seller_agree_giveback_time value
	 */
	public void setSellerAgreeGivebackTime (java.util.Date sellerAgreeGivebackTime) {
		this.sellerAgreeGivebackTime = sellerAgreeGivebackTime;
	}



	/**
	 * Return the value associated with the column: seller_agree_item_msg
	 */
	public java.lang.String getSellerAgreeItemMsg () {
		return sellerAgreeItemMsg;
	}

	/**
	 * Set the value related to the column: seller_agree_item_msg
	 * @param sellerAgreeItemMsg the seller_agree_item_msg value
	 */
	public void setSellerAgreeItemMsg (java.lang.String sellerAgreeItemMsg) {
		this.sellerAgreeItemMsg = sellerAgreeItemMsg;
	}



	/**
	 * Return the value associated with the column: seller_agree_msg
	 */
	public java.lang.String getSellerAgreeMsg () {
		return sellerAgreeMsg;
	}

	/**
	 * Set the value related to the column: seller_agree_msg
	 * @param sellerAgreeMsg the seller_agree_msg value
	 */
	public void setSellerAgreeMsg (java.lang.String sellerAgreeMsg) {
		this.sellerAgreeMsg = sellerAgreeMsg;
	}



	/**
	 * Return the value associated with the column: seller_refund_addr
	 */
	public java.lang.String getSellerRefundAddr () {
		return sellerRefundAddr;
	}

	/**
	 * Set the value related to the column: seller_refund_addr
	 * @param sellerRefundAddr the seller_refund_addr value
	 */
	public void setSellerRefundAddr (java.lang.String sellerRefundAddr) {
		this.sellerRefundAddr = sellerRefundAddr;
	}



	/**
	 * Return the value associated with the column: seller_refuse_time
	 */
	public java.util.Date getSellerRefuseTime () {
		return sellerRefuseTime;
	}

	/**
	 * Set the value related to the column: seller_refuse_time
	 * @param sellerRefuseTime the seller_refuse_time value
	 */
	public void setSellerRefuseTime (java.util.Date sellerRefuseTime) {
		this.sellerRefuseTime = sellerRefuseTime;
	}



	/**
	 * Return the value associated with the column: seller_uin
	 */
	public java.lang.String getSellerUin () {
		return sellerUin;
	}

	/**
	 * Set the value related to the column: seller_uin
	 * @param sellerUin the seller_uin value
	 */
	public void setSellerUin (java.lang.String sellerUin) {
		this.sellerUin = sellerUin;
	}



	/**
	 * Return the value associated with the column: timeout_item_flag
	 */
	public java.lang.Integer getTimeoutItemFlag () {
		return timeoutItemFlag;
	}

	/**
	 * Set the value related to the column: timeout_item_flag
	 * @param timeoutItemFlag the timeout_item_flag value
	 */
	public void setTimeoutItemFlag (java.lang.Integer timeoutItemFlag) {
		this.timeoutItemFlag = timeoutItemFlag;
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
	 * Return the value associated with the column: trade_refund_id
	 */
	public java.lang.Integer getTradeRefundId () {
		return tradeRefundId;
	}

	/**
	 * Set the value related to the column: trade_refund_id
	 * @param tradeRefundId the trade_refund_id value
	 */
	public void setTradeRefundId (java.lang.Integer tradeRefundId) {
		this.tradeRefundId = tradeRefundId;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.paipai.entity.PpRefund)) return false;
		else {
			com.sunmw.paipai.entity.PpRefund ppRefund = (com.sunmw.paipai.entity.PpRefund) obj;
			if (null == this.getId() || null == ppRefund.getId()) return false;
			else return (this.getId().equals(ppRefund.getId()));
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