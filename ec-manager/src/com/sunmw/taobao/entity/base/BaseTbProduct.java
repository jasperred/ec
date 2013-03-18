package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_product table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_product"
 */

public abstract class BaseTbProduct  implements Serializable {

	public static String REF = "TbProduct";
	public static String PROP_BINDS = "Binds";
	public static String PROP_SALE_PROPS_STR = "SalePropsStr";
	public static String PROP_BINDS_STR = "BindsStr";
	public static String PROP_PIC_URL = "PicUrl";
	public static String PROP_CREATED = "Created";
	public static String PROP_PIC_PATH = "PicPath";
	public static String PROP_MODIFIED = "Modified";
	public static String PROP_CID = "Cid";
	public static String PROP_COLLECT_NUM = "CollectNum";
	public static String PROP_OUTER_ID = "OuterId";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CAT_NAME = "CatName";
	public static String PROP_VERTICAL_MARKET = "VerticalMarket";
	public static String PROP_CUSTOMER_PROPS = "CustomerProps";
	public static String PROP_PROPERTY_ALIAS = "PropertyAlias";
	public static String PROP_SALE_PROPS = "SaleProps";
	public static String PROP_NAME = "Name";
	public static String PROP_STATUS = "Status";
	public static String PROP_TB_PRODUCT_ID = "TbProductId";
	public static String PROP_PRICE = "Price";
	public static String PROP_PROPS = "Props";
	public static String PROP_TSC = "Tsc";
	public static String PROP_PROPS_STR = "PropsStr";
	public static String PROP_LEVEL = "Level";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTbProduct () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbProduct (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbProductId;
	private java.lang.String outerId;
	private java.util.Date created;
	private java.lang.String tsc;
	private java.lang.Long cid;
	private java.lang.String catName;
	private java.lang.String props;
	private java.lang.String propsStr;
	private java.lang.String bindsStr;
	private java.lang.String salePropsStr;
	private java.lang.Long collectNum;
	private java.lang.String name;
	private java.lang.String binds;
	private java.lang.String saleProps;
	private java.math.BigDecimal price;
	private java.lang.String description;
	private java.lang.String picUrl;
	private java.util.Date modified;
	private java.lang.Long status;
	private java.lang.Long level;
	private java.lang.String picPath;
	private java.lang.Long verticalMarket;
	private java.lang.String customerProps;
	private java.lang.String propertyAlias;



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
	 * Return the value associated with the column: tb_product_id
	 */
	public java.lang.Long getTbProductId () {
		return tbProductId;
	}

	/**
	 * Set the value related to the column: tb_product_id
	 * @param tbProductId the tb_product_id value
	 */
	public void setTbProductId (java.lang.Long tbProductId) {
		this.tbProductId = tbProductId;
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
	 * Return the value associated with the column: tsc
	 */
	public java.lang.String getTsc () {
		return tsc;
	}

	/**
	 * Set the value related to the column: tsc
	 * @param tsc the tsc value
	 */
	public void setTsc (java.lang.String tsc) {
		this.tsc = tsc;
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
	 * Return the value associated with the column: cat_name
	 */
	public java.lang.String getCatName () {
		return catName;
	}

	/**
	 * Set the value related to the column: cat_name
	 * @param catName the cat_name value
	 */
	public void setCatName (java.lang.String catName) {
		this.catName = catName;
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
	 * Return the value associated with the column: props_str
	 */
	public java.lang.String getPropsStr () {
		return propsStr;
	}

	/**
	 * Set the value related to the column: props_str
	 * @param propsStr the props_str value
	 */
	public void setPropsStr (java.lang.String propsStr) {
		this.propsStr = propsStr;
	}



	/**
	 * Return the value associated with the column: binds_str
	 */
	public java.lang.String getBindsStr () {
		return bindsStr;
	}

	/**
	 * Set the value related to the column: binds_str
	 * @param bindsStr the binds_str value
	 */
	public void setBindsStr (java.lang.String bindsStr) {
		this.bindsStr = bindsStr;
	}



	/**
	 * Return the value associated with the column: sale_props_str
	 */
	public java.lang.String getSalePropsStr () {
		return salePropsStr;
	}

	/**
	 * Set the value related to the column: sale_props_str
	 * @param salePropsStr the sale_props_str value
	 */
	public void setSalePropsStr (java.lang.String salePropsStr) {
		this.salePropsStr = salePropsStr;
	}



	/**
	 * Return the value associated with the column: collect_num
	 */
	public java.lang.Long getCollectNum () {
		return collectNum;
	}

	/**
	 * Set the value related to the column: collect_num
	 * @param collectNum the collect_num value
	 */
	public void setCollectNum (java.lang.Long collectNum) {
		this.collectNum = collectNum;
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
	 * Return the value associated with the column: binds
	 */
	public java.lang.String getBinds () {
		return binds;
	}

	/**
	 * Set the value related to the column: binds
	 * @param binds the binds value
	 */
	public void setBinds (java.lang.String binds) {
		this.binds = binds;
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
	 * Return the value associated with the column: status
	 */
	public java.lang.Long getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.Long status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: level
	 */
	public java.lang.Long getLevel () {
		return level;
	}

	/**
	 * Set the value related to the column: level
	 * @param level the level value
	 */
	public void setLevel (java.lang.Long level) {
		this.level = level;
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
	 * Return the value associated with the column: vertical_market
	 */
	public java.lang.Long getVerticalMarket () {
		return verticalMarket;
	}

	/**
	 * Set the value related to the column: vertical_market
	 * @param verticalMarket the vertical_market value
	 */
	public void setVerticalMarket (java.lang.Long verticalMarket) {
		this.verticalMarket = verticalMarket;
	}



	/**
	 * Return the value associated with the column: customer_props
	 */
	public java.lang.String getCustomerProps () {
		return customerProps;
	}

	/**
	 * Set the value related to the column: customer_props
	 * @param customerProps the customer_props value
	 */
	public void setCustomerProps (java.lang.String customerProps) {
		this.customerProps = customerProps;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbProduct)) return false;
		else {
			com.sunmw.taobao.entity.TbProduct tbProduct = (com.sunmw.taobao.entity.TbProduct) obj;
			if (null == this.getId() || null == tbProduct.getId()) return false;
			else return (this.getId().equals(tbProduct.getId()));
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