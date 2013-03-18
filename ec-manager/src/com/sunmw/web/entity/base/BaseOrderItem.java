package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the order_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="order_item"
 */

public abstract class BaseOrderItem  implements Serializable {

	public static String REF = "OrderItem";
	public static String PROP_ORIG_SKU_ID = "OrigSkuId";
	public static String PROP_ORDER_HEAD_ID = "OrderHeadId";
	public static String PROP_REQ_QTY = "ReqQty";
	public static String PROP_SKU_CD = "SkuCd";
	public static String PROP_QTY = "Qty";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_REF_QTY = "RefQty";
	public static String PROP_RED_ORDER_ITEM_ID = "RedOrderItemId";
	public static String PROP_ORDER_ITEM_TYPE = "OrderItemType";
	public static String PROP_DISCOUNT_PRICE_AMT = "DiscountPriceAmt";
	public static String PROP_REAL_PRICE = "RealPrice";
	public static String PROP_ORDER_ITEM_STATUS = "OrderItemStatus";
	public static String PROP_ADJUST_FEE = "AdjustFee";
	public static String PROP_ORIG_ORDER_ITEM_NO = "OrigOrderItemNo";
	public static String PROP_DISCOUNT_PRICE = "DiscountPrice";
	public static String PROP_ITEM_CD = "ItemCd";
	public static String PROP_NAME = "Name";
	public static String PROP_ORDER_ITEM_MEMO = "OrderItemMemo";
	public static String PROP_SUB_TOTAL = "SubTotal";
	public static String PROP_REAL_PRICE_AMT = "RealPriceAmt";
	public static String PROP_PRICE = "Price";
	public static String PROP_SKU_PROP1 = "SkuProp1";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_SKU_PROP3 = "SkuProp3";
	public static String PROP_SKU_PROP2 = "SkuProp2";


	// constructors
	public BaseOrderItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrderItem (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer orderHeadId;
	private java.lang.String skuCd;
	private java.lang.String itemCd;
	private java.lang.String skuProp1;
	private java.lang.String skuProp2;
	private java.lang.String skuProp3;
	private java.math.BigDecimal reqQty;
	private java.math.BigDecimal qty;
	private java.math.BigDecimal price;
	private java.lang.String orderItemStatus;
	private java.math.BigDecimal subTotal;
	private java.lang.Integer redOrderItemId;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String origOrderItemNo;
	private java.lang.String origSkuId;
	private java.lang.String name;
	private java.lang.String orderItemType;
	private java.lang.String orderItemMemo;
	private java.math.BigDecimal refQty;
	private java.math.BigDecimal discountPrice;
	private java.math.BigDecimal realPrice;
	private java.math.BigDecimal discountPriceAmt;
	private java.math.BigDecimal realPriceAmt;
	private java.math.BigDecimal adjustFee;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="order_item_id"
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
	 * Return the value associated with the column: order_head_id
	 */
	public java.lang.Integer getOrderHeadId () {
		return orderHeadId;
	}

	/**
	 * Set the value related to the column: order_head_id
	 * @param orderHeadId the order_head_id value
	 */
	public void setOrderHeadId (java.lang.Integer orderHeadId) {
		this.orderHeadId = orderHeadId;
	}



	/**
	 * Return the value associated with the column: sku_cd
	 */
	public java.lang.String getSkuCd () {
		return skuCd;
	}

	/**
	 * Set the value related to the column: sku_cd
	 * @param skuCd the sku_cd value
	 */
	public void setSkuCd (java.lang.String skuCd) {
		this.skuCd = skuCd;
	}



	/**
	 * Return the value associated with the column: item_cd
	 */
	public java.lang.String getItemCd () {
		return itemCd;
	}

	/**
	 * Set the value related to the column: item_cd
	 * @param itemCd the item_cd value
	 */
	public void setItemCd (java.lang.String itemCd) {
		this.itemCd = itemCd;
	}



	/**
	 * Return the value associated with the column: sku_prop1
	 */
	public java.lang.String getSkuProp1 () {
		return skuProp1;
	}

	/**
	 * Set the value related to the column: sku_prop1
	 * @param skuProp1 the sku_prop1 value
	 */
	public void setSkuProp1 (java.lang.String skuProp1) {
		this.skuProp1 = skuProp1;
	}



	/**
	 * Return the value associated with the column: sku_prop2
	 */
	public java.lang.String getSkuProp2 () {
		return skuProp2;
	}

	/**
	 * Set the value related to the column: sku_prop2
	 * @param skuProp2 the sku_prop2 value
	 */
	public void setSkuProp2 (java.lang.String skuProp2) {
		this.skuProp2 = skuProp2;
	}



	/**
	 * Return the value associated with the column: sku_prop3
	 */
	public java.lang.String getSkuProp3 () {
		return skuProp3;
	}

	/**
	 * Set the value related to the column: sku_prop3
	 * @param skuProp3 the sku_prop3 value
	 */
	public void setSkuProp3 (java.lang.String skuProp3) {
		this.skuProp3 = skuProp3;
	}



	/**
	 * Return the value associated with the column: req_qty
	 */
	public java.math.BigDecimal getReqQty () {
		return reqQty;
	}

	/**
	 * Set the value related to the column: req_qty
	 * @param reqQty the req_qty value
	 */
	public void setReqQty (java.math.BigDecimal reqQty) {
		this.reqQty = reqQty;
	}



	/**
	 * Return the value associated with the column: qty
	 */
	public java.math.BigDecimal getQty () {
		return qty;
	}

	/**
	 * Set the value related to the column: qty
	 * @param qty the qty value
	 */
	public void setQty (java.math.BigDecimal qty) {
		this.qty = qty;
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
	 * Return the value associated with the column: order_item_status
	 */
	public java.lang.String getOrderItemStatus () {
		return orderItemStatus;
	}

	/**
	 * Set the value related to the column: order_item_status
	 * @param orderItemStatus the order_item_status value
	 */
	public void setOrderItemStatus (java.lang.String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}



	/**
	 * Return the value associated with the column: sub_total
	 */
	public java.math.BigDecimal getSubTotal () {
		return subTotal;
	}

	/**
	 * Set the value related to the column: sub_total
	 * @param subTotal the sub_total value
	 */
	public void setSubTotal (java.math.BigDecimal subTotal) {
		this.subTotal = subTotal;
	}



	/**
	 * Return the value associated with the column: red_order_item_id
	 */
	public java.lang.Integer getRedOrderItemId () {
		return redOrderItemId;
	}

	/**
	 * Set the value related to the column: red_order_item_id
	 * @param redOrderItemId the red_order_item_id value
	 */
	public void setRedOrderItemId (java.lang.Integer redOrderItemId) {
		this.redOrderItemId = redOrderItemId;
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
	 * Return the value associated with the column: orig_order_item_no
	 */
	public java.lang.String getOrigOrderItemNo () {
		return origOrderItemNo;
	}

	/**
	 * Set the value related to the column: orig_order_item_no
	 * @param origOrderItemNo the orig_order_item_no value
	 */
	public void setOrigOrderItemNo (java.lang.String origOrderItemNo) {
		this.origOrderItemNo = origOrderItemNo;
	}



	/**
	 * Return the value associated with the column: orig_sku_id
	 */
	public java.lang.String getOrigSkuId () {
		return origSkuId;
	}

	/**
	 * Set the value related to the column: orig_sku_id
	 * @param origSkuId the orig_sku_id value
	 */
	public void setOrigSkuId (java.lang.String origSkuId) {
		this.origSkuId = origSkuId;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: order_item_type
	 */
	public java.lang.String getOrderItemType () {
		return orderItemType;
	}

	/**
	 * Set the value related to the column: order_item_type
	 * @param orderItemType the order_item_type value
	 */
	public void setOrderItemType (java.lang.String orderItemType) {
		this.orderItemType = orderItemType;
	}



	/**
	 * Return the value associated with the column: order_item_memo
	 */
	public java.lang.String getOrderItemMemo () {
		return orderItemMemo;
	}

	/**
	 * Set the value related to the column: order_item_memo
	 * @param orderItemMemo the order_item_memo value
	 */
	public void setOrderItemMemo (java.lang.String orderItemMemo) {
		this.orderItemMemo = orderItemMemo;
	}



	/**
	 * Return the value associated with the column: ref_qty
	 */
	public java.math.BigDecimal getRefQty () {
		return refQty;
	}

	/**
	 * Set the value related to the column: ref_qty
	 * @param refQty the ref_qty value
	 */
	public void setRefQty (java.math.BigDecimal refQty) {
		this.refQty = refQty;
	}



	/**
	 * Return the value associated with the column: discount_price
	 */
	public java.math.BigDecimal getDiscountPrice () {
		return discountPrice;
	}

	/**
	 * Set the value related to the column: discount_price
	 * @param discountPrice the discount_price value
	 */
	public void setDiscountPrice (java.math.BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}



	/**
	 * Return the value associated with the column: real_price
	 */
	public java.math.BigDecimal getRealPrice () {
		return realPrice;
	}

	/**
	 * Set the value related to the column: real_price
	 * @param realPrice the real_price value
	 */
	public void setRealPrice (java.math.BigDecimal realPrice) {
		this.realPrice = realPrice;
	}



	/**
	 * Return the value associated with the column: discount_price_amt
	 */
	public java.math.BigDecimal getDiscountPriceAmt () {
		return discountPriceAmt;
	}

	/**
	 * Set the value related to the column: discount_price_amt
	 * @param discountPriceAmt the discount_price_amt value
	 */
	public void setDiscountPriceAmt (java.math.BigDecimal discountPriceAmt) {
		this.discountPriceAmt = discountPriceAmt;
	}



	/**
	 * Return the value associated with the column: real_price_amt
	 */
	public java.math.BigDecimal getRealPriceAmt () {
		return realPriceAmt;
	}

	/**
	 * Set the value related to the column: real_price_amt
	 * @param realPriceAmt the real_price_amt value
	 */
	public void setRealPriceAmt (java.math.BigDecimal realPriceAmt) {
		this.realPriceAmt = realPriceAmt;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.OrderItem)) return false;
		else {
			com.sunmw.web.entity.OrderItem orderItem = (com.sunmw.web.entity.OrderItem) obj;
			if (null == this.getId() || null == orderItem.getId()) return false;
			else return (this.getId().equals(orderItem.getId()));
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