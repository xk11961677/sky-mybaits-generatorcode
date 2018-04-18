package codeGenerate;

public class ColumnData{

	public static final String OPTION_REQUIRED = "required:true";
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	private String columnName;
	private String domainPropertyName;
	private String dataType;
	private String columnComment;
	private String columnType;
	private String columnKey;
	private String charmaxLength;
	private String nullable;
	private String scale;
	private String precision;
	private String jdbcDataType;

	public ColumnData() {
		charmaxLength = "";
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getScale() {
		return scale;
	}

	public String getPrecision() {
		return precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getCharmaxLength() {
		return charmaxLength;
	}

	public String getNullable() {
		return nullable;
	}

	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getDomainPropertyName() {
		return domainPropertyName;
	}

	public void setDomainPropertyName(String domainPropertyName) {
		this.domainPropertyName = domainPropertyName;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	/**
	 * @return the jdbcDataType
	 */
	public String getJdbcDataType() {
		return jdbcDataType;
	}

	/**
	 * @param jdbcDataType the jdbcDataType to set
	 */
	public void setJdbcDataType(String jdbcDataType) {
		this.jdbcDataType = jdbcDataType;
	}
}
