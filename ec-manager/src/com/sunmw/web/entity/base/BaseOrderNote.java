package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the order_note table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="order_note"
 */

public abstract class BaseOrderNote  implements Serializable {

	public static String REF = "OrderNote";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_NOTE = "Note";


	// constructors
	public BaseOrderNote () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOrderNote (com.sunmw.web.entity.OrderNotePK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.sunmw.web.entity.OrderNotePK id;

	// fields
	private java.lang.String note;
	private java.util.Date ctime;
	private java.util.Date mtime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.sunmw.web.entity.OrderNotePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.sunmw.web.entity.OrderNotePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: note
	 */
	public java.lang.String getNote () {
		return note;
	}

	/**
	 * Set the value related to the column: note
	 * @param note the note value
	 */
	public void setNote (java.lang.String note) {
		this.note = note;
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
		if (!(obj instanceof com.sunmw.web.entity.OrderNote)) return false;
		else {
			com.sunmw.web.entity.OrderNote orderNote = (com.sunmw.web.entity.OrderNote) obj;
			if (null == this.getId() || null == orderNote.getId()) return false;
			else return (this.getId().equals(orderNote.getId()));
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