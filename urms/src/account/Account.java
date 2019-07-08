package account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

abstract public class Account {
	String ID=null;
	String name=null;
	String country=null;
	String avator=null;
	public abstract String login(HttpServletRequest request);
	public abstract String register(HttpServletRequest request);
	public abstract JSONObject getinfo(HttpServletRequest request);
	public abstract String updateavator(HttpServletRequest request,String filepath);
	public abstract String updateinfo(HttpServletRequest request);
	public void setID(String ID) {
		if(this.ID==null)
		this.ID=ID;
	}
	public void setname(String name) {
		if(this.name==null)
		this.name=name;
	}
	public void setcountry(String country) {
		if(this.country==null)
		this.country=country;
	}
	public void setavator(String avator) {
		if(this.avator==null)
		this.avator=avator;
	}
	public String getID() {
		return ID;
	}
	public String getname() {
		return name;
	}
	public String getcountry() {
		return country;
	}
	public String getavator() {
		return avator;
	}
	public String getmd5(String input) {
		if(input==null)
			input="";
		String output="";
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			md5.update(input.getBytes());
			byte [] encryContext = md5.digest();
			int i;
            for (int offset = 0; offset < encryContext.length; offset++) {
                i = encryContext[offset];  
                if (i < 0) i += 256;  
                if (i < 16) output+="0";  
                output+=Integer.toHexString(i);  
           } 
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return output;
	}
}
