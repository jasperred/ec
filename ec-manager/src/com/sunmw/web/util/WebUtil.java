package com.sunmw.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class WebUtil {
	
	public static Map objectToMap(Object o)
	{
		Map r = new HashMap();
		Method[] ms = o.getClass().getMethods();
		for(Method m:ms)
		{
			String name = m.getName();
			if(name.indexOf("get")==0)
			{
				try {
					r.put(name.substring(3), m.invoke(o, null));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return r;
	}
	
	/**
	 * 转化特殊字符
	 * @param s
	 * @return
	 */
	public static String filterSpecialCharacters(String s)
	{
		if(s==null)
			return null;
		s = s.replace("&amp;","&");
		s = s.replace("&#35;", "#");
		s = s.replace("&#59;",  ";");	
		s = s.replace("&lt;",  "<");
		s = s.replace("&gt;",  ">");
		s = s.replace("&#96;", "`");
		s = s.replace("&#126;", "~");
		s = s.replace("&#33;", "!");
		s = s.replace("&#36;", "$");
		s = s.replace("&#37;",  "%");
		s = s.replace("&#64;",  "@");
		s = s.replace("&#94;", "^");
		s = s.replace("&#42;", "*");
		s = s.replace("&#40;", "(");
		s = s.replace("&#41;", ")");
		s = s.replace("&#95;", "_");
		s = s.replace("&#45;", "-");
		s = s.replace("&#43;", "+");
		s = s.replace("&#61;", "=");
		s = s.replace("&#91;", "[");
		s = s.replace("&#93;", "]");
		s = s.replace("&#123;", "{");
		s = s.replace("&#125;", "}");
		s = s.replace("&#92;", "\\");
		s = s.replace("&#124;", "|");
		s = s.replace("&#58;",  ":");
		s = s.replace("&apos;",  "'");
		s = s.replace("&quot;",  "\"");
		s = s.replace("&#44;",  ",");
		s = s.replace("&#46;", ".");
		s = s.replace("&#63;", "?");
		s = s.replace("&#47;", "/");
		s = s.replace("<BR/>", "\r\n");
		s = s.replace("&nbsp;&nbsp;&nbsp;&nbsp;",  "\t");
		s = s.replace("&middot;",  "·");
		s = s.replace("&nbsp;",  " ");
		return s;
	}
	
	public static String replaceSpecialCharacters(String s)
	{
		if(s==null)
			return null;
		StringBuffer ss = new StringBuffer();
		for(int i=0;i<s.length();i++)
		{
			char c = s.charAt(i);
			switch (c) {
			case ';': ss.append("&#59;");break;
			case '&': ss.append("&amp;");break;
			case '$': ss.append("&#36;");break;
			case '%': ss.append("&#37;");break;
			case '@': ss.append("&#64;");break;
			case '(': ss.append("&#40;");break;
			case ')': ss.append("&#41;");break;
			case '\\': ss.append("&#92;");break;
			case '|': ss.append("&#124;");break;
			case '\'': ss.append("&apos;");break;
			case '"': ss.append("&quot;");break;
			case '<': ss.append("&lt;");break;
			case '>': ss.append("&gt;");break;
			case ',': ss.append("&#44;");break;
			case '\r': break;
			case '\n': ss.append("<BR/>");break;
			case '\t': ss.append("&nbsp;&nbsp;&nbsp;&nbsp;");break;		
			case '`': ss.append("&#96;");break;
			case '~': ss.append("&#126;");break;
			case '!': ss.append("&#33;");break;
			case '#': ss.append("&#35;");break;
			case '^': ss.append("&#94;");break;
			case '*': ss.append("&#42;");break;
			case '_': ss.append("&#95;");break;
			case '-': ss.append("&#45;");break;
			case '+': ss.append("&#43;");break;
			case '=': ss.append("&#61;");break;
			case '[': ss.append("&#91;");break;
			case ']': ss.append("&#93;");break;
			case '{': ss.append("&#123;");break;
			case '}': ss.append("&#125;");break;
			case ':': ss.append("&#58;");break;
			case '.': ss.append("&#46;");break;
			case '?': ss.append("&#63;");break;
			case '/': ss.append("&#47;");break;
			default:
				ss.append(c);
				break;
			}
		}
		return ss.toString();
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		if(str==null)
			return false;
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	
	/**
	 * 四舍五入
	 * @param num
	 * @param digit
	 * @return
	 */
	public static BigDecimal round(BigDecimal num,int digit) {
		if(num==null)
			return new BigDecimal(0);
		BigDecimal a = num.setScale(digit, BigDecimal.ROUND_HALF_UP);
		return a;
	}
	public static BigDecimal round(BigDecimal num) {
		return round(num,2);
	}
	//字符转成BigDecimal
	public static BigDecimal toBigDecimal(Object n) {
		try {
			if(isNull(n))
				return new BigDecimal(0);
			else
				return new BigDecimal(n.toString().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BigDecimal(0);
	}
	public static boolean exportCSV(String[] headers,String[] fields,List<Map> l,String path,String fileName)
	{
		String encode = WebConfigProperties.getProperties("system.file.encode");
		if(encode==null)
			encode = "UTF-8";
		return exportCSV( headers, fields, l, path, fileName,encode);
	}
	public static boolean exportCSV(String[] headers,String[] fields,List<Map> l,String path,String fileName,String encode)
	{
		//FileWriter fw = null;
		OutputStreamWriter osw = null;
		try {
			File dir = new File(path);
			if(!dir.exists())
				dir.mkdirs();
			File file = new File(path+fileName);
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
	        osw = new OutputStreamWriter(fos, encode); 
			//fw = new FileWriter(path+fileName);
			StringBuffer header = new StringBuffer();
			for(String h:headers)
			{
				if(header.length()>0)
					header.append(",");
				header.append(h);
			}
			header.append("\r\n");
			osw.write(header.toString());
			for(Map m:l)
			{
				StringBuffer line = new StringBuffer();
				for(String field:fields)
				{
					if(line.length()>0)
						line.append(",");
					line.append(m.get(field)==null?"":m.get(field));
					
				}
				line.append("\r\n");
				osw.write(line.toString());
			}
			osw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally
		{
			if(osw!=null)
				try {
					osw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
		}
		return true;
	}
	/**
	 * 读取CSV文件
	 * @param file
	 * @param hasHeader--表示是否有文件表头，无表头返回数组，否则按表头Map返回
	 * @return
	 */
	public static List readerCsv(File file,boolean hasHeader)
	{
		String encode = WebConfigProperties.getProperties("system.file.encode");
		if(encode==null)
			encode = "UTF-8";
		return readerCsv(file,hasHeader,encode);
	}
	/**
	 * 读取CSV文件
	 * @param file
	 * @param hasHeader--表示是否有文件表头，无表头返回数组，否则按表头Map返回
	 * @return
	 */
	public static List readerCsv(File file,boolean hasHeader,String encode)
	{
		try {
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(file), encode)),',','"',false);
			List<String[]> l = reader.readAll();
			if(isNullForList(l))
				return null;
			if(hasHeader&&l.size()<=1)
				return null;
			if(!hasHeader)
				return l;
			else
			{
				List<Map> r = new ArrayList();
				String[] header = l.get(0);
				for(int i=1;i<l.size();i++)
				{
					String[] str = l.get(i);
					Map m = new HashMap();
					m.put("ROW", i+1);//行号
					for(int j=0;j<str.length;j++)
					{
						m.put(header[j], str[j]);
					}
					r.add(m);
				}
				return r;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//是否为空或空串
	public static boolean isNull(Object obj)
	{
		if(obj==null||obj.toString().trim().length()==0)
			return true;
		else
			return false;
	}
	
	public static boolean isNotNull(Object obj)
	{
		if(obj==null||obj.toString().trim().length()==0)
			return false;
		else
			return true;
	}
	
	//List检查
	public static boolean isNullForList(List l)
	{
		if(l==null||l.size()==0||l.get(0)==null)
			return true;
		else
			return false;
	}
	
	// 将字符串变为指定长度，不足长度前面加前缀prefix
	public static String addPrefix(String str, int length, String prefix) {
		if (str == null)
			return null;
		if (str.trim().length() >= length)
			return str;
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < length - str.trim().length(); i++)
			s.append(prefix);
		s.append(str.trim());
		return s.toString();
	}
	
	//日期格式
	public static String formatDateString(Date date, String format) {
		if(date==null)return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date toDateForString(String date,String format)
	{
		if(date==null||date.trim().length()==0)return null;
		SimpleDateFormat sdf  =   new  SimpleDateFormat(format);  
		try {
			Date d = sdf.parse(date);
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
		
	}
	//long转为经过的天和小时
	public static String toDH(long t)
	{
		long d = t/(24*60*60*1000);
		long h = (t-d*24*60*60*1000)/(60*60*1000);
		String s = null;
		if(d==0&&h==0)
			return null;
		if(d==0)
			return h+"小时";
		else
			return d+"天"+h+"小时";
	}
	/**
	 * 日期后的几个月
	 * @param date
	 * @param x
	 * @return
	 */
	public static String ofterMonthDate(Date date,int x) {
		//Date date = new Date();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date))
				+ x;
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		if (month > 12) {
			year += month / 12;
			month = month % 12;
		}
		if (day > 28) {
			if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29;
				} else
					day = 28;
			} else if ((month == 4 || month == 6 || month == 9 || month == 11)
					&& day == 31) {
				day = 30;
			}
		}
		String y = year + "-";
		String m = "";
		String d = "";
		if (month < 10)
			m = "0" + month + "-";
		else
			m = month + "-";
		if (day < 10)
			d = "0" + day;
		else
			d = day + "";
		return y + m + d;
	}
	
	/**
	 * 日期前的几个月
	 * @param date
	 * @param x
	 * @return
	 */
	public static String beforeMonthDate(Date date,int x) {
		//Date date = new Date();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date))
				- x;
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		if (month <=0) {
			year += month / 12-1;
			month = month % 12+12;
		}
		if (day > 28) {
			if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29;
				} else
					day = 28;
			} else if ((month == 4 || month == 6 || month == 9 || month == 11)
					&& day == 31) {
				day = 30;
			}
		}
		String y = year + "-";
		String m = "";
		String d = "";
		if (month < 10)
			m = "0" + month + "-";
		else
			m = month + "-";
		if (day < 10)
			d = "0" + day;
		else
			d = day + "";
		return y + m + d;
	}
	
	 /** 
	    * 判断当前日期是星期几<br> 
	    * <br> 
	    * @param pTime 修要判断的时间<br> 
	    * @return dayForWeek 判断结果<br> 
	    * @Exception 发生异常<br> 
	    */  
	public static int dayForWeek(String pTime){  
	 Calendar c = Calendar.getInstance();  
	 c.setTime(toDateForString(pTime,"yyyy-MM-dd"));  
	 int dayForWeek = 0;  
	 if(c.get(Calendar.DAY_OF_WEEK) == 1){  
	  dayForWeek = 7;  
	 }else{  
	  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
	 }  
	 return dayForWeek;  
	}  
	
	 /** 
	    * 判断当前日期是星期几<br> 
	    * <br> 
	    * @param pTime 修要判断的时间<br> 
	    * @return dayForWeek 判断结果<br> 
	    * @Exception 发生异常<br> 
	    */  
	public static int dayForWeek(Date pTime){  
	 Calendar c = Calendar.getInstance();  
	 c.setTime(pTime);  
	 int dayForWeek = 0;  
	 if(c.get(Calendar.DAY_OF_WEEK) == 1){  
	  dayForWeek = 7;  
	 }else{  
	  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
	 }  
	 return dayForWeek;  
	}  
	
	/**
	 * 两个日期之间的天数
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int getDaysBetween(String t1,String t2)
	{
		int d = 0;
		long l1 = toDateForString(t1,"yyyy-MM-dd").getTime();
		long l2 = toDateForString(t2,"yyyy-MM-dd").getTime();
		d = (int) ((l2-l1)/(24*60*60*1000));
		return d;
	}
	
	/**
	 * 两个日期之间的天数
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int getDaysBetween(Date t1,Date t2)
	{
		int d = 0;
		long l1 = t1.getTime();
		long l2 = t2.getTime();
		d = (int) ((l2-l1)/(86400000));
		return d;
	}
	
	/**
	 * 两个日期之间的月数
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int getMonthsBetween(Date t1,Date t2)
	{
		int t1Y = Integer.parseInt(WebUtil.formatDateString(t1, "yyyy"));
		int t1M = Integer.parseInt(WebUtil.formatDateString(t1, "M"));
		int t1D = Integer.parseInt(WebUtil.formatDateString(t1, "d"));
		int t2Y = Integer.parseInt(WebUtil.formatDateString(t2, "yyyy"));
		int t2M = Integer.parseInt(WebUtil.formatDateString(t2, "M"));
		int t2D = Integer.parseInt(WebUtil.formatDateString(t2, "d"));
		int m = 0;
		if(t2D<t1D)
		{
			m =( t2Y-t1Y)*12+t2M-t1M-1;
		}
		else
		{
			m =( t2Y-t1Y)*12+t2M-t1M;
		}
		return m;
	}
	
	//文件复制
	public static long fileCopy(File f1,File f2) throws Exception{   
        long time=new Date().getTime();   
        int length=2097152;   
         FileInputStream in=new FileInputStream(f1);   
         FileOutputStream out=new FileOutputStream(f2);   
         FileChannel inC=in.getChannel();   
         FileChannel outC=out.getChannel();   
        int i=0;   
        while(true){   
            if(inC.position()==inC.size()){   
                 inC.close();   
                 outC.close();   
                return new Date().getTime()-time;   
             }   
            if((inC.size()-inC.position())<20971520)   
                 length=(int)(inC.size()-inC.position());   
            else  
                 length=20971520;   
             inC.transferTo(inC.position(),length,outC);   
             inC.position(inC.position()+length);   
             i++;   
         }   
     }  
	
	//int转换
	public static int transformInteger(Object o)
	{
		if(o!=null)
			return Integer.parseInt(o.toString());
		else
			return 0;
	}
	
	public static List toList(Object obj)
	{
		List l = new ArrayList();
		l.add(obj);
		return l;
	}

	public static Map toMap(Object key1,Object value1)
	{
		Map m = new HashMap();
		m.put(key1, value1);
		return m;
	}
	
	public static Map toMap(Object key1,Object value1,Object key2,Object value2)
	{
		Map m = new HashMap();
		m.put(key1, value1);
		m.put(key2, value2);
		return m;
	}
	
	public static Map toMap(Object key1,Object value1,Object key2,Object value2,Object key3,Object value3)
	{
		Map m = new HashMap();
		m.put(key1, value1);
		m.put(key2, value2);
		m.put(key3, value3);
		return m;
	}
	
	public static Map toMap(Object key1,Object value1,Object key2,Object value2,Object key3,Object value3,Object key4,Object value4)
	{
		Map m = new HashMap();
		m.put(key1, value1);
		m.put(key2, value2);
		m.put(key3, value3);
		m.put(key4, value4);
		return m;
	}
	
	public static Map toMap(Object key1,Object value1,Object key2,Object value2,Object key3,Object value3,Object key4,Object value4,Object key5,Object value5)
	{
		Map m = new HashMap();
		m.put(key1, value1);
		m.put(key2, value2);
		m.put(key3, value3);
		m.put(key4, value4);
		m.put(key5, value5);
		return m;
	}
	
	public static Map toMap(Object key1,Object value1,Object key2,Object value2,Object key3,Object value3,Object key4,Object value4,Object key5,Object value5,Object key6,Object value6)
	{
		Map m = new HashMap();
		m.put(key1, value1);
		m.put(key2, value2);
		m.put(key3, value3);
		m.put(key4, value4);
		m.put(key5, value5);
		m.put(key6, value6);
		return m;
	}

	//密码转换
	public static String getDigestHash(String str, String hashType) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance(hashType);  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer strBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
            	strBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
            	strBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return strBuff.toString();  
    }
	
	public static void main(String[] args)
	{
		System.out.println(WebUtil.beforeMonthDate(WebUtil.toDateForString("2012-05-31", "yyyy-MM-dd"), 1)+" 00:00:00");
		System.out.println(WebUtil.ofterMonthDate(WebUtil.toDateForString("2012-03-31", "yyyy-MM-dd"), 3)+" 23:59:59");
	}

}
