package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the status_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="status_item"
 */

public abstract class BaseStatusItem  implements Serializable {

	public static String REF = "StatusItem";
	public static String PROP_STATUS_CODE = "StatusCode";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_STATUS_TYPE_ID = "StatusTypeId";
	public static String PROP_ID = "Id";
	public static String PROP_SEQUENCE = "Sequence";


	// constructors
	public BaseStatusItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseStatusItem (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String statusTypeId;
	private java.lang.String statusCode;
	private java.lang.Integer sequence;
	private java.lang.String description;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="STATUS_ID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: STATUS_TYPE_ID
	 */
	public java.lang.String getStatusTypeId () {
		return statusTypeId;
	}

	/**
	 * Set the value related to the column: STATUS_TYPE_ID
	 * @param statusTypeId the STATUS_TYPE_ID value
	 */
	public void setStatusTypeId (java.lang.String statusTypeId) {
		this.statusTypeId = statusTypeId;
	}



	/**
	 * Return the value associated with the column: STATUS_CODE
	 */
	public java.lang.String getStatusCode () {
		return statusCode;
	}

	/**
	 * Set the value related to the column: STATUS_CODE
	 * @param statusCode the STATUS_CODE value
	 */
	public void setStatusCode (java.lang.String statusCode) {
		this.statusCode = statusCode;
	}



	/**
	 * Return the value associated with the column: SEQUENCE
	 */
	public java.lang.Integer getSequence () {
		return sequence;
	}

	/**
	 * Set the value related to the column: SEQUENCE
	 * @param sequence the SEQUENCE value
	 */
	public void setSequence (java.lang.Integer sequence) {
		this.sequence = sequence;
	}



	/**
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * @param description the DESCRIPTION value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.StatusItem)) return false;
		else {
			com.sunmw.web.entity.StatusItem statusItem = (com.sunmw.web.entity.StatusItem) obj;
			if (null == this.getId() || null == statusItem.getId()) return false;
			else return (this.getId().equals(statusItem.getId()));
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