package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import position.Position;
import util.JDBCutil_c3p0;

public class Applicant extends Account {
String gender;
String phone;
String email;
String filepath;
String information;
public void setfilepath(String filepath) {
	if(this.filepath==null)
	this.filepath=filepath;
}
public void setinformation(String information) {
	if(this.information==null)
	this.information=information;
}
public void setgender(String gender) {
	if(this.gender==null)
	this.gender=gender;
}
public void setphone(String phone) {
	if(this.phone==null)
	this.phone=phone;
}
public void setemail(String email) {
	if(this.email==null)
	this.email=email;
}
public String getinformation() {
	return information;
}
public String getfilepath() {
	return filepath;
}
public String getgender() {
	return gender;
}
public String getphone() {
	return phone;
}
public String getemail() {
	return email;
}
	@Override
	public
	String login(HttpServletRequest request) {
		String info=null;
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from applicant"
					+ " where ID=? and pwHash=?");
			psta.setString(1, (String)request.getParameter("userID"));
			psta.setString(2, getmd5((String)request.getParameter("password")));
			rs=psta.executeQuery();
			if(!rs.next())
				info="用户名或密码错误！";
			else {
				setname(rs.getString("name"));
				setavator(rs.getString("avator"));
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public
	String register(HttpServletRequest request) {
		String info=null;
		String userID=request.getParameter("userID");
		String username=request.getParameter("username");
        String gender=request.getParameter("gender");
        String country=request.getParameter("country");
		String pwHash=getmd5(request.getParameter("password"));
		if(userID == null || userID.trim().equals(""))
			info="用户名不能为空！";
		else if (username == null || username.trim().equals("")) 
			info="昵称不能为空！";
		else if (!request.getParameter("password").matches("[^\\u3400-\\u9FFF]{5,18}"))
				info="密码必须为5-18位!";
		else if (!request.getParameter("repassword").equals(request.getParameter("password"))) 
				info="两次密码不一致！";
		/*else if (email != null && !email.trim().equals("")&&!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) 
				error="邮箱格式不正确！";

		else if(phone != null && !phone.trim().equals("")&&!phone.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\\\d{8}$")) 
				errors.put("phone","不存在此号码！");*/
		else {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from applicant"
					+ " where ID=? and pwHash=?");
			psta.setString(1, userID);
			psta.setString(2, pwHash);
			rs=psta.executeQuery();
			if(!rs.next()) {
			psta=conn.prepareStatement(
					"insert into applicant(ID,name,pwHash,gender,country,avator)"
					+ " value(?,?,?,?,?,?) ");
			psta.setString(1, userID);
			psta.setString(2, username);
			psta.setString(3, pwHash);
			psta.setString(4, gender);
			psta.setString(5, country);
			psta.setString(6, "images/origin.jpg");
			psta.executeUpdate();
			}
			else {
				info="用户名已存在！";
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		return info;
		
	}
	public String apply(HttpServletRequest request) {
		String info="";
		String com_ID=request.getParameter("com_ID");
		String pos_name=request.getParameter("pos_name");
        String filepath=request.getParameter("filepath");
        HttpSession session = request.getSession();
        String ID=(String) session.getAttribute("ID");
        session.setAttribute("com_name", com_ID);
        session.setAttribute("pos_name", pos_name);
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from apply"
					+ " where app_ID=? and com_ID=? and name=?");
			psta.setString(1, ID);
			psta.setString(2, com_ID);
			psta.setString(3, pos_name);
			rs=psta.executeQuery();
			if(!rs.next()) {
			psta=conn.prepareStatement(
					"insert into apply(app_ID,com_ID,name,material,status,information)"
					+ " value(?,?,?,?,'active','') ");
			psta.setString(1, ID);
			psta.setString(2, com_ID);
			psta.setString(3, pos_name);
			psta.setString(4, filepath);
			psta.executeUpdate();
			}
			else {
				info="请勿重复申请！";
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}
	public void applyupdate(HttpServletRequest request,String filepath) {
		HttpSession session = request.getSession();
		String com_ID=(String) session.getAttribute("com_name");
		String pos_name=(String) session.getAttribute("pos_name");
        String ID=(String) session.getAttribute("ID");
        
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from apply"
					+ " where app_ID=? and com_ID=? and name=?");
			psta.setString(1, ID);
			psta.setString(2, com_ID);
			psta.setString(3, pos_name);
			rs=psta.executeQuery();
			if(rs.next()) {
			psta=conn.prepareStatement(
					"update apply"
					+ " set material=?"
					+ " where app_ID=? and com_ID=? and name=? ");
			psta.setString(1, filepath);
			psta.setString(2, ID);
			psta.setString(3, com_ID);
			psta.setString(4, pos_name);
			psta.executeUpdate();
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Applicant[] getallapp(String com_ID,String pos_name) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		Applicant[] out=null;
		try {
			conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("select * from apply join applicant on apply.app_ID=applicant.ID"
						+ " where apply.com_ID=? and apply.name=?",ResultSet.TYPE_SCROLL_INSENSITIVE);
				psta.setString(1, com_ID);
				psta.setString(2, pos_name);
			rs=psta.executeQuery();
			rs.last(); 
			out=new Applicant[rs.getRow()];
			rs.beforeFirst(); 
			for(int i=0;rs.next();i++) {
				out[i]=new Applicant();
				out[i].setID(rs.getString("ID"));
				out[i].setname(rs.getString("applicant.name"));
				out[i].setgender(rs.getString("gender"));
				out[i].setcountry(rs.getString("country"));
				out[i].setphone(rs.getString("phone"));
				out[i].setemail(rs.getString("email"));
				out[i].setfilepath(rs.getString("material"));
				out[i].setinformation(rs.getString("information"));
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
		return out;
	}
}
