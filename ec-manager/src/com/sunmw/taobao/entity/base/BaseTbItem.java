package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_item"
 */

public abstract class BaseTbItem  implements Serializable {

	public static String REF = "TbItem";
	public static String PROP_DETAIL_URL = "DetailUrl";
	public static String PROP_IS_TIMING = "IsTiming";
	public static String PROP_AUTO_FILL = "AutoFill";
	public static String PROP_DELIST_TIME = "DelistTime";
	public static String PROP_PIC_URL = "PicUrl";
	public static String PROP_IS_VIRTUAL = "IsVirtual";
	public static String PROP_NUM_IID = "NumIid";
	public static String PROP_HAS_DISCOUNT = "HasDiscount";
	public static String PROP_STUFF_STATUS = "StuffStatus";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_INCREMENT = "Increment";
	public static String PROP_CID = "Cid";
	public static String PROP_SECOND_KILL = "SecondKill";
	public static String PROP_FREIGHT_PAYER = "FreightPayer";
	public static String PROP_SELLER_CIDS = "SellerCids";
	public static String PROP_SCORE = "Score";
	public static String PROP_IS_PREPAY = "IsPrepay";
	public static String PROP_IS_EX = "IsEx";
	public static String PROP_OUTER_ID = "OuterId";
	public static String PROP_NUM = "Num";
	public static String PROP_VALID_THRU = "ValidThru";
	public static String PROP_EXPRESS_FEE = "ExpressFee";
	public static String PROP_PRODUCT_ID = "ProductId";
	public static String PROP_INPUT_STR = "InputStr";
	public static String PROP_EMS_FEE = "EmsFee";
	public static String PROP_PRICE = "Price";
	public static String PROP_ID = "Id";
	public static String PROP_ONE_STATION = "OneStation";
	public static String PROP_COD_POSTAGE_ID = "CodPostageId";
	public static String PROP_LIST_TIME = "ListTime";
	public static String PROP_WAP_DESC = "WapDesc";
	public static String PROP_TYPE = "Type";
	public static String PROP_PROMOTED_SERVICE = "PromotedService";
	public static String PROP_HAS_SHOWCASE = "HasShowcase";
	public static String PROP_IS3D = "Is3d";
	public static String PROP_APPROVE_STATUS = "ApproveStatus";
	public static String PROP_WW_STATUS = "WwStatus";
	public static String PROP_PROPS_NAME = "PropsName";
	public static String PROP_CREATED = "Created";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_VIOLATION = "Violation";
	public static String PROP_POST_FEE = "PostFee";
	public static String PROP_AUCTION_POINT = "AuctionPoint";
	public static String PROP_NICK = "Nick";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_AFTER_SALE_ID = "AfterSaleId";
	public static String PROP_HAS_WARRANTY = "HasWarranty";
	public static String PROP_VOLUME = "Volume";
	public static String PROP_IS_TAOBAO = "IsTaobao";
	public static String PROP_HAS_INVOICE = "HasInvoice";
	public static String PROP_PROPERTY_ALIAS = "PropertyAlias";
	public static String PROP_SELL_PROMISE = "SellPromise";
	public static String PROP_POSTAGE_ID = "PostageId";
	public static String PROP_TITLE = "Title";
	public static String PROP_INPUT_PIDS = "InputPids";
	public static String PROP_PROPS = "Props";
	public static String PROP_WAP_DETAIL_URL = "WapDetailUrl";


	// constructors
	public BaseTbItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbItem (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String detailUrl;
	private java.math.BigDecimal numIid;
	private java.lang.String title;
	private java.lang.String nick;
	private java.lang.String type;
	private java.lang.String propsName;
	private java.lang.String promotedService;
	private java.lang.Long cid;
	private java.lang.String sellerCids;
	private java.lang.String props;
	private java.lang.String inputPids;
	private java.lang.String inputStr;
	private java.lang.String description;
	private java.lang.String picUrl;
	private java.lang.Long num;
	private java.lang.Long validThru;
	private java.util.Date listTime;
	private java.util.Date delistTime;
	private java.lang.String stuffStatus;
	private java.math.BigDecimal price;
	private java.math.BigDecimal postFee;
	private java.math.BigDecimal expressFee;
	private java.math.BigDecimal emsFee;
	private boolean hasDiscount;
	private java.lang.String freightPayer;
	private java.lang.Boolean hasInvoice;
	private java.lang.Boolean hasWarranty;
	private java.lang.Boolean hasShowcase;
	private java.util.Date modified;
	private java.lang.String increment;
	private java.lang.String approveStatus;
	private java.lang.Long postageId;
	private java.lang.Long productId;
	private java.lang.Long auctionPoint;
	private java.lang.String propertyAlias;
	private java.lang.String outerId;
	private java.lang.Boolean isVirtual;
	private java.lang.Boolean isTaobao;
	private java.lang.Boolean isEx;
	private java.lang.Boolean isTiming;
	private java.lang.Boolean is3d;
	private java.lang.Long score;
	private java.lang.Long volume;
	private java.lang.Boolean oneStation;
	private java.lang.String secondKill;
	private java.lang.String autoFill;
	private java.lang.Boolean violation;
	private java.util.Date created;
	private java.lang.Boolean isPrepay;
	private java.lang.Boolean wwStatus;
	private java.lang.String wapDesc;
	private java.lang.String wapDetailUrl;
	private java.lang.Long afterSaleId;
	private java.lang.Long codPostageId;
	private java.lang.Boolean sellPromise;
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
	 * Return the value associated with the column: detail_url
	 */
	public java.lang.String getDetailUrl () {
		return detailUrl;
	}

	/**
	 * Set the value related to the column: detail_url
	 * @param detailUrl the detail_url value
	 */
	public void setDetailUrl (java.lang.String detailUrl) {
		this.detailUrl = detailUrl;
	}



	/**
	 * Return the value associated with the column: num_iid
	 */
	public java.math.BigDecimal getNumIid () {
		return numIid;
	}

	/**
	 * Set the value related to the column: num_iid
	 * @param numIid the num_iid value
	 */
	public void setNumIid (java.math.BigDecimal numIid) {
		this.numIid = numIid;
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
	 * Return the value associated with the column: nick
	 */
	public java.lang.String getNick () {
		return nick;
	}

	/**
	 * Set the value related to the column: nick
	 * @param nick the nick value
	 */
	public void setNick (java.lang.String nick) {
		this.nick = nick;
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
	 * Return the value associated with the column: props_name
	 */
	public java.lang.String getPropsName () {
		return propsName;
	}

	/**
	 * Set the value related to the column: props_name
	 * @param propsName the props_name value
	 */
	public void setPropsName (java.lang.String propsName) {
		this.propsName = propsName;
	}



	/**
	 * Return the value associated with the column: promoted_service
	 */
	public java.lang.String getPromotedService () {
		return promotedService;
	}

	/**
	 * Set the value related to the column: promoted_service
	 * @param promotedService the promoted_service value
	 */
	public void setPromotedService (java.lang.String promotedService) {
		this.promotedService = promotedService;
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
	 * Return the value associated with the column: props
	 */
	public java.lang.String getProps () {
		return props;
	}

	/**
	 * Set the value related to the column: props
	 * @param props the props value
	 */
	public void setProps (java.lang.String props) {
		this.props = props;
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
	 * Return the value associated with the column: delist_time
	 */
	public java.util.Date getDelistTime () {
		return delistTime;
	}

	/**
	 * Set the value related to the column: delist_time
	 * @param delistTime the delist_time value
	 */
	public void setDelistTime (java.util.Date delistTime) {
		this.delistTime = delistTime;
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
	public boolean isHasDiscount () {
		return hasDiscount;
	}

	/**
	 * Set the value related to the column: has_discount
	 * @param hasDiscount the has_discount value
	 */
	public void setHasDiscount (boolean hasDiscount) {
		this.hasDiscount = hasDiscount;
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
	public java.lang.Boolean isHasInvoice () {
		return hasInvoice;
	}

	/**
	 * Set the value related to the column: has_invoice
	 * @param hasInvoice the has_invoice value
	 */
	public void setHasInvoice (java.lang.Boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}



	/**
	 * Return the value associated with the column: has_warranty
	 */
	public java.lang.Boolean isHasWarranty () {
		return hasWarranty;
	}

	/**
	 * Set the value related to the column: has_warranty
	 * @param hasWarranty the has_warranty value
	 */
	public void setHasWarranty (java.lang.Boolean hasWarranty) {
		this.hasWarranty = hasWarranty;
	}



	/**
	 * Return the value associated with the column: has_showcase
	 */
	public java.lang.Boolean isHasShowcase () {
		return hasShowcase;
	}

	/**
	 * Set the value related to the column: has_showcase
	 * @param hasShowcase the has_showcase value
	 */
	public void setHasShowcase (java.lang.Boolean hasShowcase) {
		this.hasShowcase = hasShowcase;
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
	public java.lang.Long getProductId () {
		return productId;
	}

	/**
	 * Set the value related to the column: product_id
	 * @param productId the product_id value
	 */
	public void setProductId (java.lang.Long productId) {
		this.productId = productId;
	}



	/**
	 * Return the value associated with the column: auction_point
	 */
	public java.lang.Long getAuctionPoint () {
		return auctionPoint;
	}

	/**
	 * Set the value related to the column: auction_point
	 * @param auctionPoint the auction_point value
	 */
	public void setAuctionPoint (java.lang.Long auctionPoint) {
		this.auctionPoint = auctionPoint;
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
	 * Return the value associated with the column: is_virtual
	 */
	public java.lang.Boolean isIsVirtual () {
		return isVirtual;
	}

	/**
	 * Set the value related to the column: is_virtual
	 * @param isVirtual the is_virtual value
	 */
	public void setIsVirtual (java.lang.Boolean isVirtual) {
		this.isVirtual = isVirtual;
	}



	/**
	 * Return the value associated with the column: is_taobao
	 */
	public java.lang.Boolean isIsTaobao () {
		return isTaobao;
	}

	/**
	 * Set the value related to the column: is_taobao
	 * @param isTaobao the is_taobao value
	 */
	public void setIsTaobao (java.lang.Boolean isTaobao) {
		this.isTaobao = isTaobao;
	}



	/**
	 * Return the value associated with the column: is_ex
	 */
	public java.lang.Boolean isIsEx () {
		return isEx;
	}

	/**
	 * Set the value related to the column: is_ex
	 * @param isEx the is_ex value
	 */
	public void setIsEx (java.lang.Boolean isEx) {
		this.isEx = isEx;
	}



	/**
	 * Return the value associated with the column: is_timing
	 */
	public java.lang.Boolean isIsTiming () {
		return isTiming;
	}

	/**
	 * Set the value related to the column: is_timing
	 * @param isTiming the is_timing value
	 */
	public void setIsTiming (java.lang.Boolean isTiming) {
		this.isTiming = isTiming;
	}



	/**
	 * Return the value associated with the column: is_3D
	 */
	public java.lang.Boolean isIs3d () {
		return is3d;
	}

	/**
	 * Set the value related to the column: is_3D
	 * @param is3d the is_3D value
	 */
	public void setIs3d (java.lang.Boolean is3d) {
		this.is3d = is3d;
	}



	/**
	 * Return the value associated with the column: score
	 */
	public java.lang.Long getScore () {
		return score;
	}

	/**
	 * Set the value related to the column: score
	 * @param score the score value
	 */
	public void setScore (java.lang.Long score) {
		this.score = score;
	}



	/**
	 * Return the value associated with the column: volume
	 */
	public java.lang.Long getVolume () {
		return volume;
	}

	/**
	 * Set the value related to the column: volume
	 * @param volume the volume value
	 */
	public void setVolume (java.lang.Long volume) {
		this.volume = volume;
	}



	/**
	 * Return the value associated with the column: one_station
	 */
	public java.lang.Boolean isOneStation () {
		return oneStation;
	}

	/**
	 * Set the value related to the column: one_station
	 * @param oneStation the one_station value
	 */
	public void setOneStation (java.lang.Boolean oneStation) {
		this.oneStation = oneStation;
	}



	/**
	 * Return the value associated with the column: second_kill
	 */
	public java.lang.String getSecondKill () {
		return secondKill;
	}

	/**
	 * Set the value related to the column: second_kill
	 * @param secondKill the second_kill value
	 */
	public void setSecondKill (java.lang.String secondKill) {
		this.secondKill = secondKill;
	}



	/**
	 * Return the value associated with the column: auto_fill
	 */
	public java.lang.String getAutoFill () {
		return autoFill;
	}

	/**
	 * Set the value related to the column: auto_fill
	 * @param autoFill the auto_fill value
	 */
	public void setAutoFill (java.lang.String autoFill) {
		this.autoFill = autoFill;
	}



	/**
	 * Return the value associated with the column: violation
	 */
	public java.lang.Boolean isViolation () {
		return violation;
	}

	/**
	 * Set the value related to the column: violation
	 * @param violation the violation value
	 */
	public void setViolation (java.lang.Boolean violation) {
		this.violation = violation;
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
	 * Return the value associated with the column: is_prepay
	 */
	public java.lang.Boolean isIsPrepay () {
		return isPrepay;
	}

	/**
	 * Set the value related to the column: is_prepay
	 * @param isPrepay the is_prepay value
	 */
	public void setIsPrepay (java.lang.Boolean isPrepay) {
		this.isPrepay = isPrepay;
	}



	/**
	 * Return the value associated with the column: ww_status
	 */
	public java.lang.Boolean isWwStatus () {
		return wwStatus;
	}

	/**
	 * Set the value related to the column: ww_status
	 * @param wwStatus the ww_status value
	 */
	public void setWwStatus (java.lang.Boolean wwStatus) {
		this.wwStatus = wwStatus;
	}



	/**
	 * Return the value associated with the column: wap_desc
	 */
	public java.lang.String getWapDesc () {
		return wapDesc;
	}

	/**
	 * Set the value related to the column: wap_desc
	 * @param wapDesc the wap_desc value
	 */
	public void setWapDesc (java.lang.String wapDesc) {
		this.wapDesc = wapDesc;
	}



	/**
	 * Return the value associated with the column: wap_detail_url
	 */
	public java.lang.String getWapDetailUrl () {
		return wapDetailUrl;
	}

	/**
	 * Set the value related to the column: wap_detail_url
	 * @param wapDetailUrl the wap_detail_url value
	 */
	public void setWapDetailUrl (java.lang.String wapDetailUrl) {
		this.wapDetailUrl = wapDetailUrl;
	}



	/**
	 * Return the value associated with the column: after_sale_id
	 */
	public java.lang.Long getAfterSaleId () {
		return afterSaleId;
	}

	/**
	 * Set the value related to the column: after_sale_id
	 * @param afterSaleId the after_sale_id value
	 */
	public void setAfterSaleId (java.lang.Long afterSaleId) {
		this.afterSaleId = afterSaleId;
	}



	/**
	 * Return the value associated with the column: cod_postage_id
	 */
	public java.lang.Long getCodPostageId () {
		return codPostageId;
	}

	/**
	 * Set the value related to the column: cod_postage_id
	 * @param codPostageId the cod_postage_id value
	 */
	public void setCodPostageId (java.lang.Long codPostageId) {
		this.codPostageId = codPostageId;
	}



	/**
	 * Return the value associated with the column: sell_promise
	 */
	public java.lang.Boolean isSellPromise () {
		return sellPromise;
	}

	/**
	 * Set the value related to the column: sell_promise
	 * @param sellPromise the sell_promise value
	 */
	public void setSellPromise (java.lang.Boolean sellPromise) {
		this.sellPromise = sellPromise;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbItem)) return false;
		else {
			com.sunmw.taobao.entity.TbItem tbItem = (com.sunmw.taobao.entity.TbItem) obj;
			if (null == this.getId() || null == tbItem.getId()) return false;
			else return (this.getId().equals(tbItem.getId()));
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