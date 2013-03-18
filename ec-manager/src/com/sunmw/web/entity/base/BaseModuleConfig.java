package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the module_config table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="module_config"
 */

public abstract class BaseModuleConfig  implements Serializable {

	public static String REF = "ModuleConfig";
	public static String PROP_MODULE_NAME = "ModuleName";
	public static String PROP_MODULE_TYPE = "ModuleType";
	public static String PROP_SHOW_MENU = "ShowMenu";
	public static String PROP_URL_PARAM = "UrlParam";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_URL = "Url";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_PERMISSION_ID = "PermissionId";
	public static String PROP_ID = "Id";
	public static String PROP_MODULE_ALIAS = "ModuleAlias";
	public static String PROP_PARENT_MODULE_ID = "ParentModuleId";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_SEQUENCE = "Sequence";
	public static String PROP_IMAGE_PATH = "ImagePath";


	// constructors
	public BaseModuleConfig () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseModuleConfig (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String moduleName;
	private java.lang.String moduleAlias;
	private java.lang.String moduleType;
	private java.lang.Integer parentModuleId;
	private java.lang.String showMenu;
	private java.lang.String urlParam;
	private java.lang.String url;
	private java.lang.Integer sequence;
	private java.lang.String permissionId;
	private java.lang.String imagePath;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="module_id"
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
	 * Return the value associated with the column: module_name
	 */
	public java.lang.String getModuleName () {
		return moduleName;
	}

	/**
	 * Set the value related to the column: module_name
	 * @param moduleName the module_name value
	 */
	public void setModuleName (java.lang.String moduleName) {
		this.moduleName = moduleName;
	}



	/**
	 * Return the value associated with the column: module_alias
	 */
	public java.lang.String getModuleAlias () {
		return moduleAlias;
	}

	/**
	 * Set the value related to the column: module_alias
	 * @param moduleAlias the module_alias value
	 */
	public void setModuleAlias (java.lang.String moduleAlias) {
		this.moduleAlias = moduleAlias;
	}



	/**
	 * Return the value associated with the column: module_type
	 */
	public java.lang.String getModuleType () {
		return moduleType;
	}

	/**
	 * Set the value related to the column: module_type
	 * @param moduleType the module_type value
	 */
	public void setModuleType (java.lang.String moduleType) {
		this.moduleType = moduleType;
	}



	/**
	 * Return the value associated with the column: parent_module_id
	 */
	public java.lang.Integer getParentModuleId () {
		return parentModuleId;
	}

	/**
	 * Set the value related to the column: parent_module_id
	 * @param parentModuleId the parent_module_id value
	 */
	public void setParentModuleId (java.lang.Integer parentModuleId) {
		this.parentModuleId = parentModuleId;
	}



	/**
	 * Return the value associated with the column: show_menu
	 */
	public java.lang.String getShowMenu () {
		return showMenu;
	}

	/**
	 * Set the value related to the column: show_menu
	 * @param showMenu the show_menu value
	 */
	public void setShowMenu (java.lang.String showMenu) {
		this.showMenu = showMenu;
	}



	/**
	 * Return the value associated with the column: url_param
	 */
	public java.lang.String getUrlParam () {
		return urlParam;
	}

	/**
	 * Set the value related to the column: url_param
	 * @param urlParam the url_param value
	 */
	public void setUrlParam (java.lang.String urlParam) {
		this.urlParam = urlParam;
	}



	/**
	 * Return the value associated with the column: url
	 */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
	}



	/**
	 * Return the value associated with the column: sequence
	 */
	public java.lang.Integer getSequence () {
		return sequence;
	}

	/**
	 * Set the value related to the column: sequence
	 * @param sequence the sequence value
	 */
	public void setSequence (java.lang.Integer sequence) {
		this.sequence = sequence;
	}



	/**
	 * Return the value associated with the column: permission_id
	 */
	public java.lang.String getPermissionId () {
		return permissionId;
	}

	/**
	 * Set the value related to the column: permission_id
	 * @param permissionId the permission_id value
	 */
	public void setPermissionId (java.lang.String permissionId) {
		this.permissionId = permissionId;
	}



	/**
	 * Return the value associated with the column: image_path
	 */
	public java.lang.String getImagePath () {
		return imagePath;
	}

	/**
	 * Set the value related to the column: image_path
	 * @param imagePath the image_path value
	 */
	public void setImagePath (java.lang.String imagePath) {
		this.imagePath = imagePath;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.ModuleConfig)) return false;
		else {
			com.sunmw.web.entity.ModuleConfig moduleConfig = (com.sunmw.web.entity.ModuleConfig) obj;
			if (null == this.getId() || null == moduleConfig.getId()) return false;
			else return (this.getId().equals(moduleConfig.getId()));
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