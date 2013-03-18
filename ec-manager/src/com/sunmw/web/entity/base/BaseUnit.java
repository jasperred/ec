package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the unit table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="unit"
 */

public abstract class BaseUnit  implements Serializable {

	public static String REF = "Unit";
	public static String PROP_MUST_MEMO = "MustMemo";
	public static String PROP_UNIT_NAME = "UnitName";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_UNIT_NO = "UnitNo";
	public static String PROP_MEMO = "Memo";
	public static String PROP_PARENT_UNIT_NO = "ParentUnitNo";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_DISP_INDEX = "DispIndex";
	public static String PROP_UNIT_TYPE = "UnitType";


	// constructors
	public BaseUnit () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUnit (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String unitType;
	private java.lang.String unitNo;
	private java.lang.String parentUnitNo;
	private java.lang.String unitName;
	private java.lang.String mustMemo;
	private java.lang.String memo;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;
	private java.lang.Integer dispIndex;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="unit_id"
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
	 * Return the value associated with the column: unit_type
	 */
	public java.lang.String getUnitType () {
		return unitType;
	}

	/**
	 * Set the value related to the column: unit_type
	 * @param unitType the unit_type value
	 */
	public void setUnitType (java.lang.String unitType) {
		this.unitType = unitType;
	}



	/**
	 * Return the value associated with the column: unit_no
	 */
	public java.lang.String getUnitNo () {
		return unitNo;
	}

	/**
	 * Set the value related to the column: unit_no
	 * @param unitNo the unit_no value
	 */
	public void setUnitNo (java.lang.String unitNo) {
		this.unitNo = unitNo;
	}



	/**
	 * Return the value associated with the column: parent_unit_no
	 */
	public java.lang.String getParentUnitNo () {
		return parentUnitNo;
	}

	/**
	 * Set the value related to the column: parent_unit_no
	 * @param parentUnitNo the parent_unit_no value
	 */
	public void setParentUnitNo (java.lang.String parentUnitNo) {
		this.parentUnitNo = parentUnitNo;
	}



	/**
	 * Return the value associated with the column: unit_name
	 */
	public java.lang.String getUnitName () {
		return unitName;
	}

	/**
	 * Set the value related to the column: unit_name
	 * @param unitName the unit_name value
	 */
	public void setUnitName (java.lang.String unitName) {
		this.unitName = unitName;
	}



	/**
	 * Return the value associated with the column: must_memo
	 */
	public java.lang.String getMustMemo () {
		return mustMemo;
	}

	/**
	 * Set the value related to the column: must_memo
	 * @param mustMemo the must_memo value
	 */
	public void setMustMemo (java.lang.String mustMemo) {
		this.mustMemo = mustMemo;
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
	 * Return the value associated with the column: cuser
	 */
	public java.lang.String getCuser () {
		return cuser;
	}

	/**
	 * Set the value related to the column: cuser
	 * @param cuser the cuser value
	 */
	public void setCuser (java.lang.String cuser) {
		this.cuser = cuser;
	}



	/**
	 * Return the value associated with the column: muser
	 */
	public java.lang.String getMuser () {
		return muser;
	}

	/**
	 * Set the value related to the column: muser
	 * @param muser the muser value
	 */
	public void setMuser (java.lang.String muser) {
		this.muser = muser;
	}



	/**
	 * Return the value associated with the column: disp_index
	 */
	public java.lang.Integer getDispIndex () {
		return dispIndex;
	}

	/**
	 * Set the value related to the column: disp_index
	 * @param dispIndex the disp_index value
	 */
	public void setDispIndex (java.lang.Integer dispIndex) {
		this.dispIndex = dispIndex;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.Unit)) return false;
		else {
			com.sunmw.web.entity.Unit unit = (com.sunmw.web.entity.Unit) obj;
			if (null == this.getId() || null == unit.getId()) return false;
			else return (this.getId().equals(unit.getId()));
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