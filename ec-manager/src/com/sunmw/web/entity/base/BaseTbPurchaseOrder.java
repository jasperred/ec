package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_purchase_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_purchase_order"
 */

public abstract class BaseTbPurchaseOrder  implements Serializable {

	public static String REF = "TbPurchaseOrder";
	public static String PROP_TOTAL_FEE = "TotalFee";
	public static String PROP_RECEIVER_ZIP = "ReceiverZip";
	public static String PROP_DISTRIBUTOR_USERNAME = "DistributorUsername";
	public static String PROP_DISTRIBUTOR_FROM = "DistributorFrom";
	public static String PROP_SUPPLIER_USERNAME = "SupplierUsername";
	public static String PROP_RECEIVER_DISTRICT = "ReceiverDistrict";
	public static String PROP_ISV_CUSTOM_KEY = "IsvCustomKey";
	public static String PROP_BUYER_NICK = "BuyerNick";
	public static String PROP_CREATED = "Created";
	public static String PROP_LOGISTICS_COMPANY_NAME = "LogisticsCompanyName";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_PAY_TIME = "PayTime";
	public static String PROP_TC_ORDER_ID = "TcOrderId";
	public static String PROP_ISV_CUSTOM_VALUE = "IsvCustomValue";
	public static String PROP_CONSIGN_TIME = "ConsignTime";
	public static String PROP_SUPPLIER_MEMO = "SupplierMemo";
	public static String PROP_FENXIAO_ID = "FenxiaoId";
	public static String PROP_RECEIVER_CITY = "ReceiverCity";
	public static String PROP_SHIPPING = "Shipping";
	public static String PROP_POST_FEE = "PostFee";
	public static String PROP_PAY_TYPE = "PayType";
	public static String PROP_RECEIVER_MOBILE_PHONE = "ReceiverMobilePhone";
	public static String PROP_RECEIVER_STATE = "ReceiverState";
	public static String PROP_SUPPLIER_FLAG = "SupplierFlag";
	public static String PROP_ALIPAY_NO = "AlipayNo";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_SUPPLIER_FROM = "SupplierFrom";
	public static String PROP_PO_ID = "PoId";
	public static String PROP_RECEIVER_PHONE = "ReceiverPhone";
	public static String PROP_DISTRIBUTOR_PAYMENT = "DistributorPayment";
	public static String PROP_STATUS = "Status";
	public static String PROP_RECEIVER_ADDRESS = "ReceiverAddress";
	public static String PROP_LOGISTICS_ID = "LogisticsId";
	public static String PROP_TRADE_TYPE = "TradeType";
	public static String PROP_SNAPSHOT_URL = "SnapshotUrl";
	public static String PROP_MEMO = "Memo";
	public static String PROP_ID = "Id";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_END_TIME = "EndTime";
	public static String PROP_RECEIVER_NAME = "ReceiverName";


	// constructors
	public BaseTbPurchaseOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbPurchaseOrder (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String supplierMemo;
	private java.lang.Long fenxiaoId;
	private java.lang.String payType;
	private java.lang.String tradeType;
	private java.lang.String distributorFrom;
	private java.lang.Long poId;
	private java.lang.String status;
	private java.lang.String buyerNick;
	private java.lang.String memo;
	private java.lang.Long tcOrderId;
	private java.lang.String receiverName;
	private java.lang.String receiverPhone;
	private java.lang.String receiverMobilePhone;
	private java.lang.String receiverAddress;
	private java.lang.String receiverDistrict;
	private java.lang.String receiverCity;
	private java.lang.String receiverZip;
	private java.lang.String receiverState;
	private java.lang.String shipping;
	private java.lang.String logisticsCompanyName;
	private java.lang.String logisticsId;
	private java.lang.String isvCustomKey;
	private java.lang.String isvCustomValue;
	private java.util.Date endTime;
	private java.lang.String supplierFlag;
	private java.lang.String supplierFrom;
	private java.lang.String supplierUsername;
	private java.lang.String distributorUsername;
	private java.util.Date created;
	private java.lang.String alipayNo;
	private java.math.BigDecimal totalFee;
	private java.math.BigDecimal postFee;
	private java.math.BigDecimal distributorPayment;
	private java.lang.String snapshotUrl;
	private java.util.Date payTime;
	private java.util.Date consignTime;
	private java.util.Date modified;
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
	 * Return the value associated with the column: supplier_memo
	 */
	public java.lang.String getSupplierMemo () {
		return supplierMemo;
	}

	/**
	 * Set the value related to the column: supplier_memo
	 * @param supplierMemo the supplier_memo value
	 */
	public void setSupplierMemo (java.lang.String supplierMemo) {
		this.supplierMemo = supplierMemo;
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
	 * Return the value associated with the column: pay_type
	 */
	public java.lang.String getPayType () {
		return payType;
	}

	/**
	 * Set the value related to the column: pay_type
	 * @param payType the pay_type value
	 */
	public void setPayType (java.lang.String payType) {
		this.payType = payType;
	}



	/**
	 * Return the value associated with the column: trade_type
	 */
	public java.lang.String getTradeType () {
		return tradeType;
	}

	/**
	 * Set the value related to the column: trade_type
	 * @param tradeType the trade_type value
	 */
	public void setTradeType (java.lang.String tradeType) {
		this.tradeType = tradeType;
	}



	/**
	 * Return the value associated with the column: distributor_from
	 */
	public java.lang.String getDistributorFrom () {
		return distributorFrom;
	}

	/**
	 * Set the value related to the column: distributor_from
	 * @param distributorFrom the distributor_from value
	 */
	public void setDistributorFrom (java.lang.String distributorFrom) {
		this.distributorFrom = distributorFrom;
	}



	/**
	 * Return the value associated with the column: po_id
	 */
	public java.lang.Long getPoId () {
		return poId;
	}

	/**
	 * Set the value related to the column: po_id
	 * @param poId the po_id value
	 */
	public void setPoId (java.lang.Long poId) {
		this.poId = poId;
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
	 * Return the value associated with the column: receiver_name
	 */
	public java.lang.String getReceiverName () {
		return receiverName;
	}

	/**
	 * Set the value related to the column: receiver_name
	 * @param receiverName the receiver_name value
	 */
	public void setReceiverName (java.lang.String receiverName) {
		this.receiverName = receiverName;
	}



	/**
	 * Return the value associated with the column: receiver_phone
	 */
	public java.lang.String getReceiverPhone () {
		return receiverPhone;
	}

	/**
	 * Set the value related to the column: receiver_phone
	 * @param receiverPhone the receiver_phone value
	 */
	public void setReceiverPhone (java.lang.String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}



	/**
	 * Return the value associated with the column: receiver_mobile_phone
	 */
	public java.lang.String getReceiverMobilePhone () {
		return receiverMobilePhone;
	}

	/**
	 * Set the value related to the column: receiver_mobile_phone
	 * @param receiverMobilePhone the receiver_mobile_phone value
	 */
	public void setReceiverMobilePhone (java.lang.String receiverMobilePhone) {
		this.receiverMobilePhone = receiverMobilePhone;
	}



	/**
	 * Return the value associated with the column: receiver_address
	 */
	public java.lang.String getReceiverAddress () {
		return receiverAddress;
	}

	/**
	 * Set the value related to the column: receiver_address
	 * @param receiverAddress the receiver_address value
	 */
	public void setReceiverAddress (java.lang.String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}



	/**
	 * Return the value associated with the column: receiver_district
	 */
	public java.lang.String getReceiverDistrict () {
		return receiverDistrict;
	}

	/**
	 * Set the value related to the column: receiver_district
	 * @param receiverDistrict the receiver_district value
	 */
	public void setReceiverDistrict (java.lang.String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}



	/**
	 * Return the value associated with the column: receiver_city
	 */
	public java.lang.String getReceiverCity () {
		return receiverCity;
	}

	/**
	 * Set the value related to the column: receiver_city
	 * @param receiverCity the receiver_city value
	 */
	public void setReceiverCity (java.lang.String receiverCity) {
		this.receiverCity = receiverCity;
	}



	/**
	 * Return the value associated with the column: receiver_zip
	 */
	public java.lang.String getReceiverZip () {
		return receiverZip;
	}

	/**
	 * Set the value related to the column: receiver_zip
	 * @param receiverZip the receiver_zip value
	 */
	public void setReceiverZip (java.lang.String receiverZip) {
		this.receiverZip = receiverZip;
	}



	/**
	 * Return the value associated with the column: receiver_state
	 */
	public java.lang.String getReceiverState () {
		return receiverState;
	}

	/**
	 * Set the value related to the column: receiver_state
	 * @param receiverState the receiver_state value
	 */
	public void setReceiverState (java.lang.String receiverState) {
		this.receiverState = receiverState;
	}



	/**
	 * Return the value associated with the column: shipping
	 */
	public java.lang.String getShipping () {
		return shipping;
	}

	/**
	 * Set the value related to the column: shipping
	 * @param shipping the shipping value
	 */
	public void setShipping (java.lang.String shipping) {
		this.shipping = shipping;
	}



	/**
	 * Return the value associated with the column: logistics_company_name
	 */
	public java.lang.String getLogisticsCompanyName () {
		return logisticsCompanyName;
	}

	/**
	 * Set the value related to the column: logistics_company_name
	 * @param logisticsCompanyName the logistics_company_name value
	 */
	public void setLogisticsCompanyName (java.lang.String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}



	/**
	 * Return the value associated with the column: logistics_id
	 */
	public java.lang.String getLogisticsId () {
		return logisticsId;
	}

	/**
	 * Set the value related to the column: logistics_id
	 * @param logisticsId the logistics_id value
	 */
	public void setLogisticsId (java.lang.String logisticsId) {
		this.logisticsId = logisticsId;
	}



	/**
	 * Return the value associated with the column: isv_custom_key
	 */
	public java.lang.String getIsvCustomKey () {
		return isvCustomKey;
	}

	/**
	 * Set the value related to the column: isv_custom_key
	 * @param isvCustomKey the isv_custom_key value
	 */
	public void setIsvCustomKey (java.lang.String isvCustomKey) {
		this.isvCustomKey = isvCustomKey;
	}



	/**
	 * Return the value associated with the column: isv_custom_value
	 */
	public java.lang.String getIsvCustomValue () {
		return isvCustomValue;
	}

	/**
	 * Set the value related to the column: isv_custom_value
	 * @param isvCustomValue the isv_custom_value value
	 */
	public void setIsvCustomValue (java.lang.String isvCustomValue) {
		this.isvCustomValue = isvCustomValue;
	}



	/**
	 * Return the value associated with the column: end_time
	 */
	public java.util.Date getEndTime () {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * @param endTime the end_time value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}



	/**
	 * Return the value associated with the column: supplier_flag
	 */
	public java.lang.String getSupplierFlag () {
		return supplierFlag;
	}

	/**
	 * Set the value related to the column: supplier_flag
	 * @param supplierFlag the supplier_flag value
	 */
	public void setSupplierFlag (java.lang.String supplierFlag) {
		this.supplierFlag = supplierFlag;
	}



	/**
	 * Return the value associated with the column: supplier_from
	 */
	public java.lang.String getSupplierFrom () {
		return supplierFrom;
	}

	/**
	 * Set the value related to the column: supplier_from
	 * @param supplierFrom the supplier_from value
	 */
	public void setSupplierFrom (java.lang.String supplierFrom) {
		this.supplierFrom = supplierFrom;
	}



	/**
	 * Return the value associated with the column: supplier_username
	 */
	public java.lang.String getSupplierUsername () {
		return supplierUsername;
	}

	/**
	 * Set the value related to the column: supplier_username
	 * @param supplierUsername the supplier_username value
	 */
	public void setSupplierUsername (java.lang.String supplierUsername) {
		this.supplierUsername = supplierUsername;
	}



	/**
	 * Return the value associated with the column: distributor_username
	 */
	public java.lang.String getDistributorUsername () {
		return distributorUsername;
	}

	/**
	 * Set the value related to the column: distributor_username
	 * @param distributorUsername the distributor_username value
	 */
	public void setDistributorUsername (java.lang.String distributorUsername) {
		this.distributorUsername = distributorUsername;
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
	 * Return the value associated with the column: alipay_no
	 */
	public java.lang.String getAlipayNo () {
		return alipayNo;
	}

	/**
	 * Set the value related to the column: alipay_no
	 * @param alipayNo the alipay_no value
	 */
	public void setAlipayNo (java.lang.String alipayNo) {
		this.alipayNo = alipayNo;
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
	 * Return the value associated with the column: post_fee
	 */
	public java.math.BigDecimal getPostFee () {
		return postFee;
	}

	/**
	 * Set the value related to the column: post_fee
	 * @param postFee the post_fee value
	 */
	public void setPostFee (java.math.BigDecimal postFee) {
		this.postFee = postFee;
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
	 * Return the value associated with the column: pay_time
	 */
	public java.util.Date getPayTime () {
		return payTime;
	}

	/**
	 * Set the value related to the column: pay_time
	 * @param payTime the pay_time value
	 */
	public void setPayTime (java.util.Date payTime) {
		this.payTime = payTime;
	}



	/**
	 * Return the value associated with the column: consign_time
	 */
	public java.util.Date getConsignTime () {
		return consignTime;
	}

	/**
	 * Set the value related to the column: consign_time
	 * @param consignTime the consign_time value
	 */
	public void setConsignTime (java.util.Date consignTime) {
		this.consignTime = consignTime;
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
		if (!(obj instanceof com.sunmw.web.entity.TbPurchaseOrder)) return false;
		else {
			com.sunmw.web.entity.TbPurchaseOrder tbPurchaseOrder = (com.sunmw.web.entity.TbPurchaseOrder) obj;
			if (null == this.getId() || null == tbPurchaseOrder.getId()) return false;
			else return (this.getId().equals(tbPurchaseOrder.getId()));
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