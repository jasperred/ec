package com.sunmw.web.entity;

import com.sunmw.web.entity.base.BaseTaskDefine;



public class TaskDefine extends BaseTaskDefine {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TaskDefine () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TaskDefine (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TaskDefine (
		java.lang.Long id,
		java.lang.Long ifId,
		java.util.Date startDate,
		java.util.Date endDate) {

		super (
			id,
			ifId,
			startDate,
			endDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}