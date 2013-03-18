package com.sunmw.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class WebConfigProperties {
	static Logger logger = Logger.getLogger(WebConfigProperties.class);
	private static Properties pro;

	private static final WebConfigProperties pos = new WebConfigProperties();

	/**
	 * 
	 */

	public WebConfigProperties() {
		InputStream is = getClass().getResourceAsStream("/com/sunmw/web/cfg/webconfig.properties");

		pro = new Properties();

		try {

			pro.load(is);

		}

		catch (IOException ex) {

			ex.printStackTrace();

		}
	}

	public static String getProperties(String properties) {

		return pos.pro.getProperty(properties);

	}
}
