import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class AddCityValue {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader fis=new BufferedReader(new InputStreamReader(new FileInputStream("D:/workspace/ec-manager/WebRoot/js/common/city-data.js")));
        FileOutputStream fos=new FileOutputStream("d:/city.js");
        String line = null;
        StringBuffer state = new StringBuffer();
        StringBuffer city = new StringBuffer();
        StringBuffer district = new StringBuffer();
        int f = 0;
        while((line=fis.readLine())!=null)
        {
        	if(line.trim().length()==0||line.indexOf("//")>=0)
        		continue;
        	line = line.trim();
        	if(line.indexOf("stateArray")>=0)
        		f = 1;
        	if(line.indexOf("cityArray")>=0)
        		f = 2;
        	if(line.indexOf("districtArray")>=0)
        		f = 3;
        	switch(f)
        	{
	        	case 1:
	        		String s = null;
	        		if(line.indexOf("=")>=0)
	        			s = line.substring(line.indexOf("=")+1);
	        		else
	        		{
	        			s = line;
	        			if(s.indexOf(";")>=0)
	        				s = s.substring(0,s.length()-1);
	        		}
	        		state.append(s.trim());
	        	break;
	        	case 2:
	        		String c = null;
	        		if(line.indexOf("=")>=0)
	        			c = line.substring(line.indexOf("=")+1);
	        		else
	        		{
	        			c = line;
	        			if(c.indexOf(";")>=0)
	        				c = c.substring(0,c.length()-1);
	        		}
	        		city.append(c.trim());
		        	break;
	        	case 3:
	        		String d = null;
	        		if(line.indexOf("=")>=0)
	        			d = line.substring(line.indexOf("=")+1);
	        		else
	        		{
	        			d = line;
	        			if(d.indexOf(";")>=0)
	        				d = d.substring(0,d.length()-1);
	        		}
	        		district.append(d.trim());
		        	break;
        	}
        }
        fis.close();
        System.out.println(state.toString());
        System.out.println(city.toString());
        System.out.println(district.toString());
        //JSONArray ss = JSONArray.fromObject(state.toString());
        JSONObject js = JSONObject.fromObject("{\"state\":"+state.toString()+"}");
        List<Map> ss = (List)js.get("state");
        for(Map sm:ss)
        	sm.put("value", sm.get("name"));
        String citys = city.substring(1, city.length()-1).toString();
        String[] ct = citys.split("],");
        StringBuffer city2 = new StringBuffer();
        city2.append("[");
        for(String cc:ct)
        {
        	if(city2.length()>1)
        		city2.append(",");
        	if(cc.indexOf("]")<0)
        		cc = cc+"]";
        	JSONObject jc = JSONObject.fromObject("{\"city\":"+cc+"}");
            List<Map> cl = (List)jc.get("city");
            for(Map sm:cl)
            	sm.put("value", sm.get("name"));
            city2.append(cl);
        }
        city2.append("]");
        
        
        String dds = district.substring(1, district.length()-1).toString();
        String[] ddd = dds.split("]],");
        StringBuffer dd2 = new StringBuffer();
        dd2.append("[");
        for(String cc:ddd)
        {
        	if(dd2.length()>1)
        		dd2.append(",");
        	if(cc.lastIndexOf("]")!=cc.length())
        		cc = cc+"]";
        	String dt2 = cc.substring(1, cc.length()-1).toString();
        	System.out.println("cc:"+cc);
            String[] ddt = dt2.split("],");
            if(dt2.indexOf("],")<0)
            	ddt = dt2.split("]");
            dd2.append("[");
            int i=0;
            for(String tt:ddt)
            {
            	if(i>0)
            		dd2.append(",");
            	if(tt.indexOf("]")<0)
            		tt = tt+"]";
            	System.out.println(tt);
            	JSONObject jc = JSONObject.fromObject("{\"district\":"+tt+"}");
                List<Map> cl = (List)jc.get("district");
                for(Map sm:cl)
                	sm.put("value", sm.get("name"));
                dd2.append(cl);
                i++;
            }
        	dd2.append("]");
        }
        dd2.append("]");
        System.out.println(dd2);
        fos.write(("var stateArray = "+ss+";\n").getBytes());
        fos.write(("var cityArray = "+city2+";\n").getBytes());
        fos.write(("var districtArray = "+dd2+";\n").getBytes());
        fos.close();
	}

}
