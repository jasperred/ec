package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the order_head table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="order_head"
 */

public abstract class BaseOrderHead  implements Serializable {

	public static String REF = "OrderHead";
	public static String PROP_RECEIVE_FIELD3 = "ReceiveField3";
	public static String PROP_TOTAL_FEE = "TotalFee";
	public static String PROP_RECEIVE_FIELD4 = "ReceiveField4";
	public static String PROP_RECEIVE_FIELD5 = "ReceiveField5";
	public static String PROP_RECEIVE_FIELD6 = "ReceiveField6";
	public static String PROP_ORDER_STATUS = "OrderStatus";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_RECEIVE_FIELD1 = "ReceiveField1";
	public static String PROP_RECEIVE_FIELD2 = "ReceiveField2";
	public static String PROP_ORIGIN = "Origin";
	public static String PROP_INVOICE_TITLE = "InvoiceTitle";
	public static String PROP_ORDER_AMT = "OrderAmt";
	public static String PROP_PAYMENT = "Payment";
	public static String PROP_CUST_ID = "CustId";
	public static String PROP_BUYER_NICK = "BuyerNick";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_PAY_TIME = "PayTime";
	public static String PROP_ORDER_NO = "OrderNo";
	public static String PROP_ORIG_ORDER_TYPE = "OrigOrderType";
	public static String PROP_RECEIVER_MOBILE = "ReceiverMobile";
	public static String PROP_BUYER_EMAIL = "BuyerEmail";
	public static String PROP_DISCOUNT = "Discount";
	public static String PROP_COMPLETE_DATE = "CompleteDate";
	public static String PROP_RECEIVER_TEL = "ReceiverTel";
	public static String PROP_DELIVERY_DATE = "DeliveryDate";
	public static String PROP_SELLER_NICK = "SellerNick";
	public static String PROP_POST_NO = "PostNo";
	public static String PROP_POST_METHOD = "PostMethod";
	public static String PROP_RECEIVER_ADDRESS = "ReceiverAddress";
	public static String PROP_INVOICE_TYPE = "InvoiceType";
	public static String PROP_OBTAIN_POINT = "ObtainPoint";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_RECEIVER_ZIP = "ReceiverZip";
	public static String PROP_ORIG_ORDER_NO = "OrigOrderNo";
	public static String PROP_ORIG_ORDER_STATUS = "OrigOrderStatus";
	public static String PROP_INVOICE_CONTENT = "InvoiceContent";
	public static String PROP_REQUEST_DATE = "RequestDate";
	public static String PROP_REF_ORDER_ID = "RefOrderId";
	public static String PROP_RECEIVER_DISTRICT = "ReceiverDistrict";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_ORDER_TYPE = "OrderType";
	public static String PROP_RECEIVER_CITY = "ReceiverCity";
	public static String PROP_PAYMENT_POINT = "PaymentPoint";
	public static String PROP_POST_FEE = "PostFee";
	public static String PROP_ORDER_DATE = "OrderDate";
	public static String PROP_REF_ORDER_TYPE = "RefOrderType";
	public static String PROP_RECEIVER_STATE = "ReceiverState";
	public static String PROP_REF_ORDER_NO = "RefOrderNo";
	public static String PROP_PAY_NO = "PayNo";
	public static String PROP_INVOICE_ID = "InvoiceId";
	public static String PROP_INV_STATUS = "InvStatus";
	public static String PROP_POST_COMPANY = "PostCompany";
	public static String PROP_POST_NOS = "PostNos";
	public static String PROP_RECEIVER_NAME = "ReceiverName";


	// constructors
	public BaseOrderHead () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrderHead (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String orderNo;
	private java.lang.String orderType;
	private java.lang.String origOrderNo;
	private java.lang.String origOrderType;
	private java.lang.Integer refOrderId;
	private java.lang.String refOrderType;
	private java.lang.Integer storeId;
	private java.lang.String origOrderStatus;
	private java.lang.String orderStatus;
	private java.util.Date orderDate;
	private java.util.Date requestDate;
	private java.util.Date deliveryDate;
	private java.util.Date completeDate;
	private java.lang.Integer invoiceId;
	private java.math.BigDecimal orderAmt;
	private java.lang.String receiveField1;
	private java.lang.String receiveField2;
	private java.lang.String receiveField3;
	private java.lang.String receiveField4;
	private java.lang.String receiveField5;
	private java.lang.String receiveField6;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String refOrderNo;
	private java.lang.String origin;
	private java.lang.String invStatus;
	private java.lang.Integer companyId;
	private java.math.BigDecimal payment;
	private java.math.BigDecimal totalFee;
	private java.math.BigDecimal postFee;
	private java.math.BigDecimal discount;
	private java.lang.Integer paymentPoint;
	private java.lang.Integer obtainPoint;
	private java.lang.Integer custId;
	private java.lang.String buyerNick;
	private java.lang.String buyerEmail;
	private java.lang.String postMethod;
	private java.lang.String postCompany;
	private java.lang.String postNo;
	private java.lang.String receiverState;
	private java.lang.String receiverCity;
	private java.lang.String receiverDistrict;
	private java.lang.String receiverAddress;
	private java.lang.String receiverName;
	private java.lang.String receiverMobile;
	private java.lang.String receiverTel;
	private java.lang.String sellerNick;
	private java.lang.String invoiceType;
	private java.lang.String invoiceTitle;
	private java.lang.String invoiceContent;
	private java.lang.String receiverZip;
	private java.lang.String postNos;
	private java.util.Date payTime;
	private java.lang.String payNo;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="order_head_id"
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
	 * Return the value associated with the column: order_no
	 */
	public java.lang.String getOrderNo () {
		return orderNo;
	}

	/**
	 * Set the value related to the column: order_no
	 * @param orderNo the order_no value
	 */
	public void setOrderNo (java.lang.String orderNo) {
		this.orderNo = orderNo;
	}



	/**
	 * Return the value associated with the column: order_type
	 */
	public java.lang.String getOrderType () {
		return orderType;
	}

	/**
	 * Set the value related to the column: order_type
	 * @param orderType the order_type value
	 */
	public void setOrderType (java.lang.String orderType) {
		this.orderType = orderType;
	}



	/**
	 * Return the value associated with the column: orig_order_no
	 */
	public java.lang.String getOrigOrderNo () {
		return origOrderNo;
	}

	/**
	 * Set the value related to the column: orig_order_no
	 * @param origOrderNo the orig_order_no value
	 */
	public void setOrigOrderNo (java.lang.String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}



	/**
	 * Return the value associated with the column: orig_order_type
	 */
	public java.lang.String getOrigOrderType () {
		return origOrderType;
	}

	/**
	 * Set the value related to the column: orig_order_type
	 * @param origOrderType the orig_order_type value
	 */
	public void setOrigOrderType (java.lang.String origOrderType) {
		this.origOrderType = origOrderType;
	}



	/**
	 * Return the value associated with the column: ref_order_id
	 */
	public java.lang.Integer getRefOrderId () {
		return refOrderId;
	}

	/**
	 * Set the value related to the column: ref_order_id
	 * @param refOrderId the ref_order_id value
	 */
	public void setRefOrderId (java.lang.Integer refOrderId) {
		this.refOrderId = refOrderId;
	}



	/**
	 * Return the value associated with the column: ref_order_type
	 */
	public java.lang.String getRefOrderType () {
		return refOrderType;
	}

	/**
	 * Set the value related to the column: ref_order_type
	 * @param refOrderType the ref_order_type value
	 */
	public void setRefOrderType (java.lang.String refOrderType) {
		this.refOrderType = refOrderType;
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
	 * Return the value associated with the column: orig_order_status
	 */
	public java.lang.String getOrigOrderStatus () {
		return origOrderStatus;
	}

	/**
	 * Set the value related to the column: orig_order_status
	 * @param origOrderStatus the orig_order_status value
	 */
	public void setOrigOrderStatus (java.lang.String origOrderStatus) {
		this.origOrderStatus = origOrderStatus;
	}



	/**
	 * Return the value associated with the column: order_status
	 */
	public java.lang.String getOrderStatus () {
		return orderStatus;
	}

	/**
	 * Set the value related to the column: order_status
	 * @param orderStatus the order_status value
	 */
	public void setOrderStatus (java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}



	/**
	 * Return the value associated with the column: order_date
	 */
	public java.util.Date getOrderDate () {
		return orderDate;
	}

	/**
	 * Set the value related to the column: order_date
	 * @param orderDate the order_date value
	 */
	public void setOrderDate (java.util.Date orderDate) {
		this.orderDate = orderDate;
	}



	/**
	 * Return the value associated with the column: request_date
	 */
	public java.util.Date getRequestDate () {
		return requestDate;
	}

	/**
	 * Set the value related to the column: request_date
	 * @param requestDate the request_date value
	 */
	public void setRequestDate (java.util.Date requestDate) {
		this.requestDate = requestDate;
	}



	/**
	 * Return the value associated with the column: delivery_date
	 */
	public java.util.Date getDeliveryDate () {
		return deliveryDate;
	}

	/**
	 * Set the value related to the column: delivery_date
	 * @param deliveryDate the delivery_date value
	 */
	public void setDeliveryDate (java.util.Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}



	/**
	 * Return the value associated with the column: complete_date
	 */
	public java.util.Date getCompleteDate () {
		return completeDate;
	}

	/**
	 * Set the value related to the column: complete_date
	 * @param completeDate the complete_date value
	 */
	public void setCompleteDate (java.util.Date completeDate) {
		this.completeDate = completeDate;
	}



	/**
	 * Return the value associated with the column: invoice_id
	 */
	public java.lang.Integer getInvoiceId () {
		return invoiceId;
	}

	/**
	 * Set the value related to the column: invoice_id
	 * @param invoiceId the invoice_id value
	 */
	public void setInvoiceId (java.lang.Integer invoiceId) {
		this.invoiceId = invoiceId;
	}



	/**
	 * Return the value associated with the column: order_amt
	 */
	public java.math.BigDecimal getOrderAmt () {
		return orderAmt;
	}

	/**
	 * Set the value related to the column: order_amt
	 * @param orderAmt the order_amt value
	 */
	public void setOrderAmt (java.math.BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}



	/**
	 * Return the value associated with the column: receive_field1
	 */
	public java.lang.String getReceiveField1 () {
		return receiveField1;
	}

	/**
	 * Set the value related to the column: receive_field1
	 * @param receiveField1 the receive_field1 value
	 */
	public void setReceiveField1 (java.lang.String receiveField1) {
		this.receiveField1 = receiveField1;
	}



	/**
	 * Return the value associated with the column: receive_field2
	 */
	public java.lang.String getReceiveField2 () {
		return receiveField2;
	}

	/**
	 * Set the value related to the column: receive_field2
	 * @param receiveField2 the receive_field2 value
	 */
	public void setReceiveField2 (java.lang.String receiveField2) {
		this.receiveField2 = receiveField2;
	}



	/**
	 * Return the value associated with the column: receive_field3
	 */
	public java.lang.String getReceiveField3 () {
		return receiveField3;
	}

	/**
	 * Set the value related to the column: receive_field3
	 * @param receiveField3 the receive_field3 value
	 */
	public void setReceiveField3 (java.lang.String receiveField3) {
		this.receiveField3 = receiveField3;
	}



	/**
	 * Return the value associated with the column: receive_field4
	 */
	public java.lang.String getReceiveField4 () {
		return receiveField4;
	}

	/**
	 * Set the value related to the column: receive_field4
	 * @param receiveField4 the receive_field4 value
	 */
	public void setReceiveField4 (java.lang.String receiveField4) {
		this.receiveField4 = receiveField4;
	}



	/**
	 * Return the value associated with the column: receive_field5
	 */
	public java.lang.String getReceiveField5 () {
		return receiveField5;
	}

	/**
	 * Set the value related to the column: receive_field5
	 * @param receiveField5 the receive_field5 value
	 */
	public void setReceiveField5 (java.lang.String receiveField5) {
		this.receiveField5 = receiveField5;
	}



	/**
	 * Return the value associated with the column: receive_field6
	 */
	public java.lang.String getReceiveField6 () {
		return receiveField6;
	}

	/**
	 * Set the value related to the column: receive_field6
	 * @param receiveField6 the receive_field6 value
	 */
	public void setReceiveField6 (java.lang.String receiveField6) {
		this.receiveField6 = receiveField6;
	}



	/**
	 * Return the value associated with the column: ctime
	 */
	public java.util.Date getCtime () {
		return ctime;
	}

	/**
	 * Set the value related to the column: ctime
	 * @param ctime the ctime value
	 */
	public void setCtime (java.util.Date ctime) {
		this.ctime = ctime;
	}



	/**
	 * Return the value associated with the column: mtime
	 */
	public java.util.Date getMtime () {
		return mtime;
	}

	/**
	 * Set the value related to the column: mtime
	 * @param mtime the mtime value
	 */
	public void setMtime (java.util.Date mtime) {
		this.mtime = mtime;
	}



	/**
	 * Return the value associated with the column: ref_order_no
	 */
	public java.lang.String getRefOrderNo () {
		return refOrderNo;
	}

	/**
	 * Set the value related to the column: ref_order_no
	 * @param refOrderNo the ref_order_no value
	 */
	public void setRefOrderNo (java.lang.String refOrderNo) {
		this.refOrderNo = refOrderNo;
	}



	/**
	 * Return the value associated with the column: origin
	 */
	public java.lang.String getOrigin () {
		return origin;
	}

	/**
	 * Set the value related to the column: origin
	 * @param origin the origin value
	 */
	public void setOrigin (java.lang.String origin) {
		this.origin = origin;
	}



	/**
	 * Return the value associated with the column: inv_status
	 */
	public java.lang.String getInvStatus () {
		return invStatus;
	}

	/**
	 * Set the value related to the column: inv_status
	 * @param invStatus the inv_status value
	 */
	public void setInvStatus (java.lang.String invStatus) {
		this.invStatus = invStatus;
	}



	/**
	 * Return the value associated with the column: company_id
	 */
	public java.lang.Integer getCompanyId () {
		return companyId;
	}

	/**
	 * Set the value related to the column: company_id
	 * @param companyId the company_id value
	 */
	public void setCompanyId (java.lang.Integer companyId) {
		this.companyId = companyId;
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
	 * Return the value associated with the column: discount
	 */
	public java.math.BigDecimal getDiscount () {
		return discount;
	}

	/**
	 * Set the value related to the column: discount
	 * @param discount the discount value
	 */
	public void setDiscount (java.math.BigDecimal discount) {
		this.discount = discount;
	}



	/**
	 * Return the value associated with the column: payment_point
	 */
	public java.lang.Integer getPaymentPoint () {
		return paymentPoint;
	}

	/**
	 * Set the value related to the column: payment_point
	 * @param paymentPoint the payment_point value
	 */
	public void setPaymentPoint (java.lang.Integer paymentPoint) {
		this.paymentPoint = paymentPoint;
	}



	/**
	 * Return the value associated with the column: obtain_point
	 */
	public java.lang.Integer getObtainPoint () {
		return obtainPoint;
	}

	/**
	 * Set the value related to the column: obtain_point
	 * @param obtainPoint the obtain_point value
	 */
	public void setObtainPoint (java.lang.Integer obtainPoint) {
		this.obtainPoint = obtainPoint;
	}



	/**
	 * Return the value associated with the column: cust_id
	 */
	public java.lang.Integer getCustId () {
		return custId;
	}

	/**
	 * Set the value related to the column: cust_id
	 * @param custId the cust_id value
	 */
	public void setCustId (java.lang.Integer custId) {
		this.custId = custId;
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
	 * Return the value associated with the column: buyer_email
	 */
	public java.lang.String getBuyerEmail () {
		return buyerEmail;
	}

	/**
	 * Set the value related to the column: buyer_email
	 * @param buyerEmail the buyer_email value
	 */
	public void setBuyerEmail (java.lang.String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}



	/**
	 * Return the value associated with the column: post_method
	 */
	public java.lang.String getPostMethod () {
		return postMethod;
	}

	/**
	 * Set the value related to the column: post_method
	 * @param postMethod the post_method value
	 */
	public void setPostMethod (java.lang.String postMethod) {
		this.postMethod = postMethod;
	}



	/**
	 * Return the value associated with the column: post_company
	 */
	public java.lang.String getPostCompany () {
		return postCompany;
	}

	/**
	 * Set the value related to the column: post_company
	 * @param postCompany the post_company value
	 */
	public void setPostCompany (java.lang.String postCompany) {
		this.postCompany = postCompany;
	}



	/**
	 * Return the value associated with the column: post_no
	 */
	public java.lang.String getPostNo () {
		return postNo;
	}

	/**
	 * Set the value related to the column: post_no
	 * @param postNo the post_no value
	 */
	public void setPostNo (java.lang.String postNo) {
		this.postNo = postNo;
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
	 * Return the value associated with the column: receiver_tel
	 */
	public java.lang.String getReceiverTel () {
		return receiverTel;
	}

	/**
	 * Set the value related to the column: receiver_tel
	 * @param receiverTel the receiver_tel value
	 */
	public void setReceiverTel (java.lang.String receiverTel) {
		this.receiverTel = receiverTel;
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
	 * Return the value associated with the column: invoice_type
	 */
	public java.lang.String getInvoiceType () {
		return invoiceType;
	}

	/**
	 * Set the value related to the column: invoice_type
	 * @param invoiceType the invoice_type value
	 */
	public void setInvoiceType (java.lang.String invoiceType) {
		this.invoiceType = invoiceType;
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
	 * Return the value associated with the column: post_nos
	 */
	public java.lang.String getPostNos () {
		return postNos;
	}

	/**
	 * Set the value related to the column: post_nos
	 * @param postNos the post_nos value
	 */
	public void setPostNos (java.lang.String postNos) {
		this.postNos = postNos;
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
	 * Return the value associated with the column: pay_no
	 */
	public java.lang.String getPayNo () {
		return payNo;
	}

	/**
	 * Set the value related to the column: pay_no
	 * @param payNo the pay_no value
	 */
	public void setPayNo (java.lang.String payNo) {
		this.payNo = payNo;
	}



	/**
	 * Return the value associated with the column: cuser
	 */
	public java.lang.String getCuser () {
		return cuser;
	}

	/**
	 * Set the value related to the column: cuser
	 * @param cuser the cuser value
	 */
	public void setCuser (java.lang.String cuser) {
		this.cuser = cuser;
	}



	/**
	 * Return the value associated with the column: muser
	 */
	public java.lang.String getMuser () {
		return muser;
	}

	/**
	 * Set the value related to the column: muser
	 * @param muser the muser value
	 */
	public void setMuser (java.lang.String muser) {
		this.muser = muser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.OrderHead)) return false;
		else {
			com.sunmw.web.entity.OrderHead orderHead = (com.sunmw.web.entity.OrderHead) obj;
			if (null == this.getId() || null == orderHead.getId()) return false;
			else return (this.getId().equals(orderHead.getId()));
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