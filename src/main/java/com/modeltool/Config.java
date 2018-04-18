// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Config.java

package com.modeltool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{

	public static Properties prop;
	private static String targetDir = null;
	private static String templateEncoding = null;

	public Config()
	{
	}

	public static String getTemplateEncoding()
	{
		return templateEncoding;
	}

	public static String getTargetDir()
	{
		return targetDir;
	}

	static 
	{
		prop = new Properties();
		InputStream resourceAsStream = Config.class.getClassLoader().getResourceAsStream("modeltool.properties");
		try
		{
			prop.load(resourceAsStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		String targetDirString = (String)prop.get("targetDir");
		targetDir = targetDirString;
		String templateEncodingString = (String)prop.get("templateEncoding");
		templateEncoding = templateEncodingString;
	}
}
