package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the order_taobaoinfo table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="order_taobaoinfo"
 */

public abstract class BaseOrderTaobaoinfo  implements Serializable {

	public static String REF = "OrderTaobaoinfo";
	public static String PROP_TOTAL_FEE = "TotalFee";
	public static String PROP_PROMOTION = "Promotion";
	public static String PROP_SELLER_RATE = "SellerRate";
	public static String PROP_SELLER_PHONE = "SellerPhone";
	public static String PROP_PAYMENT = "Payment";
	public static String PROP_BUYER_NICK = "BuyerNick";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_PIC_PATH = "PicPath";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_PAY_TIME = "PayTime";
	public static String PROP_BUYER_RATE = "BuyerRate";
	public static String PROP_CONSIGN_TIME = "ConsignTime";
	public static String PROP_RECEIVER_MOBILE = "ReceiverMobile";
	public static String PROP_AVAILABLE_CONFIRM_FEE = "AvailableConfirmFee";
	public static String PROP_INVOICE_NAME = "InvoiceName";
	public static String PROP_BUYER_EMAIL = "BuyerEmail";
	public static String PROP_SELLER_EMAIL = "SellerEmail";
	public static String PROP_ADJUST_FEE = "AdjustFee";
	public static String PROP_DISCOUNT_FEE = "DiscountFee";
	public static String PROP_SELLER_NICK = "SellerNick";
	public static String PROP_NUM = "Num";
	public static String PROP_ALIPAY_URL = "AlipayUrl";
	public static String PROP_SELLER_NAME = "SellerName";
	public static String PROP_ALIPAY_NO = "AlipayNo";
	public static String PROP_SHIPPING_TYPE = "ShippingType";
	public static String PROP_BUYER_ALIPAY_NO = "BuyerAlipayNo";
	public static String PROP_SELLER_MEMO = "SellerMemo";
	public static String PROP_RECEIVER_ADDRESS = "ReceiverAddress";
	public static String PROP_SNAPSHOT_URL = "SnapshotUrl";
	public static String PROP_COMMISSION_FEE = "CommissionFee";
	public static String PROP_PRICE = "Price";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_RECEIVED_PAYMENT = "ReceivedPayment";
	public static String PROP_TRADE_MEMO = "TradeMemo";
	public static String PROP_RECEIVER_ZIP = "ReceiverZip";
	public static String PROP_TIMEOUT_ACTION_TIME = "TimeoutActionTime";
	public static String PROP_COD_STATUS = "CodStatus";
	public static String PROP_SELLER_MOBILE = "SellerMobile";
	public static String PROP_RECEIVER_DISTRICT = "ReceiverDistrict";
	public static String PROP_IS3D = "Is3d";
	public static String PROP_SNAPSHOT = "Snapshot";
	public static String PROP_BUYER_MESSAGE = "BuyerMessage";
	public static String PROP_CREATED = "Created";
	public static String PROP_SELLER_FLAG = "SellerFlag";
	public static String PROP_BUYER_MEMO = "BuyerMemo";
	public static String PROP_RECEIVER_CITY = "ReceiverCity";
	public static String PROP_REAL_POINT_FEE = "RealPointFee";
	public static String PROP_POST_FEE = "PostFee";
	public static String PROP_TRADE_FROM = "TradeFrom";
	public static String PROP_BUYER_OBTAIN_POINT_FEE = "BuyerObtainPointFee";
	public static String PROP_SELLER_ALIPAY_NO = "SellerAlipayNo";
	public static String PROP_BUYER_FLAG = "BuyerFlag";
	public static String PROP_RECEIVER_STATE = "ReceiverState";
	public static String PROP_COD_FEE = "CodFee";
	public static String PROP_HAS_POST_FEE = "HasPostFee";
	public static String PROP_RECEIVER_PHONE = "ReceiverPhone";
	public static String PROP_TITLE = "Title";
	public static String PROP_STATUS = "Status";
	public static String PROP_POINT_FEE = "PointFee";
	public static String PROP_IID = "Iid";
	public static String PROP_END_TIME = "EndTime";
	public static String PROP_RECEIVER_NAME = "ReceiverName";


	// constructors
	public BaseOrderTaobaoinfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrderTaobaoinfo (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.util.Date endTime;
	private java.lang.String sellerNick;
	private java.lang.String buyerNick;
	private java.lang.String title;
	private java.util.Date created;
	private java.lang.String iid;
	private java.math.BigDecimal price;
	private java.lang.String picPath;
	private java.math.BigDecimal num;
	private java.lang.String buyerMessage;
	private java.lang.String shippingType;
	private java.lang.String alipayNo;
	private java.math.BigDecimal payment;
	private java.math.BigDecimal discountFee;
	private java.math.BigDecimal adjustFee;
	private java.lang.String snapshotUrl;
	private java.lang.String snapshot;
	private java.lang.String status;
	private boolean sellerRate;
	private boolean buyerRate;
	private java.lang.String buyerMemo;
	private java.lang.String sellerMemo;
	private java.lang.String tradeMemo;
	private java.util.Date payTime;
	private java.util.Date modified;
	private java.math.BigDecimal buyerObtainPointFee;
	private java.math.BigDecimal pointFee;
	private java.math.BigDecimal realPointFee;
	private java.math.BigDecimal totalFee;
	private java.math.BigDecimal postFee;
	private java.lang.String buyerAlipayNo;
	private java.lang.String receiverName;
	private java.lang.String receiverState;
	private java.lang.String receiverCity;
	private java.lang.String receiverDistrict;
	private java.lang.String receiverAddress;
	private java.lang.String receiverZip;
	private java.lang.String receiverMobile;
	private java.lang.String receiverPhone;
	private java.util.Date consignTime;
	private java.lang.String buyerEmail;
	private java.math.BigDecimal commissionFee;
	private java.lang.String sellerAlipayNo;
	private java.lang.String sellerMobile;
	private java.lang.String sellerPhone;
	private java.lang.String sellerName;
	private java.lang.String sellerEmail;
	private java.math.BigDecimal availableConfirmFee;
	private boolean hasPostFee;
	private java.math.BigDecimal receivedPayment;
	private java.math.BigDecimal codFee;
	private java.lang.String codStatus;
	private java.util.Date timeoutActionTime;
	private boolean is3d;
	private java.lang.Integer buyerFlag;
	private java.lang.Integer sellerFlag;
	private java.lang.String promotion;
	private java.lang.String invoiceName;
	private java.lang.String tradeFrom;
	private java.lang.String alipayUrl;
	private java.util.Date ctime;
	private java.util.Date mtime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: buyer_message
	 */
	public java.lang.String getBuyerMessage () {
		return buyerMessage;
	}

	/**
	 * Set the value related to the column: buyer_message
	 * @param buyerMessage the buyer_message value
	 */
	public void setBuyerMessage (java.lang.String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}



	/**
	 * Return the value associated with the column: shipping_type
	 */
	public java.lang.String getShippingType () {
		return shippingType;
	}

	/**
	 * Set the value related to the column: shipping_type
	 * @param shippingType the shipping_type value
	 */
	public void setShippingType (java.lang.String shippingType) {
		this.shippingType = shippingType;
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
	 * Return the value associated with the column: buyer_memo
	 */
	public java.lang.String getBuyerMemo () {
		return buyerMemo;
	}

	/**
	 * Set the value related to the column: buyer_memo
	 * @param buyerMemo the buyer_memo value
	 */
	public void setBuyerMemo (java.lang.String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}



	/**
	 * Return the value associated with the column: seller_memo
	 */
	public java.lang.String getSellerMemo () {
		return sellerMemo;
	}

	/**
	 * Set the value related to the column: seller_memo
	 * @param sellerMemo the seller_memo value
	 */
	public void setSellerMemo (java.lang.String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}



	/**
	 * Return the value associated with the column: trade_memo
	 */
	public java.lang.String getTradeMemo () {
		return tradeMemo;
	}

	/**
	 * Set the value related to the column: trade_memo
	 * @param tradeMemo the trade_memo value
	 */
	public void setTradeMemo (java.lang.String tradeMemo) {
		this.tradeMemo = tradeMemo;
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
	 * Return the value associated with the column: buyer_obtain_point_fee
	 */
	public java.math.BigDecimal getBuyerObtainPointFee () {
		return buyerObtainPointFee;
	}

	/**
	 * Set the value related to the column: buyer_obtain_point_fee
	 * @param buyerObtainPointFee the buyer_obtain_point_fee value
	 */
	public void setBuyerObtainPointFee (java.math.BigDecimal buyerObtainPointFee) {
		this.buyerObtainPointFee = buyerObtainPointFee;
	}



	/**
	 * Return the value associated with the column: point_fee
	 */
	public java.math.BigDecimal getPointFee () {
		return pointFee;
	}

	/**
	 * Set the value related to the column: point_fee
	 * @param pointFee the point_fee value
	 */
	public void setPointFee (java.math.BigDecimal pointFee) {
		this.pointFee = pointFee;
	}



	/**
	 * Return the value associated with the column: real_point_fee
	 */
	public java.math.BigDecimal getRealPointFee () {
		return realPointFee;
	}

	/**
	 * Set the value related to the column: real_point_fee
	 * @param realPointFee the real_point_fee value
	 */
	public void setRealPointFee (java.math.BigDecimal realPointFee) {
		this.realPointFee = realPointFee;
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
	 * Return the value associated with the column: buyer_alipay_no
	 */
	public java.lang.String getBuyerAlipayNo () {
		return buyerAlipayNo;
	}

	/**
	 * Set the value related to the column: buyer_alipay_no
	 * @param buyerAlipayNo the buyer_alipay_no value
	 */
	public void setBuyerAlipayNo (java.lang.String buyerAlipayNo) {
		this.buyerAlipayNo = buyerAlipayNo;
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
	 * Return the value associated with the column: commission_fee
	 */
	public java.math.BigDecimal getCommissionFee () {
		return commissionFee;
	}

	/**
	 * Set the value related to the column: commission_fee
	 * @param commissionFee the commission_fee value
	 */
	public void setCommissionFee (java.math.BigDecimal commissionFee) {
		this.commissionFee = commissionFee;
	}



	/**
	 * Return the value associated with the column: seller_alipay_no
	 */
	public java.lang.String getSellerAlipayNo () {
		return sellerAlipayNo;
	}

	/**
	 * Set the value related to the column: seller_alipay_no
	 * @param sellerAlipayNo the seller_alipay_no value
	 */
	public void setSellerAlipayNo (java.lang.String sellerAlipayNo) {
		this.sellerAlipayNo = sellerAlipayNo;
	}



	/**
	 * Return the value associated with the column: seller_mobile
	 */
	public java.lang.String getSellerMobile () {
		return sellerMobile;
	}

	/**
	 * Set the value related to the column: seller_mobile
	 * @param sellerMobile the seller_mobile value
	 */
	public void setSellerMobile (java.lang.String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}



	/**
	 * Return the value associated with the column: seller_phone
	 */
	public java.lang.String getSellerPhone () {
		return sellerPhone;
	}

	/**
	 * Set the value related to the column: seller_phone
	 * @param sellerPhone the seller_phone value
	 */
	public void setSellerPhone (java.lang.String sellerPhone) {
		this.sellerPhone = sellerPhone;
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
	 * Return the value associated with the column: seller_email
	 */
	public java.lang.String getSellerEmail () {
		return sellerEmail;
	}

	/**
	 * Set the value related to the column: seller_email
	 * @param sellerEmail the seller_email value
	 */
	public void setSellerEmail (java.lang.String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}



	/**
	 * Return the value associated with the column: available_confirm_fee
	 */
	public java.math.BigDecimal getAvailableConfirmFee () {
		return availableConfirmFee;
	}

	/**
	 * Set the value related to the column: available_confirm_fee
	 * @param availableConfirmFee the available_confirm_fee value
	 */
	public void setAvailableConfirmFee (java.math.BigDecimal availableConfirmFee) {
		this.availableConfirmFee = availableConfirmFee;
	}



	/**
	 * Return the value associated with the column: has_post_fee
	 */
	public boolean isHasPostFee () {
		return hasPostFee;
	}

	/**
	 * Set the value related to the column: has_post_fee
	 * @param hasPostFee the has_post_fee value
	 */
	public void setHasPostFee (boolean hasPostFee) {
		this.hasPostFee = hasPostFee;
	}



	/**
	 * Return the value associated with the column: received_payment
	 */
	public java.math.BigDecimal getReceivedPayment () {
		return receivedPayment;
	}

	/**
	 * Set the value related to the column: received_payment
	 * @param receivedPayment the received_payment value
	 */
	public void setReceivedPayment (java.math.BigDecimal receivedPayment) {
		this.receivedPayment = receivedPayment;
	}



	/**
	 * Return the value associated with the column: cod_fee
	 */
	public java.math.BigDecimal getCodFee () {
		return codFee;
	}

	/**
	 * Set the value related to the column: cod_fee
	 * @param codFee the cod_fee value
	 */
	public void setCodFee (java.math.BigDecimal codFee) {
		this.codFee = codFee;
	}



	/**
	 * Return the value associated with the column: cod_status
	 */
	public java.lang.String getCodStatus () {
		return codStatus;
	}

	/**
	 * Set the value related to the column: cod_status
	 * @param codStatus the cod_status value
	 */
	public void setCodStatus (java.lang.String codStatus) {
		this.codStatus = codStatus;
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
	 * Return the value associated with the column: is_3D
	 */
	public boolean isIs3d () {
		return is3d;
	}

	/**
	 * Set the value related to the column: is_3D
	 * @param is3d the is_3D value
	 */
	public void setIs3d (boolean is3d) {
		this.is3d = is3d;
	}



	/**
	 * Return the value associated with the column: buyer_flag
	 */
	public java.lang.Integer getBuyerFlag () {
		return buyerFlag;
	}

	/**
	 * Set the value related to the column: buyer_flag
	 * @param buyerFlag the buyer_flag value
	 */
	public void setBuyerFlag (java.lang.Integer buyerFlag) {
		this.buyerFlag = buyerFlag;
	}



	/**
	 * Return the value associated with the column: seller_flag
	 */
	public java.lang.Integer getSellerFlag () {
		return sellerFlag;
	}

	/**
	 * Set the value related to the column: seller_flag
	 * @param sellerFlag the seller_flag value
	 */
	public void setSellerFlag (java.lang.Integer sellerFlag) {
		this.sellerFlag = sellerFlag;
	}



	/**
	 * Return the value associated with the column: promotion
	 */
	public java.lang.String getPromotion () {
		return promotion;
	}

	/**
	 * Set the value related to the column: promotion
	 * @param promotion the promotion value
	 */
	public void setPromotion (java.lang.String promotion) {
		this.promotion = promotion;
	}



	/**
	 * Return the value associated with the column: invoice_name
	 */
	public java.lang.String getInvoiceName () {
		return invoiceName;
	}

	/**
	 * Set the value related to the column: invoice_name
	 * @param invoiceName the invoice_name value
	 */
	public void setInvoiceName (java.lang.String invoiceName) {
		this.invoiceName = invoiceName;
	}



	/**
	 * Return the value associated with the column: trade_from
	 */
	public java.lang.String getTradeFrom () {
		return tradeFrom;
	}

	/**
	 * Set the value related to the column: trade_from
	 * @param tradeFrom the trade_from value
	 */
	public void setTradeFrom (java.lang.String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}



	/**
	 * Return the value associated with the column: alipay_url
	 */
	public java.lang.String getAlipayUrl () {
		return alipayUrl;
	}

	/**
	 * Set the value related to the column: alipay_url
	 * @param alipayUrl the alipay_url value
	 */
	public void setAlipayUrl (java.lang.String alipayUrl) {
		this.alipayUrl = alipayUrl;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.OrderTaobaoinfo)) return false;
		else {
			com.sunmw.web.entity.OrderTaobaoinfo orderTaobaoinfo = (com.sunmw.web.entity.OrderTaobaoinfo) obj;
			if (null == this.getId() || null == orderTaobaoinfo.getId()) return false;
			else return (this.getId().equals(orderTaobaoinfo.getId()));
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