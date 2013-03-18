package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the Item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="Item"
 */

public abstract class BaseItem  implements Serializable {

	public static String REF = "Item";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_TYPE = "Type";
	public static String PROP_WARRANTY = "Warranty";
	public static String PROP_PIC_URL = "PicUrl";
	public static String PROP_APPROVE_STATUS = "ApproveStatus";
	public static String PROP_NUM_IID = "NumIid";
	public static String PROP_ACTION = "Action";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_STUFF_STATUS = "StuffStatus";
	public static String PROP_INCREMENT = "Increment";
	public static String PROP_CID = "Cid";
	public static String PROP_VIOLATION = "Violation";
	public static String PROP_DISCOUNT = "Discount";
	public static String PROP_FREIGHT_PAYER = "FreightPayer";
	public static String PROP_POST_FEE = "PostFee";
	public static String PROP_AUCTION_POINT = "AuctionPoint";
	public static String PROP_SELLER_CIDS = "SellerCids";
	public static String PROP_TIMING = "Timing";
	public static String PROP_OUTER_ID = "OuterId";
	public static String PROP_PUBLISH_TIME = "PublishTime";
	public static String PROP_NUM = "Num";
	public static String PROP_DEFAULT_SKU = "DefaultSku";
	public static String PROP_PRICE_UPDATE_TIME = "PriceUpdateTime";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_PROPERTY_ALIAS = "PropertyAlias";
	public static String PROP_BIND_PROPS = "BindProps";
	public static String PROP_INVOICE = "Invoice";
	public static String PROP_SHOWCASE = "Showcase";
	public static String PROP_POSTAGE_ID = "PostageId";
	public static String PROP_VALID_THRU = "ValidThru";
	public static String PROP_PRODUCT_ID = "ProductId";
	public static String PROP_SALE_PROPS = "SaleProps";
	public static String PROP_TITLE = "Title";
	public static String PROP_EXPRESS_FEE = "ExpressFee";
	public static String PROP_ITEM_CD = "ItemCd";
	public static String PROP_INPUT_STR = "InputStr";
	public static String PROP_INPUT_PIDS = "InputPids";
	public static String PROP_EMS_FEE = "EmsFee";
	public static String PROP_CREATE_TIME = "CreateTime";
	public static String PROP_PRICE = "Price";
	public static String PROP_ID = "Id";
	public static String PROP_LIST_TIME = "ListTime";
	public static String PROP_INV_UPDATE_TIME = "InvUpdateTime";


	// constructors
	public BaseItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItem (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItem (
		java.lang.Long id,
		java.lang.String title,
		java.lang.String type,
		java.lang.Long cid,
		java.lang.String saleProps,
		java.lang.String inputPids,
		java.lang.String inputStr,
		java.lang.String picUrl,
		java.lang.Long num,
		java.lang.Long validThru,
		java.lang.String stuffStatus,
		java.lang.Integer productId) {

		this.setId(id);
		this.setTitle(title);
		this.setType(type);
		this.setCid(cid);
		this.setSaleProps(saleProps);
		this.setInputPids(inputPids);
		this.setInputStr(inputStr);
		this.setPicUrl(picUrl);
		this.setNum(num);
		this.setValidThru(validThru);
		this.setStuffStatus(stuffStatus);
		this.setProductId(productId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.String action;
	private java.lang.Long numIid;
	private java.lang.String itemCd;
	private java.lang.String title;
	private java.lang.String type;
	private java.lang.Long cid;
	private java.lang.String sellerCids;
	private java.lang.String saleProps;
	private java.lang.String bindProps;
	private java.lang.String inputPids;
	private java.lang.String inputStr;
	private java.lang.String picUrl;
	private java.lang.Long num;
	private java.lang.Long validThru;
	private java.util.Date listTime;
	private java.lang.String stuffStatus;
	private java.math.BigDecimal price;
	private java.math.BigDecimal postFee;
	private java.math.BigDecimal expressFee;
	private java.math.BigDecimal emsFee;
	private int discount;
	private java.lang.String freightPayer;
	private int invoice;
	private int warranty;
	private int showcase;
	private java.lang.String increment;
	private java.lang.String approveStatus;
	private java.lang.Long postageId;
	private java.lang.Integer productId;
	private java.lang.Integer auctionPoint;
	private java.lang.String outerId;
	private java.lang.String timing;
	private java.lang.String violation;
	private java.lang.String propertyAlias;
	private java.lang.Integer companyId;
	private java.lang.Integer storeId;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private java.util.Date publishTime;
	private java.lang.String defaultSku;
	private java.util.Date invUpdateTime;
	private java.util.Date priceUpdateTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Item_id"
     */
	public java.lang.Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Action
	 */
	public java.lang.String getAction () {
		return action;
	}

	/**
	 * Set the value related to the column: Action
	 * @param action the Action value
	 */
	public void setAction (java.lang.String action) {
		this.action = action;
	}



	/**
	 * Return the value associated with the column: num_iid
	 */
	public java.lang.Long getNumIid () {
		return numIid;
	}

	/**
	 * Set the value related to the column: num_iid
	 * @param numIid the num_iid value
	 */
	public void setNumIid (java.lang.Long numIid) {
		this.numIid = numIid;
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
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: cid
	 */
	public java.lang.Long getCid () {
		return cid;
	}

	/**
	 * Set the value related to the column: cid
	 * @param cid the cid value
	 */
	public void setCid (java.lang.Long cid) {
		this.cid = cid;
	}



	/**
	 * Return the value associated with the column: seller_cids
	 */
	public java.lang.String getSellerCids () {
		return sellerCids;
	}

	/**
	 * Set the value related to the column: seller_cids
	 * @param sellerCids the seller_cids value
	 */
	public void setSellerCids (java.lang.String sellerCids) {
		this.sellerCids = sellerCids;
	}



	/**
	 * Return the value associated with the column: sale_props
	 */
	public java.lang.String getSaleProps () {
		return saleProps;
	}

	/**
	 * Set the value related to the column: sale_props
	 * @param saleProps the sale_props value
	 */
	public void setSaleProps (java.lang.String saleProps) {
		this.saleProps = saleProps;
	}



	/**
	 * Return the value associated with the column: bind_props
	 */
	public java.lang.String getBindProps () {
		return bindProps;
	}

	/**
	 * Set the value related to the column: bind_props
	 * @param bindProps the bind_props value
	 */
	public void setBindProps (java.lang.String bindProps) {
		this.bindProps = bindProps;
	}



	/**
	 * Return the value associated with the column: input_pids
	 */
	public java.lang.String getInputPids () {
		return inputPids;
	}

	/**
	 * Set the value related to the column: input_pids
	 * @param inputPids the input_pids value
	 */
	public void setInputPids (java.lang.String inputPids) {
		this.inputPids = inputPids;
	}



	/**
	 * Return the value associated with the column: input_str
	 */
	public java.lang.String getInputStr () {
		return inputStr;
	}

	/**
	 * Set the value related to the column: input_str
	 * @param inputStr the input_str value
	 */
	public void setInputStr (java.lang.String inputStr) {
		this.inputStr = inputStr;
	}



	/**
	 * Return the value associated with the column: pic_url
	 */
	public java.lang.String getPicUrl () {
		return picUrl;
	}

	/**
	 * Set the value related to the column: pic_url
	 * @param picUrl the pic_url value
	 */
	public void setPicUrl (java.lang.String picUrl) {
		this.picUrl = picUrl;
	}



	/**
	 * Return the value associated with the column: num
	 */
	public java.lang.Long getNum () {
		return num;
	}

	/**
	 * Set the value related to the column: num
	 * @param num the num value
	 */
	public void setNum (java.lang.Long num) {
		this.num = num;
	}



	/**
	 * Return the value associated with the column: valid_thru
	 */
	public java.lang.Long getValidThru () {
		return validThru;
	}

	/**
	 * Set the value related to the column: valid_thru
	 * @param validThru the valid_thru value
	 */
	public void setValidThru (java.lang.Long validThru) {
		this.validThru = validThru;
	}



	/**
	 * Return the value associated with the column: list_time
	 */
	public java.util.Date getListTime () {
		return listTime;
	}

	/**
	 * Set the value related to the column: list_time
	 * @param listTime the list_time value
	 */
	public void setListTime (java.util.Date listTime) {
		this.listTime = listTime;
	}



	/**
	 * Return the value associated with the column: stuff_status
	 */
	public java.lang.String getStuffStatus () {
		return stuffStatus;
	}

	/**
	 * Set the value related to the column: stuff_status
	 * @param stuffStatus the stuff_status value
	 */
	public void setStuffStatus (java.lang.String stuffStatus) {
		this.stuffStatus = stuffStatus;
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
	 * Return the value associated with the column: express_fee
	 */
	public java.math.BigDecimal getExpressFee () {
		return expressFee;
	}

	/**
	 * Set the value related to the column: express_fee
	 * @param expressFee the express_fee value
	 */
	public void setExpressFee (java.math.BigDecimal expressFee) {
		this.expressFee = expressFee;
	}



	/**
	 * Return the value associated with the column: ems_fee
	 */
	public java.math.BigDecimal getEmsFee () {
		return emsFee;
	}

	/**
	 * Set the value related to the column: ems_fee
	 * @param emsFee the ems_fee value
	 */
	public void setEmsFee (java.math.BigDecimal emsFee) {
		this.emsFee = emsFee;
	}



	/**
	 * Return the value associated with the column: has_discount
	 */
	public int getDiscount () {
		return discount;
	}

	/**
	 * Set the value related to the column: has_discount
	 * @param discount the has_discount value
	 */
	public void setDiscount (int discount) {
		this.discount = discount;
	}



	/**
	 * Return the value associated with the column: freight_payer
	 */
	public java.lang.String getFreightPayer () {
		return freightPayer;
	}

	/**
	 * Set the value related to the column: freight_payer
	 * @param freightPayer the freight_payer value
	 */
	public void setFreightPayer (java.lang.String freightPayer) {
		this.freightPayer = freightPayer;
	}



	/**
	 * Return the value associated with the column: has_invoice
	 */
	public int getInvoice () {
		return invoice;
	}

	/**
	 * Set the value related to the column: has_invoice
	 * @param invoice the has_invoice value
	 */
	public void setInvoice (int invoice) {
		this.invoice = invoice;
	}



	/**
	 * Return the value associated with the column: has_warranty
	 */
	public int getWarranty () {
		return warranty;
	}

	/**
	 * Set the value related to the column: has_warranty
	 * @param warranty the has_warranty value
	 */
	public void setWarranty (int warranty) {
		this.warranty = warranty;
	}



	/**
	 * Return the value associated with the column: has_showcase
	 */
	public int getShowcase () {
		return showcase;
	}

	/**
	 * Set the value related to the column: has_showcase
	 * @param showcase the has_showcase value
	 */
	public void setShowcase (int showcase) {
		this.showcase = showcase;
	}



	/**
	 * Return the value associated with the column: increment
	 */
	public java.lang.String getIncrement () {
		return increment;
	}

	/**
	 * Set the value related to the column: increment
	 * @param increment the increment value
	 */
	public void setIncrement (java.lang.String increment) {
		this.increment = increment;
	}



	/**
	 * Return the value associated with the column: approve_status
	 */
	public java.lang.String getApproveStatus () {
		return approveStatus;
	}

	/**
	 * Set the value related to the column: approve_status
	 * @param approveStatus the approve_status value
	 */
	public void setApproveStatus (java.lang.String approveStatus) {
		this.approveStatus = approveStatus;
	}



	/**
	 * Return the value associated with the column: postage_id
	 */
	public java.lang.Long getPostageId () {
		return postageId;
	}

	/**
	 * Set the value related to the column: postage_id
	 * @param postageId the postage_id value
	 */
	public void setPostageId (java.lang.Long postageId) {
		this.postageId = postageId;
	}



	/**
	 * Return the value associated with the column: product_id
	 */
	public java.lang.Integer getProductId () {
		return productId;
	}

	/**
	 * Set the value related to the column: product_id
	 * @param productId the product_id value
	 */
	public void setProductId (java.lang.Integer productId) {
		this.productId = productId;
	}



	/**
	 * Return the value associated with the column: auction_point
	 */
	public java.lang.Integer getAuctionPoint () {
		return auctionPoint;
	}

	/**
	 * Set the value related to the column: auction_point
	 * @param auctionPoint the auction_point value
	 */
	public void setAuctionPoint (java.lang.Integer auctionPoint) {
		this.auctionPoint = auctionPoint;
	}



	/**
	 * Return the value associated with the column: outer_id
	 */
	public java.lang.String getOuterId () {
		return outerId;
	}

	/**
	 * Set the value related to the column: outer_id
	 * @param outerId the outer_id value
	 */
	public void setOuterId (java.lang.String outerId) {
		this.outerId = outerId;
	}



	/**
	 * Return the value associated with the column: is_timing
	 */
	public java.lang.String getTiming () {
		return timing;
	}

	/**
	 * Set the value related to the column: is_timing
	 * @param timing the is_timing value
	 */
	public void setTiming (java.lang.String timing) {
		this.timing = timing;
	}



	/**
	 * Return the value associated with the column: violation
	 */
	public java.lang.String getViolation () {
		return violation;
	}

	/**
	 * Set the value related to the column: violation
	 * @param violation the violation value
	 */
	public void setViolation (java.lang.String violation) {
		this.violation = violation;
	}



	/**
	 * Return the value associated with the column: property_alias
	 */
	public java.lang.String getPropertyAlias () {
		return propertyAlias;
	}

	/**
	 * Set the value related to the column: property_alias
	 * @param propertyAlias the property_alias value
	 */
	public void setPropertyAlias (java.lang.String propertyAlias) {
		this.propertyAlias = propertyAlias;
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
	 * Return the value associated with the column: Store_id
	 */
	public java.lang.Integer getStoreId () {
		return storeId;
	}

	/**
	 * Set the value related to the column: Store_id
	 * @param storeId the Store_id value
	 */
	public void setStoreId (java.lang.Integer storeId) {
		this.storeId = storeId;
	}



	/**
	 * Return the value associated with the column: Update_time
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}

	/**
	 * Set the value related to the column: Update_time
	 * @param updateTime the Update_time value
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}



	/**
	 * Return the value associated with the column: Create_time
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: Create_time
	 * @param createTime the Create_time value
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}



	/**
	 * Return the value associated with the column: Publish_time
	 */
	public java.util.Date getPublishTime () {
		return publishTime;
	}

	/**
	 * Set the value related to the column: Publish_time
	 * @param publishTime the Publish_time value
	 */
	public void setPublishTime (java.util.Date publishTime) {
		this.publishTime = publishTime;
	}



	/**
	 * Return the value associated with the column: Default_sku
	 */
	public java.lang.String getDefaultSku () {
		return defaultSku;
	}

	/**
	 * Set the value related to the column: Default_sku
	 * @param defaultSku the Default_sku value
	 */
	public void setDefaultSku (java.lang.String defaultSku) {
		this.defaultSku = defaultSku;
	}



	/**
	 * Return the value associated with the column: Inv_Update_Time
	 */
	public java.util.Date getInvUpdateTime () {
		return invUpdateTime;
	}

	/**
	 * Set the value related to the column: Inv_Update_Time
	 * @param invUpdateTime the Inv_Update_Time value
	 */
	public void setInvUpdateTime (java.util.Date invUpdateTime) {
		this.invUpdateTime = invUpdateTime;
	}



	/**
	 * Return the value associated with the column: Price_Update_Time
	 */
	public java.util.Date getPriceUpdateTime () {
		return priceUpdateTime;
	}

	/**
	 * Set the value related to the column: Price_Update_Time
	 * @param priceUpdateTime the Price_Update_Time value
	 */
	public void setPriceUpdateTime (java.util.Date priceUpdateTime) {
		this.priceUpdateTime = priceUpdateTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.Item)) return false;
		else {
			com.sunmw.taobao.entity.Item item = (com.sunmw.taobao.entity.Item) obj;
			if (null == this.getId() || null == item.getId()) return false;
			else return (this.getId().equals(item.getId()));
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