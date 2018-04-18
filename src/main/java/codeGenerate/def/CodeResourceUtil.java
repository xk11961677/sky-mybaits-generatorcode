package codeGenerate.def;

import java.util.ResourceBundle;

public class CodeResourceUtil{

	private static final ResourceBundle bundle = ResourceBundle.getBundle("database");
	private static final ResourceBundle bundlePath = ResourceBundle.getBundle("config");
	public static String DIVER_NAME = "com.mysql.jdbc.Driver";
	public static String URL;
	public static String USERNAME = "credit";
	public static String PASSWORD = "credit";
	public static String DATABASE_NAME = "payment";
	public static String DATABASE_TYPE = "mysql";
	public static String DATABASE_TYPE_MYSQL;
	public static String DATABASE_TYPE_ORACLE;
	public static String page_package;
	public static String ENTITY_URL;
	public static String PAGE_URL;
	public static String ENTITY_URL_INX;
	public static String PAGE_URL_INX;
	public static String CODEPATH;
	public static String JSPPATH;
	public static String JEECG_GENERATE_UI_FILTER_FIELDS;
	public static String SYSTEM_ENCODING = getSYSTEM_ENCODING();

	public CodeResourceUtil() {
	}

	public static final String getDIVER_NAME() {
		return bundle.getString("diver_name");
	}

	public static final String getURL() {
		return bundle.getString("url");
	}

	public static final String getUSERNAME() {
		return bundle.getString("username");
	}

	public static final String getPASSWORD() {
		return bundle.getString("password");
	}

	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name");
	}

	public static final String getEntityPackage() {
		return bundlePath.getString("entity_package");
	}

	public static final String getPagePackage() {
		return bundlePath.getString("page_package");
	}
	
	public static final String getTEMPLATEPATH() {
		return bundlePath.getString("templatepath_ftl");
	}

	public static final String getSourceRootPackage() {
		return bundlePath.getString("source_root_package");
	}

	public static final String getSYSTEM_ENCODING() {
		return bundlePath.getString("system_encoding");
	}

	public static final String getGenerate_ui_filter_fields() {
		return bundlePath.getString("generate_ui_filter_fields");
	}

	public static final String getUi_field_required_num() {
		return bundlePath.getString("ui_field_required_num");
	}

	public static final String getConfigInfo(String key) {
		return bundlePath.getString(key);
	}

	static {
		DATABASE_TYPE_MYSQL = "mysql";
		DATABASE_TYPE_ORACLE = "oracle";
		page_package = "page";
		DIVER_NAME = getDIVER_NAME();
		URL = getURL();
		USERNAME = getUSERNAME();
		PASSWORD = getPASSWORD();
		DATABASE_NAME = getDATABASE_NAME();
		if (URL.indexOf("mysql") >= 0 || URL.indexOf("MYSQL") >= 0)
			DATABASE_TYPE = DATABASE_TYPE_MYSQL;
		else if (URL.indexOf("oracle") >= 0 || URL.indexOf("ORACLE") >= 0)
			DATABASE_TYPE = DATABASE_TYPE_ORACLE;
	}
}
