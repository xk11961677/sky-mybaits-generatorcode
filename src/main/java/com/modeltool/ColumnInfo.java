// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ColumnInfo.java

package com.modeltool;


public class ColumnInfo
{

	public String name;
	public String type;
	public String sqlType;
	public int precision;
	public boolean isKey;

	public ColumnInfo(String name, String type, String sqlType, int precision, boolean isKey)
	{
		this.name = name;
		this.type = type;
		this.sqlType = sqlType;
		this.precision = precision;
		this.isKey = isKey;
	}

	public String toString()
	{
		String s = (new StringBuilder("name=")).append(name).append(",type=").append(type).append(",sqlType=").append(sqlType).append(",precision=").append(precision).append(",isKey=").append(isKey).toString();
		return s;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSqlType()
	{
		return sqlType;
	}

	public void setSqlType(String sqlType)
	{
		this.sqlType = sqlType;
	}

	public int getPrecision()
	{
		return precision;
	}

	public void setPrecision(int precision)
	{
		this.precision = precision;
	}

	public boolean isKey()
	{
		return isKey;
	}

	public void setKey(boolean isKey)
	{
		this.isKey = isKey;
	}
}
