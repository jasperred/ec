package com.sunmw.web.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;

public class ExpandSQLServerDialect extends SQLServerDialect {
	
	public ExpandSQLServerDialect()
	{
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}

}
