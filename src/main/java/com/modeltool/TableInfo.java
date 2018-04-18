package com.modeltool;

import java.util.*;
@SuppressWarnings("rawtypes")
public class TableInfo
{

	public String name;
	public List columns;
	public List columnInfos;
	public Map columnsMap;
	public List keys;
	public List keyColumns;

	public TableInfo()
	{
		columns = new ArrayList();
		keys = new ArrayList();
		keyColumns = new ArrayList();
		columnsMap = new HashMap();
		columnInfos = new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public void addKey(String name)
	{
		if (!keys.contains(name))
		{
			keys.add(name);
			ColumnInfo columnInfo = (ColumnInfo)columnsMap.get(name);
			keyColumns.add(columnInfo);
		}
	}

	@SuppressWarnings("unchecked")
	public void addColumn(ColumnInfo colInfo)
	{
		String colName = colInfo.getName();
		if (columnsMap.containsKey(colName))
			return;
		columns.add(colName);
		columnInfos.add(colInfo);
		columnsMap.put(colName, colInfo);
		if (colInfo.isKey())
			keys.add(colName);
	}

	public String toString()
	{
		String s = (new StringBuilder("name=")).append(name).append(",keys=").append(keys).append(",columns=").append(columns).append(",columnsMap=").append(columnsMap).append(",columnInfos=").append(columnInfos).toString();
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

	public List getColumns()
	{
		return columns;
	}

	public void setColumns(List columns)
	{
		this.columns = columns;
	}

	public Map getColumnsMap()
	{
		return columnsMap;
	}

	public void setColumnsMap(Map columnsMap)
	{
		this.columnsMap = columnsMap;
	}

	public List getKeys()
	{
		return keys;
	}

	public void setKeys(List keys)
	{
		this.keys = keys;
	}

	public List getColumnInfos()
	{
		return columnInfos;
	}

	public void setColumnInfos(List columnInfos)
	{
		this.columnInfos = columnInfos;
	}

	public List getKeyColumns()
	{
		return keyColumns;
	}

	public void setKeyColumns(List keyColumns)
	{
		this.keyColumns = keyColumns;
	}
}
