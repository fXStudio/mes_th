// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Security.java

package common;


public class Security
{

	public Security()
	{
	}

	public static String clearSingleQuotationMarksFlaw(String s)
	{
		return s.replaceAll("'", "''");
	}
}
