package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_refund table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_refund"
 */

public abstract class BaseTbRefund  implements Serializable {

	public static String REF = "TbRefund";
	public static String PROP_TOTAL_FEE = "TotalFee";
	public static String PROP_GOOD_RETURN_TIME = "GoodReturnTime";
	public static String PROP_ORDER_STATUS = "OrderStatus";
	public static String PROP_OID = "Oid";
	public static String PROP_PAYMENT = "Payment";
	public static String PROP_NUM_IID = "NumIid";
	public static String PROP_BUYER_NICK = "BuyerNick";
	public static String PROP_CREATED = "Created";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_REASON = "Reason";
	public static String PROP_REFUND_ID = "RefundId";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_SELLER_NICK = "SellerNick";
	public static String PROP_NUM = "Num";
	public static String PROP_ALIPAY_NO = "AlipayNo";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_HAS_GOOD_RETURN = "HasGoodReturn";
	public static String PROP_SHIPPING_TYPE = "ShippingType";
	public static String PROP_COMPANY_NAME = "CompanyName";
	public static String PROP_TID = "Tid";
	public static String PROP_TITLE = "Title";
	public static String PROP_REFUND_FEE = "RefundFee";
	public static String PROP_STATUS = "Status";
	public static String PROP_PRICE = "Price";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_IID = "Iid";
	public static String PROP_SID = "Sid";
	public static String PROP_ID = "Id";
	public static String PROP_UPDATE_FLAG = "UpdateFlag";
	public static String PROP_GOOD_STATUS = "GoodStatus";


	// constructors
	public BaseTbRefund () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbRefund (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.math.BigDecimal refundId;
	private java.math.BigDecimal tid;
	private java.math.BigDecimal oid;
	private java.lang.String alipayNo;
	private java.math.BigDecimal totalFee;
	private java.lang.String buyerNick;
	private java.lang.String sellerNick;
	private java.util.Date created;
	private java.util.Date modified;
	private java.lang.String orderStatus;
	private java.lang.String status;
	private java.lang.String goodStatus;
	private boolean hasGoodReturn;
	private java.math.BigDecimal refundFee;
	private java.math.BigDecimal payment;
	private java.lang.String reason;
	private java.lang.String description;
	private java.lang.String iid;
	private java.lang.String title;
	private java.math.BigDecimal price;
	private java.lang.Integer num;
	private java.util.Date goodReturnTime;
	private java.lang.String companyName;
	private java.lang.String sid;
	private java.lang.String address;
	private java.lang.String shippingType;
	private java.lang.Integer numIid;
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
	 * Return the value associated with the column: tid
	 */
	public java.math.BigDecimal getTid () {
		return tid;
	}

	/**
	 * Set the value related to the column: tid
	 * @param tid the tid value
	 */
	public void setTid (java.math.BigDecimal tid) {
		this.tid = tid;
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
	 * Return the value associated with the column: good_status
	 */
	public java.lang.String getGoodStatus () {
		return goodStatus;
	}

	/**
	 * Set the value related to the column: good_status
	 * @param goodStatus the good_status value
	 */
	public void setGoodStatus (java.lang.String goodStatus) {
		this.goodStatus = goodStatus;
	}



	/**
	 * Return the value associated with the column: has_good_return
	 */
	public boolean isHasGoodReturn () {
		return hasGoodReturn;
	}

	/**
	 * Set the value related to the column: has_good_return
	 * @param hasGoodReturn the has_good_return value
	 */
	public void setHasGoodReturn (boolean hasGoodReturn) {
		this.hasGoodReturn = hasGoodReturn;
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
	 * Return the value associated with the column: reason
	 */
	public java.lang.String getReason () {
		return reason;
	}

	/**
	 * Set the value related to the column: reason
	 * @param reason the reason value
	 */
	public void setReason (java.lang.String reason) {
		this.reason = reason;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
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
	 * Return the value associated with the column: good_return_time
	 */
	public java.util.Date getGoodReturnTime () {
		return goodReturnTime;
	}

	/**
	 * Set the value related to the column: good_return_time
	 * @param goodReturnTime the good_return_time value
	 */
	public void setGoodReturnTime (java.util.Date goodReturnTime) {
		this.goodReturnTime = goodReturnTime;
	}



	/**
	 * Return the value associated with the column: company_name
	 */
	public java.lang.String getCompanyName () {
		return companyName;
	}

	/**
	 * Set the value related to the column: company_name
	 * @param companyName the company_name value
	 */
	public void setCompanyName (java.lang.String companyName) {
		this.companyName = companyName;
	}



	/**
	 * Return the value associated with the column: sid
	 */
	public java.lang.String getSid () {
		return sid;
	}

	/**
	 * Set the value related to the column: sid
	 * @param sid the sid value
	 */
	public void setSid (java.lang.String sid) {
		this.sid = sid;
	}



	/**
	 * Return the value associated with the column: address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * @param address the address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
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
	 * Return the value associated with the column: num_iid
	 */
	public java.lang.Integer getNumIid () {
		return numIid;
	}

	/**
	 * Set the value related to the column: num_iid
	 * @param numIid the num_iid value
	 */
	public void setNumIid (java.lang.Integer numIid) {
		this.numIid = numIid;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbRefund)) return false;
		else {
			com.sunmw.taobao.entity.TbRefund tbRefund = (com.sunmw.taobao.entity.TbRefund) obj;
			if (null == this.getId() || null == tbRefund.getId()) return false;
			else return (this.getId().equals(tbRefund.getId()));
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