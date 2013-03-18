package com.sunmw.paipai.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the pp_order_head table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="pp_order_head"
 */

public abstract class BasePpOrderHead  implements Serializable {

	public static String REF = "PpOrderHead";
	public static String PROP_DEAL_NOTE = "DealNote";
	public static String PROP_DEAL_STATE = "DealState";
	public static String PROP_RECVFEE_RETURN_TIME = "RecvfeeReturnTime";
	public static String PROP_INVOICE_TITLE = "InvoiceTitle";
	public static String PROP_DEAL_PAY_FEE_POINT = "DealPayFeePoint";
	public static String PROP_DEAL_PAY_TYPE_DESC = "DealPayTypeDesc";
	public static String PROP_BUYER_REMARK = "BuyerRemark";
	public static String PROP_DEAL_PAY_FEE_TOTAL = "DealPayFeeTotal";
	public static String PROP_TOTAL_CASH = "TotalCash";
	public static String PROP_PAY_TIME = "PayTime";
	public static String PROP_RECEIVER_MOBILE = "ReceiverMobile";
	public static String PROP_DEAL_NOTE_TYPE = "DealNoteType";
	public static String PROP_EXPECT_ARRIVAL_TIME = "ExpectArrivalTime";
	public static String PROP_SELLER_CRM = "SellerCrm";
	public static String PROP_DEAL_CODE = "DealCode";
	public static String PROP_RECVFEE_TIME = "RecvfeeTime";
	public static String PROP_FREIGHT = "Freight";
	public static String PROP_SELLER_NAME = "SellerName";
	public static String PROP_DEAL_TYPE_DESC = "DealTypeDesc";
	public static String PROP_SELLER_CONSIGNMENT_TIME = "SellerConsignmentTime";
	public static String PROP_COUPON_FEE = "CouponFee";
	public static String PROP_RECEIVER_POSTCODE = "ReceiverPostcode";
	public static String PROP_DEAL_STATE_DESC = "DealStateDesc";
	public static String PROP_DEAL_PAY_TYPE = "DealPayType";
	public static String PROP_DEAL_DETAIL_LINK = "DealDetailLink";
	public static String PROP_DEAL_DESC = "DealDesc";
	public static String PROP_RECEIVER_ADDRESS = "ReceiverAddress";
	public static String PROP_ID = "Id";
	public static String PROP_BUYER_NAME = "BuyerName";
	public static String PROP_SELLER_RECV_REFUND = "SellerRecvRefund";
	public static String PROP_INVOICE_CONTENT = "InvoiceContent";
	public static String PROP_TENPAY_CODE = "TenpayCode";
	public static String PROP_TRANSPORT_TYPE_DESC = "TransportTypeDesc";
	public static String PROP_PAY_RETURN_TIME = "PayReturnTime";
	public static String PROP_LAST_UPDATE_TIME = "LastUpdateTime";
	public static String PROP_WULIU_ID = "WuliuId";
	public static String PROP_DEAL_PAY_FEE_TICKET = "DealPayFeeTicket";
	public static String PROP_DEAL_RATE_STATE = "DealRateState";
	public static String PROP_TRANSPORT_TYPE = "TransportType";
	public static String PROP_WHO_PAY_SHIPPINGFEE = "WhoPayShippingfee";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_DEAL_END_TIME = "DealEndTime";
	public static String PROP_COMBO_INFO = "ComboInfo";
	public static String PROP_SELLER_UIN = "SellerUin";
	public static String PROP_SHIPPINGFEE_CALC = "ShippingfeeCalc";
	public static String PROP_HAS_INVOICE = "HasInvoice";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_BUYER_UIN = "BuyerUin";
	public static String PROP_RECEIVER_PHONE = "ReceiverPhone";
	public static String PROP_CREATE_TIME = "CreateTime";
	public static String PROP_PROPERTY_MASK = "PropertyMask";
	public static String PROP_DEAL_TYPE = "DealType";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_DEAL_RATE_STATE_DESC = "DealRateStateDesc";
	public static String PROP_BUYER_RECV_REFUND = "BuyerRecvRefund";
	public static String PROP_RECEIVER_NAME = "ReceiverName";


	// constructors
	public BasePpOrderHead () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePpOrderHead (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String sellerCrm;
	private java.lang.String buyerName;
	private java.lang.String buyerUin;
	private java.lang.String buyerRemark;
	private java.lang.String comboInfo;
	private java.lang.String dealCode;
	private java.lang.String dealDesc;
	private java.lang.String dealDetailLink;
	private java.lang.String dealNoteType;
	private java.lang.String dealNote;
	private java.lang.String dealState;
	private java.lang.String dealStateDesc;
	private java.lang.String propertyMask;
	private java.lang.String dealType;
	private java.lang.String dealTypeDesc;
	private java.lang.String dealPayType;
	private java.lang.String dealPayTypeDesc;
	private java.lang.String dealRateState;
	private java.lang.String dealRateStateDesc;
	private java.util.Date createTime;
	private java.util.Date dealEndTime;
	private java.util.Date lastUpdateTime;
	private java.util.Date payTime;
	private java.util.Date payReturnTime;
	private java.util.Date recvfeeReturnTime;
	private java.util.Date recvfeeTime;
	private java.util.Date sellerConsignmentTime;
	private java.lang.Integer hasInvoice;
	private java.lang.String invoiceContent;
	private java.lang.String invoiceTitle;
	private java.lang.String tenpayCode;
	private java.lang.String transportType;
	private java.lang.String transportTypeDesc;
	private java.lang.Integer whoPayShippingfee;
	private java.lang.String wuliuId;
	private java.lang.String receiverAddress;
	private java.lang.String receiverMobile;
	private java.lang.String receiverName;
	private java.lang.String receiverPhone;
	private java.lang.String receiverPostcode;
	private java.math.BigDecimal sellerRecvRefund;
	private java.math.BigDecimal buyerRecvRefund;
	private java.math.BigDecimal couponFee;
	private java.math.BigDecimal dealPayFeePoint;
	private java.math.BigDecimal dealPayFeeTicket;
	private java.math.BigDecimal dealPayFeeTotal;
	private java.math.BigDecimal freight;
	private java.math.BigDecimal shippingfeeCalc;
	private java.math.BigDecimal totalCash;
	private java.lang.String sellerName;
	private java.lang.String sellerUin;
	private java.util.Date expectArrivalTime;
	private java.lang.Integer storeId;
	private java.util.Date updateTime;
	private java.lang.String updateFlag;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="pp_order_head_id"
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
	 * Return the value associated with the column: seller_crm
	 */
	public java.lang.String getSellerCrm () {
		return sellerCrm;
	}

	/**
	 * Set the value related to the column: seller_crm
	 * @param sellerCrm the seller_crm value
	 */
	public void setSellerCrm (java.lang.String sellerCrm) {
		this.sellerCrm = sellerCrm;
	}



	/**
	 * Return the value associated with the column: buyer_name
	 */
	public java.lang.String getBuyerName () {
		return buyerName;
	}

	/**
	 * Set the value related to the column: buyer_name
	 * @param buyerName the buyer_name value
	 */
	public void setBuyerName (java.lang.String buyerName) {
		this.buyerName = buyerName;
	}



	/**
	 * Return the value associated with the column: buyer_uin
	 */
	public java.lang.String getBuyerUin () {
		return buyerUin;
	}

	/**
	 * Set the value related to the column: buyer_uin
	 * @param buyerUin the buyer_uin value
	 */
	public void setBuyerUin (java.lang.String buyerUin) {
		this.buyerUin = buyerUin;
	}



	/**
	 * Return the value associated with the column: buyer_remark
	 */
	public java.lang.String getBuyerRemark () {
		return buyerRemark;
	}

	/**
	 * Set the value related to the column: buyer_remark
	 * @param buyerRemark the buyer_remark value
	 */
	public void setBuyerRemark (java.lang.String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}



	/**
	 * Return the value associated with the column: combo_info
	 */
	public java.lang.String getComboInfo () {
		return comboInfo;
	}

	/**
	 * Set the value related to the column: combo_info
	 * @param comboInfo the combo_info value
	 */
	public void setComboInfo (java.lang.String comboInfo) {
		this.comboInfo = comboInfo;
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
	 * Return the value associated with the column: deal_desc
	 */
	public java.lang.String getDealDesc () {
		return dealDesc;
	}

	/**
	 * Set the value related to the column: deal_desc
	 * @param dealDesc the deal_desc value
	 */
	public void setDealDesc (java.lang.String dealDesc) {
		this.dealDesc = dealDesc;
	}



	/**
	 * Return the value associated with the column: deal_detail_link
	 */
	public java.lang.String getDealDetailLink () {
		return dealDetailLink;
	}

	/**
	 * Set the value related to the column: deal_detail_link
	 * @param dealDetailLink the deal_detail_link value
	 */
	public void setDealDetailLink (java.lang.String dealDetailLink) {
		this.dealDetailLink = dealDetailLink;
	}



	/**
	 * Return the value associated with the column: deal_note_type
	 */
	public java.lang.String getDealNoteType () {
		return dealNoteType;
	}

	/**
	 * Set the value related to the column: deal_note_type
	 * @param dealNoteType the deal_note_type value
	 */
	public void setDealNoteType (java.lang.String dealNoteType) {
		this.dealNoteType = dealNoteType;
	}



	/**
	 * Return the value associated with the column: deal_note
	 */
	public java.lang.String getDealNote () {
		return dealNote;
	}

	/**
	 * Set the value related to the column: deal_note
	 * @param dealNote the deal_note value
	 */
	public void setDealNote (java.lang.String dealNote) {
		this.dealNote = dealNote;
	}



	/**
	 * Return the value associated with the column: deal_state
	 */
	public java.lang.String getDealState () {
		return dealState;
	}

	/**
	 * Set the value related to the column: deal_state
	 * @param dealState the deal_state value
	 */
	public void setDealState (java.lang.String dealState) {
		this.dealState = dealState;
	}



	/**
	 * Return the value associated with the column: deal_state_desc
	 */
	public java.lang.String getDealStateDesc () {
		return dealStateDesc;
	}

	/**
	 * Set the value related to the column: deal_state_desc
	 * @param dealStateDesc the deal_state_desc value
	 */
	public void setDealStateDesc (java.lang.String dealStateDesc) {
		this.dealStateDesc = dealStateDesc;
	}



	/**
	 * Return the value associated with the column: property_mask
	 */
	public java.lang.String getPropertyMask () {
		return propertyMask;
	}

	/**
	 * Set the value related to the column: property_mask
	 * @param propertyMask the property_mask value
	 */
	public void setPropertyMask (java.lang.String propertyMask) {
		this.propertyMask = propertyMask;
	}



	/**
	 * Return the value associated with the column: deal_type
	 */
	public java.lang.String getDealType () {
		return dealType;
	}

	/**
	 * Set the value related to the column: deal_type
	 * @param dealType the deal_type value
	 */
	public void setDealType (java.lang.String dealType) {
		this.dealType = dealType;
	}



	/**
	 * Return the value associated with the column: deal_type_desc
	 */
	public java.lang.String getDealTypeDesc () {
		return dealTypeDesc;
	}

	/**
	 * Set the value related to the column: deal_type_desc
	 * @param dealTypeDesc the deal_type_desc value
	 */
	public void setDealTypeDesc (java.lang.String dealTypeDesc) {
		this.dealTypeDesc = dealTypeDesc;
	}



	/**
	 * Return the value associated with the column: deal_pay_type
	 */
	public java.lang.String getDealPayType () {
		return dealPayType;
	}

	/**
	 * Set the value related to the column: deal_pay_type
	 * @param dealPayType the deal_pay_type value
	 */
	public void setDealPayType (java.lang.String dealPayType) {
		this.dealPayType = dealPayType;
	}



	/**
	 * Return the value associated with the column: deal_pay_type_desc
	 */
	public java.lang.String getDealPayTypeDesc () {
		return dealPayTypeDesc;
	}

	/**
	 * Set the value related to the column: deal_pay_type_desc
	 * @param dealPayTypeDesc the deal_pay_type_desc value
	 */
	public void setDealPayTypeDesc (java.lang.String dealPayTypeDesc) {
		this.dealPayTypeDesc = dealPayTypeDesc;
	}



	/**
	 * Return the value associated with the column: deal_rate_state
	 */
	public java.lang.String getDealRateState () {
		return dealRateState;
	}

	/**
	 * Set the value related to the column: deal_rate_state
	 * @param dealRateState the deal_rate_state value
	 */
	public void setDealRateState (java.lang.String dealRateState) {
		this.dealRateState = dealRateState;
	}



	/**
	 * Return the value associated with the column: deal_rate_state_desc
	 */
	public java.lang.String getDealRateStateDesc () {
		return dealRateStateDesc;
	}

	/**
	 * Set the value related to the column: deal_rate_state_desc
	 * @param dealRateStateDesc the deal_rate_state_desc value
	 */
	public void setDealRateStateDesc (java.lang.String dealRateStateDesc) {
		this.dealRateStateDesc = dealRateStateDesc;
	}



	/**
	 * Return the value associated with the column: create_time
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: create_time
	 * @param createTime the create_time value
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}



	/**
	 * Return the value associated with the column: deal_end_time
	 */
	public java.util.Date getDealEndTime () {
		return dealEndTime;
	}

	/**
	 * Set the value related to the column: deal_end_time
	 * @param dealEndTime the deal_end_time value
	 */
	public void setDealEndTime (java.util.Date dealEndTime) {
		this.dealEndTime = dealEndTime;
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
	 * Return the value associated with the column: pay_return_time
	 */
	public java.util.Date getPayReturnTime () {
		return payReturnTime;
	}

	/**
	 * Set the value related to the column: pay_return_time
	 * @param payReturnTime the pay_return_time value
	 */
	public void setPayReturnTime (java.util.Date payReturnTime) {
		this.payReturnTime = payReturnTime;
	}



	/**
	 * Return the value associated with the column: recvfee_return_time
	 */
	public java.util.Date getRecvfeeReturnTime () {
		return recvfeeReturnTime;
	}

	/**
	 * Set the value related to the column: recvfee_return_time
	 * @param recvfeeReturnTime the recvfee_return_time value
	 */
	public void setRecvfeeReturnTime (java.util.Date recvfeeReturnTime) {
		this.recvfeeReturnTime = recvfeeReturnTime;
	}



	/**
	 * Return the value associated with the column: recvfee_time
	 */
	public java.util.Date getRecvfeeTime () {
		return recvfeeTime;
	}

	/**
	 * Set the value related to the column: recvfee_time
	 * @param recvfeeTime the recvfee_time value
	 */
	public void setRecvfeeTime (java.util.Date recvfeeTime) {
		this.recvfeeTime = recvfeeTime;
	}



	/**
	 * Return the value associated with the column: seller_consignment_time
	 */
	public java.util.Date getSellerConsignmentTime () {
		return sellerConsignmentTime;
	}

	/**
	 * Set the value related to the column: seller_consignment_time
	 * @param sellerConsignmentTime the seller_consignment_time value
	 */
	public void setSellerConsignmentTime (java.util.Date sellerConsignmentTime) {
		this.sellerConsignmentTime = sellerConsignmentTime;
	}



	/**
	 * Return the value associated with the column: has_invoice
	 */
	public java.lang.Integer getHasInvoice () {
		return hasInvoice;
	}

	/**
	 * Set the value related to the column: has_invoice
	 * @param hasInvoice the has_invoice value
	 */
	public void setHasInvoice (java.lang.Integer hasInvoice) {
		this.hasInvoice = hasInvoice;
	}



	/**
	 * Return the value associated with the column: invoice_content
	 */
	public java.lang.String getInvoiceContent () {
		return invoiceContent;
	}

	/**
	 * Set the value related to the column: invoice_content
	 * @param invoiceContent the invoice_content value
	 */
	public void setInvoiceContent (java.lang.String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}



	/**
	 * Return the value associated with the column: invoice_title
	 */
	public java.lang.String getInvoiceTitle () {
		return invoiceTitle;
	}

	/**
	 * Set the value related to the column: invoice_title
	 * @param invoiceTitle the invoice_title value
	 */
	public void setInvoiceTitle (java.lang.String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}



	/**
	 * Return the value associated with the column: tenpay_code
	 */
	public java.lang.String getTenpayCode () {
		return tenpayCode;
	}

	/**
	 * Set the value related to the column: tenpay_code
	 * @param tenpayCode the tenpay_code value
	 */
	public void setTenpayCode (java.lang.String tenpayCode) {
		this.tenpayCode = tenpayCode;
	}



	/**
	 * Return the value associated with the column: transport_type
	 */
	public java.lang.String getTransportType () {
		return transportType;
	}

	/**
	 * Set the value related to the column: transport_type
	 * @param transportType the transport_type value
	 */
	public void setTransportType (java.lang.String transportType) {
		this.transportType = transportType;
	}



	/**
	 * Return the value associated with the column: transport_type_desc
	 */
	public java.lang.String getTransportTypeDesc () {
		return transportTypeDesc;
	}

	/**
	 * Set the value related to the column: transport_type_desc
	 * @param transportTypeDesc the transport_type_desc value
	 */
	public void setTransportTypeDesc (java.lang.String transportTypeDesc) {
		this.transportTypeDesc = transportTypeDesc;
	}



	/**
	 * Return the value associated with the column: who_pay_shippingfee
	 */
	public java.lang.Integer getWhoPayShippingfee () {
		return whoPayShippingfee;
	}

	/**
	 * Set the value related to the column: who_pay_shippingfee
	 * @param whoPayShippingfee the who_pay_shippingfee value
	 */
	public void setWhoPayShippingfee (java.lang.Integer whoPayShippingfee) {
		this.whoPayShippingfee = whoPayShippingfee;
	}



	/**
	 * Return the value associated with the column: wuliu_id
	 */
	public java.lang.String getWuliuId () {
		return wuliuId;
	}

	/**
	 * Set the value related to the column: wuliu_id
	 * @param wuliuId the wuliu_id value
	 */
	public void setWuliuId (java.lang.String wuliuId) {
		this.wuliuId = wuliuId;
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
	 * Return the value associated with the column: receiver_mobile
	 */
	public java.lang.String getReceiverMobile () {
		return receiverMobile;
	}

	/**
	 * Set the value related to the column: receiver_mobile
	 * @param receiverMobile the receiver_mobile value
	 */
	public void setReceiverMobile (java.lang.String receiverMobile) {
		this.receiverMobile = receiverMobile;
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
	 * Return the value associated with the column: receiver_postcode
	 */
	public java.lang.String getReceiverPostcode () {
		return receiverPostcode;
	}

	/**
	 * Set the value related to the column: receiver_postcode
	 * @param receiverPostcode the receiver_postcode value
	 */
	public void setReceiverPostcode (java.lang.String receiverPostcode) {
		this.receiverPostcode = receiverPostcode;
	}



	/**
	 * Return the value associated with the column: seller_recv_refund
	 */
	public java.math.BigDecimal getSellerRecvRefund () {
		return sellerRecvRefund;
	}

	/**
	 * Set the value related to the column: seller_recv_refund
	 * @param sellerRecvRefund the seller_recv_refund value
	 */
	public void setSellerRecvRefund (java.math.BigDecimal sellerRecvRefund) {
		this.sellerRecvRefund = sellerRecvRefund;
	}



	/**
	 * Return the value associated with the column: buyer_recv_refund
	 */
	public java.math.BigDecimal getBuyerRecvRefund () {
		return buyerRecvRefund;
	}

	/**
	 * Set the value related to the column: buyer_recv_refund
	 * @param buyerRecvRefund the buyer_recv_refund value
	 */
	public void setBuyerRecvRefund (java.math.BigDecimal buyerRecvRefund) {
		this.buyerRecvRefund = buyerRecvRefund;
	}



	/**
	 * Return the value associated with the column: coupon_fee
	 */
	public java.math.BigDecimal getCouponFee () {
		return couponFee;
	}

	/**
	 * Set the value related to the column: coupon_fee
	 * @param couponFee the coupon_fee value
	 */
	public void setCouponFee (java.math.BigDecimal couponFee) {
		this.couponFee = couponFee;
	}



	/**
	 * Return the value associated with the column: deal_pay_fee_point
	 */
	public java.math.BigDecimal getDealPayFeePoint () {
		return dealPayFeePoint;
	}

	/**
	 * Set the value related to the column: deal_pay_fee_point
	 * @param dealPayFeePoint the deal_pay_fee_point value
	 */
	public void setDealPayFeePoint (java.math.BigDecimal dealPayFeePoint) {
		this.dealPayFeePoint = dealPayFeePoint;
	}



	/**
	 * Return the value associated with the column: deal_pay_fee_ticket
	 */
	public java.math.BigDecimal getDealPayFeeTicket () {
		return dealPayFeeTicket;
	}

	/**
	 * Set the value related to the column: deal_pay_fee_ticket
	 * @param dealPayFeeTicket the deal_pay_fee_ticket value
	 */
	public void setDealPayFeeTicket (java.math.BigDecimal dealPayFeeTicket) {
		this.dealPayFeeTicket = dealPayFeeTicket;
	}



	/**
	 * Return the value associated with the column: deal_pay_fee_total
	 */
	public java.math.BigDecimal getDealPayFeeTotal () {
		return dealPayFeeTotal;
	}

	/**
	 * Set the value related to the column: deal_pay_fee_total
	 * @param dealPayFeeTotal the deal_pay_fee_total value
	 */
	public void setDealPayFeeTotal (java.math.BigDecimal dealPayFeeTotal) {
		this.dealPayFeeTotal = dealPayFeeTotal;
	}



	/**
	 * Return the value associated with the column: freight
	 */
	public java.math.BigDecimal getFreight () {
		return freight;
	}

	/**
	 * Set the value related to the column: freight
	 * @param freight the freight value
	 */
	public void setFreight (java.math.BigDecimal freight) {
		this.freight = freight;
	}



	/**
	 * Return the value associated with the column: shippingfee_calc
	 */
	public java.math.BigDecimal getShippingfeeCalc () {
		return shippingfeeCalc;
	}

	/**
	 * Set the value related to the column: shippingfee_calc
	 * @param shippingfeeCalc the shippingfee_calc value
	 */
	public void setShippingfeeCalc (java.math.BigDecimal shippingfeeCalc) {
		this.shippingfeeCalc = shippingfeeCalc;
	}



	/**
	 * Return the value associated with the column: total_cash
	 */
	public java.math.BigDecimal getTotalCash () {
		return totalCash;
	}

	/**
	 * Set the value related to the column: total_cash
	 * @param totalCash the total_cash value
	 */
	public void setTotalCash (java.math.BigDecimal totalCash) {
		this.totalCash = totalCash;
	}



	/**
	 * Return the value associated with the column: seller_name
	 */
	public java.lang.String getSellerName () {
		return sellerName;
	}

	/**
	 * Set the value related to the column: seller_name
	 * @param sellerName the seller_name value
	 */
	public void setSellerName (java.lang.String sellerName) {
		this.sellerName = sellerName;
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
	 * Return the value associated with the column: expect_arrival_time
	 */
	public java.util.Date getExpectArrivalTime () {
		return expectArrivalTime;
	}

	/**
	 * Set the value related to the column: expect_arrival_time
	 * @param expectArrivalTime the expect_arrival_time value
	 */
	public void setExpectArrivalTime (java.util.Date expectArrivalTime) {
		this.expectArrivalTime = expectArrivalTime;
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
		if (!(obj instanceof com.sunmw.paipai.entity.PpOrderHead)) return false;
		else {
			com.sunmw.paipai.entity.PpOrderHead ppOrderHead = (com.sunmw.paipai.entity.PpOrderHead) obj;
			if (null == this.getId() || null == ppOrderHead.getId()) return false;
			else return (this.getId().equals(ppOrderHead.getId()));
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