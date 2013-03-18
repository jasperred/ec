package com.sunmw.web.util;

public class VerifyRequest {
	
	private static long ti = 30000000;
	
	private String encrypt(String str)
	{
		str = WebUtil.getDigestHash(str, "MD5");
		return str;
	}
	
	public static String issueCrumb(String uid)
	{
		String t = ""+System.currentTimeMillis()/ti;
		return WebUtil.getDigestHash(t.substring(4)+uid, "MD5");
	}
	
	public static boolean verifyCrumb(String uid,String crumb)
	{
		long t = System.currentTimeMillis();
		String t1 = ""+t/ti;
		String t2 = ""+(t-ti)/ti;
		String s1 = WebUtil.getDigestHash(t1.substring(4)+uid, "MD5");
		String s2 = WebUtil.getDigestHash(t2.substring(4)+uid, "MD5");
		if(s1.equals(crumb)||s2.equals(crumb))
			return true;
		return false;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println(VerifyRequest.issueCrumb("sadfyh123"));
		System.out.println(VerifyRequest.verifyCrumb("sadfyh123", "a03bc0dc9e63c3c1c7df8f24d745c15f"));
	}

}
