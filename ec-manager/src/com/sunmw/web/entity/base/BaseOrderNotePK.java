package com.sunmw.web.entity.base;

import java.io.Serializable;


public abstract class BaseOrderNotePK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String noteType;
	private java.lang.Integer orderHeadId;


	public BaseOrderNotePK () {}
	
	public BaseOrderNotePK (
		java.lang.String noteType,
		java.lang.Integer orderHeadId) {

		this.setNoteType(noteType);
		this.setOrderHeadId(orderHeadId);
	}


	/**
	 * Return the value associated with the column: note_type
	 */
	public java.lang.String getNoteType () {
		return noteType;
	}

	/**
	 * Set the value related to the column: note_type
	 * @param noteType the note_type value
	 */
	public void setNoteType (java.lang.String noteType) {
		this.noteType = noteType;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.OrderNotePK)) return false;
		else {
			com.sunmw.web.entity.OrderNotePK mObj = (com.sunmw.web.entity.OrderNotePK) obj;
			if (null != this.getNoteType() && null != mObj.getNoteType()) {
				if (!this.getNoteType().equals(mObj.getNoteType())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getOrderHeadId() && null != mObj.getOrderHeadId()) {
				if (!this.getOrderHeadId().equals(mObj.getOrderHeadId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getNoteType()) {
				sb.append(this.getNoteType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getOrderHeadId()) {
				sb.append(this.getOrderHeadId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}