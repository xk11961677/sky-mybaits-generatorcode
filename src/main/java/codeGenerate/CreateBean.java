package codeGenerate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.StringUtils;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.TableConvert;

public class CreateBean{

	static String url;
	static String username;
	static String password;
	static String databaseName;
	static String rt = "\r\t";
	String SQLTables;
	private String method;
	private String argv;
	static String selectStr = "select ";
	static String from = " from ";
	private List<String> beanFieldDataTypes;

	public CreateBean() {
		SQLTables = "show tables";
	}

	@SuppressWarnings("static-access")
	public void setMysqlInfo(String url, String username, String password,String databaseName) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
		this.beanFieldDataTypes = new ArrayList<String>();
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public List<String> getTables() throws SQLException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		List<String> list = new ArrayList<String>();
		String tableName;
		for (; rs.next(); list.add(tableName))
			tableName = rs.getString(1).toLowerCase();

		rs.close();
		ps.close();
		con.close();
		return list;
	}

	public List<TableInfo> getTablesInfo() throws SQLException {
		//String SQLTables = (new StringBuilder("select t.TABLE_NAME ,t.COMMENTS from user_tab_comments  t order by t.TABLE_NAME")).toString();
		String SQLTables = (new StringBuilder("SELECT T.TABLE_NAME ,T.TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES  T  WHERE TABLE_SCHEMA = '").append(databaseName).append("' ORDER BY T.TABLE_NAME")).toString();
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		List<TableInfo> list = new ArrayList<TableInfo>();
		TableInfo tableInfo;
		for (; rs.next(); list.add(tableInfo)) {
			tableInfo = new TableInfo();
			String tableName = rs.getString(1).toLowerCase();
			String tableComment = rs.getString(2);
			tableInfo.setTableName(tableName);
			tableInfo.setTableComment(tableComment);
		}

		rs.close();
		ps.close();
		con.close();
		return list;
	}

	public Map<String, String> getTableCommentMap() throws SQLException {
		//String SQLTables = (new StringBuilder("select t.TABLE_NAME ,t.COMMENTS from user_tab_comments  t order by t.TABLE_NAME")).toString();
		String SQLTables = (new StringBuilder("SELECT T.TABLE_NAME ,T.TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES  T  WHERE TABLE_SCHEMA = '").append(databaseName).append("' ORDER BY T.TABLE_NAME")).toString();
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		Map<String, String> map = new HashMap<String, String>();
		String tableName;
		String tableComment;
		for (; rs.next(); map.put(tableName, tableComment)) {
			tableName = rs.getString(1).toLowerCase();
			tableComment = rs.getString(2);
		}
		rs.close();
		ps.close();
		con.close();
		return map;
	}

	public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
		tableName = tableName.toLowerCase();
//		String SQLColumns = (new StringBuilder(
//				"select a.column_name,b.data_type,a.comments,b.data_precision,b.data_scale,b.char_length,b.NULLABLE,case when c.column_name is not null then 'PRI' else '' end pri "
//						+ "from user_col_comments a "
//						+ "left join user_tab_columns b "
//						+ "  on a.table_name = b.table_name "
//						+ "    and a.column_name=b.column_name "
//						+ "left join (select p.table_name,p.column_name from user_cons_columns p where exists(select 1 from user_constraints t where t.constraint_type='P' and p.constraint_name=t.constraint_name)) c "
//						+ "   on a.table_name=c.table_name and a.column_name=c.column_name "
//						+ " where a.table_name =  '")).append(tableName).append("' order by b.column_id ").toString();
		String SQLColumns = (new StringBuilder(
				"SELECT COLUMN_NAME ,DATA_TYPE,COLUMN_COMMENT,NUMERIC_PRECISION,NUMERIC_SCALE,CHARACTER_MAXIMUM_LENGTH,IS_NULLABLE NULLABLE,COLUMN_KEY "
						+ "FROM INFORMATION_SCHEMA.COLUMNS "
						+ "WHERE TABLE_NAME =  '").append(tableName).append("' AND TABLE_SCHEMA =  '").append(databaseName).append("' ORDER BY ORDINAL_POSITION ")).toString();
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List<ColumnData> columnList = new ArrayList<ColumnData>();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		ColumnData cd;
		for (; rs.next(); columnList.add(cd)) {
			String name = rs.getString(1).toUpperCase();
			String jdbcDataType = rs.getString(2);
			String comment = rs.getString(3);
			comment = comment==null?"":comment;
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = rs.getString(6) != null ? rs.getString(6) : "";
			String nullable = TableConvert.getNullAble(rs.getString(7));
			String columnKey = rs.getString(8) != null ? rs.getString(8) : "";
			String type = getType(jdbcDataType, precision, scale);
			String domainPropertyName = getcolumnNameToDomainPropertyName(name);
			cd = new ColumnData();
			cd.setColumnName(name);
			cd.setDataType(type);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			cd.setDomainPropertyName(domainPropertyName);
			cd.setColumnKey(columnKey.toUpperCase());
			jdbcDataType = jdbcDataType == null ? "" :this.getJdbcDataType(jdbcDataType, precision, scale);
			cd.setJdbcDataType(jdbcDataType);
		}

		argv = str.toString();
		method = getset.toString();
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	public List<ColumnData> getColumnKeyDatas(List<ColumnData> columnList) {
		List<ColumnData> columnKeyList = new ArrayList<ColumnData>();
		if (columnList != null && columnList.size() > 0) {
			for (Iterator<ColumnData> iterator = columnList.iterator(); iterator.hasNext();) {
				ColumnData column = (ColumnData) iterator.next();
				if ("PRI".equals(column.getColumnKey()))
					columnKeyList.add(column);
			}

		}
		return columnKeyList;
	}

	public String getColumnKeyParam(List<ColumnData> columnList) {
		StringBuffer sb = new StringBuffer("");
		if (columnList != null && columnList.size() > 0) {
			ColumnData column;
			for (Iterator<ColumnData> iterator = columnList.iterator(); iterator.hasNext(); sb
					.append(column.getDataType()).append(" ").append(column.getDomainPropertyName()).append(","))
				column = (ColumnData) iterator.next();

		}
		String str = sb.toString();
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}

	public String getColumnKeyUseParam(List<ColumnData> columnList) {
		StringBuffer sb = new StringBuffer("");
		if (columnList != null && columnList.size() > 0) {
			ColumnData column;
			for (Iterator<ColumnData> iterator = columnList.iterator(); iterator.hasNext(); sb.append(
					column.getDomainPropertyName()).append(","))
				column = (ColumnData) iterator.next();

		}
		String str = sb.toString();
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}
	
	public String getColumnKeySort(List<ColumnData> columnList) {
		StringBuffer sb = new StringBuffer("");
		if (columnList != null && columnList.size() > 0) {
			ColumnData column;
			for (Iterator<ColumnData> iterator = columnList.iterator(); iterator.hasNext(); sb.append(
					column.getColumnName()).append(","))
				column = (ColumnData) iterator.next();
		}
		String str = sb.toString();
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}

	@SuppressWarnings("rawtypes")
	public String getBeanFeilds(String tableName, String className) throws SQLException {
		List dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		String name;
		String ignoreGenerateColumnsTemp = CodeResourceUtil.getConfigInfo("ignore_generate_columns");
		ignoreGenerateColumnsTemp = ignoreGenerateColumnsTemp== null ? "": ignoreGenerateColumnsTemp.trim().toUpperCase();
		Set<String> fieldDataSet = new HashSet<String>();
		for (int i=0;i<dataList.size();i++) {
			ColumnData columnData = (ColumnData) dataList.get(i);
			if(!ignoreGenerateColumnsTemp.contains(columnData.getColumnName())){
				name = columnData.getDomainPropertyName();
				String type = columnData.getDataType();
				String comment = columnData.getColumnComment();
				String maxChar = name.substring(0, 1).toUpperCase();
				str.append("\r\t").append("/**");
				str.append("\r\t").append(" *").append(comment);
				str.append("\r\t").append(" */");
				str.append("\r\t").append("private ")
						.append((new StringBuilder(String.valueOf(type))).append(" ").toString()).append(name).append(";");
				String method = (new StringBuilder(String.valueOf(maxChar))).append(name.substring(1, name.length()))
						.toString();
				getset.append("\r\t").append("/**");
				getset.append("\r\t").append(" * 获取").append(comment);
				getset.append("\r\t").append(" */");
				getset.append("\r\t").append("public ")
						.append((new StringBuilder(String.valueOf(type))).append(" ").toString())
						.append((new StringBuilder("get")).append(method).append("() {\r\t").toString());
				getset.append("    return this.").append(name).append(";\r\t}");
				//getset.append("\r\t")
				getset.append("\r\t").append("/**")
						.append("\r\t").append(" * 设置").append(comment)
						.append("\r\t").append(" */")
						.append("\r\t")
						.append("public void ")
						.append((new StringBuilder("set")).append(method).append("(").append(type).append(" ").append(name)
								.append(") {\r\t").toString());
				getset.append((new StringBuilder("    this.")).append(name).append("=").toString()).append(name)
				.append(";\r\t}");
				getset.append("\r\t");
				if(fieldDataSet.add(type)){
					beanFieldDataTypes.add(type);
				}
			}
		}

		argv = str.toString();
		this.method = getset.toString();
		return (new StringBuilder(String.valueOf(argv))).append("\r\t").append(this.method).toString();
	}

	@SuppressWarnings("rawtypes")
	public String getQueryBeanFeilds(String tableName, String className) throws SQLException {
		List dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		String name;
		for (Iterator iterator = dataList.iterator(); iterator.hasNext(); getset
				.append((new StringBuilder("    this.")).append(name).append("=").toString()).append(name)
				.append(";\r\t}")) {
			ColumnData d = (ColumnData) iterator.next();
			name = d.getDomainPropertyName();
			String type = d.getDataType();
			String comment = d.getColumnComment();
			String maxChar = name.substring(0, 1).toUpperCase();
			str.append("\r\t").append("/**");
			str.append("\r\t").append(" *").append(comment);
			str.append("\r\t").append(" */");
			str.append("\r\t").append("private ")
					.append((new StringBuilder(String.valueOf(type))).append(" ").toString()).append(name).append(";");
			String method = (new StringBuilder(String.valueOf(maxChar))).append(name.substring(1, name.length()))
					.toString();
			getset.append("\r\t").append("public ")
					.append((new StringBuilder(String.valueOf(type))).append(" ").toString())
					.append((new StringBuilder("get")).append(method).append("() {\r\t").toString());
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t")
					.append("public void ")
					.append((new StringBuilder("set")).append(method).append("(").append(type).append(" ").append(name)
							.append(") {\r\t").toString());
		}

		argv = str.toString();
		this.method = getset.toString();
		return (new StringBuilder(String.valueOf(argv))).append(this.method).toString();
	}

	public String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "String";
		else if (dataType.contains("text"))
			dataType = "String";
		else if (dataType.contains("bigint"))
			dataType="BigInteger";
		else if (dataType.contains("int"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number")) {
			if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0)
				dataType = "BigDecimal";
			else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 9)
				dataType = "Long";
			else
				dataType = "Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time"))
			dataType = "Date";
		else if (dataType.contains("clob"))
			dataType = "Clob";
		else if(dataType.contains("bit")) {
			dataType = "Integer";
		}
		else
			dataType = "Object";
		return dataType;
	}

	public void getPackage(int type, String createPath, String content, String packageName, String className,
			String extendsClassName, String importName[]) throws Exception {
		if (packageName == null)
			packageName = "";
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; i++)
			sb.append("import ").append(importName[i]).append(";\r");

		sb.append("\r");
		sb.append((new StringBuilder("/**\r *  entity. @author wolf Date:"))
				.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).append("\r */").toString());
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null)
			sb.append(" extends ").append(extendsClassName);
		if (type == 1)
			sb.append(" ").append("implements java.io.Serializable {\r");
		else
			sb.append(" {\r");
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = (new StringBuilder(String.valueOf(temp))).append(className.substring(1, className.length())).toString();
		if (type == 1)
			sb.append((new StringBuilder("private ")).append(className).append(" ").append(temp).append("; // entity ")
					.toString());
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		createFile(createPath, "", sb.toString());
	}

	public String getTablesNameToClassName(String tableName) {
		tableName = tableName.toLowerCase();
		String split[] = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String tempTableName = (new StringBuilder(String.valueOf(split[i].substring(0, 1).toUpperCase())))
						.append(split[i].substring(1, split[i].length())).toString();
				sb.append(tempTableName);
			}

			return sb.toString();
		} else {
			String tempTables = (new StringBuilder(String.valueOf(split[0].substring(0, 1).toUpperCase()))).append(
					split[0].substring(1, split[0].length())).toString();
			return tempTables;
		}
	}

	public String getTablesASName(String tableName) {
		tableName = tableName.toLowerCase();
		String split[] = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String tempTableName = split[i].substring(0, 1).toLowerCase();
				sb.append(tempTableName);
			}

			return sb.toString();
		} else {
			String tempTables = split[0].substring(0, 1).toLowerCase();
			return tempTables;
		}
	}

	public String getcolumnNameToDomainPropertyName(String columnName) {
		columnName = columnName.toLowerCase();
		String split[] = columnName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String tempTableName = (new StringBuilder(String.valueOf(split[i].substring(0, 1).toUpperCase())))
						.append(split[i].substring(1, split[i].length())).toString();
				sb.append(tempTableName);
			}

			columnName = sb.toString();
			columnName = (new StringBuilder(String.valueOf(columnName.substring(0, 1).toLowerCase()))).append(
					columnName.substring(1, columnName.length())).toString();
			return columnName;
		} else {
			columnName = (new StringBuilder(String.valueOf(split[0].substring(0, 1).toLowerCase()))).append(
					split[0].substring(1, split[0].length())).toString();
			return columnName;
		}
	}

	public void createFile(String path, String fileName, String str) throws IOException {
		FileWriter writer = new FileWriter(new File((new StringBuilder(String.valueOf(path))).append(fileName)
				.toString()));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}

	public Map<String, Object> getAutoCreateSql(String tableName) throws Exception {
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		List<ColumnData> columnDatas = getColumnDatas(tableName);
		if(columnDatas==null|| columnDatas.size()<=0){
			throw new Exception("数据表【"+tableName+"】不存在！");
		}
		String columns = getColumnSplit(columnDatas);
		String columnList[] = getColumnList(columns);
		String columnFields = getColumnFields(columns);
		String insert = (new StringBuilder("INSERT INTO ")).append(tableName).append("(")
				.append(columns.replaceAll("\\|", ",")).append(")\n values(#{")
				.append(columns.replaceAll("\\|", "},#{")).append("})").toString();
		String update = getUpdateSql(tableName, columnList);
		String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
		String selectById = getSelectByIdSql(tableName, columnList);
		String delete = getDeleteSql(tableName, columnList);
		sqlMap.put("columnList", columnList);
		sqlMap.put("columnFields", columnFields);
		sqlMap.put("insert", insert);
		sqlMap.put("update", update);
		sqlMap.put("delete", delete);
		sqlMap.put("updateSelective", updateSelective);
		sqlMap.put("selectById", selectById);
		return sqlMap;
	}

	public String getDeleteSql(String tableName, String columnsList[]) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}

	public String getSelectByIdSql(String tableName, String columnsList[]) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("select <include refid=\"Base_Column_List\" /> \n");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}

	public String getColumnFields(String columns) throws SQLException {
		String fields = columns;
		if (fields != null && !"".equals(fields))
			fields = fields.replaceAll("[|]", ",");
		return fields;
	}

	public String[] getColumnList(String columns) throws SQLException {
		String columnList[] = columns.split("[|]");
		return columnList;
	}

	public String getUpdateSql(String tableName, String columnsList[]) throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < columnsList.length; i++) {
			String column = columnsList[i];
			if (!"CREATETIME".equals(column.toUpperCase())) {
				if ("UPDATETIME".equals(column.toUpperCase()))
					sb.append((new StringBuilder(String.valueOf(column))).append("=now()").toString());
				else
					sb.append((new StringBuilder(String.valueOf(column))).append("=#{").append(column).append("}")
							.toString());
				if (i + 1 < columnsList.length)
					sb.append(",");
			}
		}

		String update = (new StringBuilder("update ")).append(tableName).append(" set ").append(sb.toString())
				.append(" where ").append(columnsList[0]).append("=#{").append(columnsList[0]).append("}").toString();
		return update;
	}

	@SuppressWarnings("rawtypes")
	public String getUpdateSelectiveSql(String tableName, List columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		ColumnData cd = (ColumnData) columnList.get(0);
		sb.append("\t<trim  suffixOverrides=\",\" >\n");
		for (int i = 1; i < columnList.size(); i++) {
			ColumnData data = (ColumnData) columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"").append(columnName).append(" != null ");
			if ("String" == data.getDataType())
				sb.append(" and ").append(columnName).append(" != ''");
			sb.append(" \">\n\t\t");
			sb.append((new StringBuilder(String.valueOf(columnName))).append("=#{").append(columnName).append("},\n")
					.toString());
			sb.append("\t</if>\n");
		}

		sb.append("\t</trim>");
		String update = (new StringBuilder("update ")).append(tableName).append(" set \n").append(sb.toString())
				.append(" where ").append(cd.getColumnName()).append("=#{").append(cd.getColumnName()).append("}")
				.toString();
		return update;
	}

	@SuppressWarnings("rawtypes")
	public String getColumnSplit(List columnList) throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		ColumnData data;
		for (Iterator iterator = columnList.iterator(); iterator.hasNext(); commonColumns.append((new StringBuilder(
				String.valueOf(data.getColumnName()))).append("|").toString()))
			data = (ColumnData) iterator.next();

		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}
	
	private String getJdbcDataType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "VARCHAR";
		else if (dataType.contains("text"))
			dataType = "VARCHAR";
		else if (dataType.contains("bigint"))
			dataType="BIGINT";
		else if (dataType.contains("int"))
			dataType = "INTEGER";
		else if (dataType.contains("float"))
			dataType = "DECIMAL";
		else if (dataType.contains("double"))
			dataType = "DECIMAL";
		else if (dataType.contains("number")) {
			if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0)
				dataType = "DECIMAL";
			else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 9)
				dataType = "BIGINT";
			else
				dataType = "INTEGER";
		} else if (dataType.contains("decimal"))
			dataType = "DECIMAL";
		else if (dataType.contains("date"))
			dataType = "DATE";
		else if (dataType.contains("time"))
			dataType = "TIMESTAMP";
		else if (dataType.contains("clob"))
			dataType = "CLOB";
		else if(dataType.contains("bit"))
			dataType = "INTEGER";
		else
			dataType = "OBJECT";
		return dataType;
	}

	/**
	 * @return the beanFieldDataTypes
	 */
	public List<String> getBeanFieldDataTypes() {
		return beanFieldDataTypes;
	}

	/**
	 * @param beanFieldDataTypes the beanFieldDataTypes to set
	 */
	public void setBeanFieldDataTypes(List<String> beanFieldDataTypes) {
		this.beanFieldDataTypes = beanFieldDataTypes;
	}
	
	
}
