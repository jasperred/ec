package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_trade_account_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_trade_account_detail"
 */

public abstract class BaseTbTradeAccountDetail  implements Serializable {

	public static String REF = "TbTradeAccountDetail";
	public static String PROP_TRADE_PARTNER = "TradePartner";
	public static String PROP_EXPENSE = "Expense";
	public static String PROP_TAOBAO_TID = "TaobaoTid";
	public static String PROP_TRADE_LOCALE = "TradeLocale";
	public static String PROP_DATE = "Date";
	public static String PROP_TYPE = "Type";
	public static String PROP_MEMO = "Memo";
	public static String PROP_ITEM_NAME = "ItemName";
	public static String PROP_ACCOUNT_BALANCE = "AccountBalance";
	public static String PROP_ID = "Id";
	public static String PROP_ALIPAY_TID = "AlipayTid";
	public static String PROP_INCOME = "Income";


	// constructors
	public BaseTbTradeAccountDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbTradeAccountDetail (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer taobaoTid;
	private java.lang.String alipayTid;
	private java.lang.String date;
	private java.math.BigDecimal accountBalance;
	private java.math.BigDecimal income;
	private java.math.BigDecimal expense;
	private java.lang.String tradePartner;
	private java.lang.String tradeLocale;
	private java.lang.String itemName;
	private java.lang.String type;
	private java.lang.String memo;



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
	 * Return the value associated with the column: taobao_tid
	 */
	public java.lang.Integer getTaobaoTid () {
		return taobaoTid;
	}

	/**
	 * Set the value related to the column: taobao_tid
	 * @param taobaoTid the taobao_tid value
	 */
	public void setTaobaoTid (java.lang.Integer taobaoTid) {
		this.taobaoTid = taobaoTid;
	}



	/**
	 * Return the value associated with the column: alipay_tid
	 */
	public java.lang.String getAlipayTid () {
		return alipayTid;
	}

	/**
	 * Set the value related to the column: alipay_tid
	 * @param alipayTid the alipay_tid value
	 */
	public void setAlipayTid (java.lang.String alipayTid) {
		this.alipayTid = alipayTid;
	}



	/**
	 * Return the value associated with the column: date
	 */
	public java.lang.String getDate () {
		return date;
	}

	/**
	 * Set the value related to the column: date
	 * @param date the date value
	 */
	public void setDate (java.lang.String date) {
		this.date = date;
	}



	/**
	 * Return the value associated with the column: account_balance
	 */
	public java.math.BigDecimal getAccountBalance () {
		return accountBalance;
	}

	/**
	 * Set the value related to the column: account_balance
	 * @param accountBalance the account_balance value
	 */
	public void setAccountBalance (java.math.BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}



	/**
	 * Return the value associated with the column: income
	 */
	public java.math.BigDecimal getIncome () {
		return income;
	}

	/**
	 * Set the value related to the column: income
	 * @param income the income value
	 */
	public void setIncome (java.math.BigDecimal income) {
		this.income = income;
	}



	/**
	 * Return the value associated with the column: expense
	 */
	public java.math.BigDecimal getExpense () {
		return expense;
	}

	/**
	 * Set the value related to the column: expense
	 * @param expense the expense value
	 */
	public void setExpense (java.math.BigDecimal expense) {
		this.expense = expense;
	}



	/**
	 * Return the value associated with the column: trade_partner
	 */
	public java.lang.String getTradePartner () {
		return tradePartner;
	}

	/**
	 * Set the value related to the column: trade_partner
	 * @param tradePartner the trade_partner value
	 */
	public void setTradePartner (java.lang.String tradePartner) {
		this.tradePartner = tradePartner;
	}



	/**
	 * Return the value associated with the column: trade_locale
	 */
	public java.lang.String getTradeLocale () {
		return tradeLocale;
	}

	/**
	 * Set the value related to the column: trade_locale
	 * @param tradeLocale the trade_locale value
	 */
	public void setTradeLocale (java.lang.String tradeLocale) {
		this.tradeLocale = tradeLocale;
	}



	/**
	 * Return the value associated with the column: item_name
	 */
	public java.lang.String getItemName () {
		return itemName;
	}

	/**
	 * Set the value related to the column: item_name
	 * @param itemName the item_name value
	 */
	public void setItemName (java.lang.String itemName) {
		this.itemName = itemName;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbTradeAccountDetail)) return false;
		else {
			com.sunmw.taobao.entity.TbTradeAccountDetail tbTradeAccountDetail = (com.sunmw.taobao.entity.TbTradeAccountDetail) obj;
			if (null == this.getId() || null == tbTradeAccountDetail.getId()) return false;
			else return (this.getId().equals(tbTradeAccountDetail.getId()));
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