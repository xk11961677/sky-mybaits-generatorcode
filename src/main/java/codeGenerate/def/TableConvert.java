// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TableConvert.java

package codeGenerate.def;


public class TableConvert
{

	public TableConvert()
	{
	}

	public static String getNullAble(String nullable)
	{
		if ("YES".equals(nullable) || "yes".equals(nullable) || "y".equals(nullable) || "Y".equals(nullable))
			return "Y";
		if ("NO".equals(nullable) || "N".equals(nullable) || "no".equals(nullable) || "n".equals(nullable))
			return "N";
		else
			return null;
	}
}
