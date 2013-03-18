package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the order_payment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="order_payment"
 */

public abstract class BaseOrderPayment  implements Serializable {

	public static String REF = "OrderPayment";
	public static String PROP_FREIGHT_AMT = "FreightAmt";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_REFUND_AMT = "RefundAmt";
	public static String PROP_PAYMENT_AMT = "PaymentAmt";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_ACTUAL_TOTAL_AMT = "ActualTotalAmt";


	// constructors
	public BaseOrderPayment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrderPayment (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.math.BigDecimal actualTotalAmt;
	private java.math.BigDecimal paymentAmt;
	private java.math.BigDecimal freightAmt;
	private java.math.BigDecimal refundAmt;
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
	 * Return the value associated with the column: actual_total_amt
	 */
	public java.math.BigDecimal getActualTotalAmt () {
		return actualTotalAmt;
	}

	/**
	 * Set the value related to the column: actual_total_amt
	 * @param actualTotalAmt the actual_total_amt value
	 */
	public void setActualTotalAmt (java.math.BigDecimal actualTotalAmt) {
		this.actualTotalAmt = actualTotalAmt;
	}



	/**
	 * Return the value associated with the column: payment_amt
	 */
	public java.math.BigDecimal getPaymentAmt () {
		return paymentAmt;
	}

	/**
	 * Set the value related to the column: payment_amt
	 * @param paymentAmt the payment_amt value
	 */
	public void setPaymentAmt (java.math.BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}



	/**
	 * Return the value associated with the column: freight_amt
	 */
	public java.math.BigDecimal getFreightAmt () {
		return freightAmt;
	}

	/**
	 * Set the value related to the column: freight_amt
	 * @param freightAmt the freight_amt value
	 */
	public void setFreightAmt (java.math.BigDecimal freightAmt) {
		this.freightAmt = freightAmt;
	}



	/**
	 * Return the value associated with the column: refund_amt
	 */
	public java.math.BigDecimal getRefundAmt () {
		return refundAmt;
	}

	/**
	 * Set the value related to the column: refund_amt
	 * @param refundAmt the refund_amt value
	 */
	public void setRefundAmt (java.math.BigDecimal refundAmt) {
		this.refundAmt = refundAmt;
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
		if (!(obj instanceof com.sunmw.web.entity.OrderPayment)) return false;
		else {
			com.sunmw.web.entity.OrderPayment orderPayment = (com.sunmw.web.entity.OrderPayment) obj;
			if (null == this.getId() || null == orderPayment.getId()) return false;
			else return (this.getId().equals(orderPayment.getId()));
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