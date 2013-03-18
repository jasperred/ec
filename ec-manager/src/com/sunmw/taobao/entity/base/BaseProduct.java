package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the Product table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="Product"
 */

public abstract class BaseProduct  implements Serializable {

	public static String REF = "Product";
	public static String PROP_OUTER_ID = "OuterId";
	public static String PROP_PUBLISH_ERROR_MSG = "PublishErrorMsg";
	public static String PROP_PUBLISH_TIME = "PublishTime";
	public static String PROP_DEFAULT_SKU = "DefaultSku";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_CUSTOMER_PROPS = "CustomerProps";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	public static String PROP_PROPERTY_ALIAS = "PropertyAlias";
	public static String PROP_BINDS = "Binds";
	public static String PROP_PROPS_BACK = "PropsBack";
	public static String PROP_SALE_PROPS = "SaleProps";
	public static String PROP_NAME = "Name";
	public static String PROP_ACTION = "Action";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_TB_PRODUCT_ID = "TbProductId";
	public static String PROP_CREATE_TIME = "CreateTime";
	public static String PROP_CID = "Cid";
	public static String PROP_PRICE = "Price";
	public static String PROP_PROPS = "Props";
	public static String PROP_ID = "Id";


	// constructors
	public BaseProduct () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseProduct (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseProduct (
		java.lang.Integer id,
		java.lang.String action,
		java.lang.String publishErrorMsg) {

		this.setId(id);
		this.setAction(action);
		this.setPublishErrorMsg(publishErrorMsg);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String action;
	private java.lang.String publishErrorMsg;
	private java.lang.Long tbProductId;
	private java.lang.String outerId;
	private java.lang.Long cid;
	private java.lang.String props;
	private java.lang.String propsBack;
	private java.lang.String name;
	private java.lang.String binds;
	private java.lang.String saleProps;
	private java.math.BigDecimal price;
	private java.lang.String customerProps;
	private java.lang.String propertyAlias;
	private java.lang.Integer companyId;
	private java.lang.Integer storeId;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private java.util.Date publishTime;
	private java.lang.String defaultSku;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="Product_id"
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
	 * Return the value associated with the column: Publish_error_msg
	 */
	public java.lang.String getPublishErrorMsg () {
		return publishErrorMsg;
	}

	/**
	 * Set the value related to the column: Publish_error_msg
	 * @param publishErrorMsg the Publish_error_msg value
	 */
	public void setPublishErrorMsg (java.lang.String publishErrorMsg) {
		this.publishErrorMsg = publishErrorMsg;
	}



	/**
	 * Return the value associated with the column: Tb_product_id
	 */
	public java.lang.Long getTbProductId () {
		return tbProductId;
	}

	/**
	 * Set the value related to the column: Tb_product_id
	 * @param tbProductId the Tb_product_id value
	 */
	public void setTbProductId (java.lang.Long tbProductId) {
		this.tbProductId = tbProductId;
	}



	/**
	 * Return the value associated with the column: Outer_id
	 */
	public java.lang.String getOuterId () {
		return outerId;
	}

	/**
	 * Set the value related to the column: Outer_id
	 * @param outerId the Outer_id value
	 */
	public void setOuterId (java.lang.String outerId) {
		this.outerId = outerId;
	}



	/**
	 * Return the value associated with the column: Cid
	 */
	public java.lang.Long getCid () {
		return cid;
	}

	/**
	 * Set the value related to the column: Cid
	 * @param cid the Cid value
	 */
	public void setCid (java.lang.Long cid) {
		this.cid = cid;
	}



	/**
	 * Return the value associated with the column: Props
	 */
	public java.lang.String getProps () {
		return props;
	}

	/**
	 * Set the value related to the column: Props
	 * @param props the Props value
	 */
	public void setProps (java.lang.String props) {
		this.props = props;
	}



	/**
	 * Return the value associated with the column: Props_back
	 */
	public java.lang.String getPropsBack () {
		return propsBack;
	}

	/**
	 * Set the value related to the column: Props_back
	 * @param propsBack the Props_back value
	 */
	public void setPropsBack (java.lang.String propsBack) {
		this.propsBack = propsBack;
	}



	/**
	 * Return the value associated with the column: Name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: Name
	 * @param name the Name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: Binds
	 */
	public java.lang.String getBinds () {
		return binds;
	}

	/**
	 * Set the value related to the column: Binds
	 * @param binds the Binds value
	 */
	public void setBinds (java.lang.String binds) {
		this.binds = binds;
	}



	/**
	 * Return the value associated with the column: Sale_props
	 */
	public java.lang.String getSaleProps () {
		return saleProps;
	}

	/**
	 * Set the value related to the column: Sale_props
	 * @param saleProps the Sale_props value
	 */
	public void setSaleProps (java.lang.String saleProps) {
		this.saleProps = saleProps;
	}



	/**
	 * Return the value associated with the column: Price
	 */
	public java.math.BigDecimal getPrice () {
		return price;
	}

	/**
	 * Set the value related to the column: Price
	 * @param price the Price value
	 */
	public void setPrice (java.math.BigDecimal price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: Customer_props
	 */
	public java.lang.String getCustomerProps () {
		return customerProps;
	}

	/**
	 * Set the value related to the column: Customer_props
	 * @param customerProps the Customer_props value
	 */
	public void setCustomerProps (java.lang.String customerProps) {
		this.customerProps = customerProps;
	}



	/**
	 * Return the value associated with the column: Property_alias
	 */
	public java.lang.String getPropertyAlias () {
		return propertyAlias;
	}

	/**
	 * Set the value related to the column: Property_alias
	 * @param propertyAlias the Property_alias value
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
	 * Return the value associated with the column: Default_Sku
	 */
	public java.lang.String getDefaultSku () {
		return defaultSku;
	}

	/**
	 * Set the value related to the column: Default_Sku
	 * @param defaultSku the Default_Sku value
	 */
	public void setDefaultSku (java.lang.String defaultSku) {
		this.defaultSku = defaultSku;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.Product)) return false;
		else {
			com.sunmw.taobao.entity.Product product = (com.sunmw.taobao.entity.Product) obj;
			if (null == this.getId() || null == product.getId()) return false;
			else return (this.getId().equals(product.getId()));
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