package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import position.Position;
import util.JDBCutil_c3p0;

public class Applicant extends Account {
String gender;
String phone;
String email;
String pos_name;
String filepath;
String information;
String status;
public void setstatus(String status) {
	if(this.status==null)
	this.status=status;
}
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
public String getstatus() {
	return status;
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
public String getpos_name() {
	return pos_name;
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
        String phone=request.getParameter("phone");
        String email=request.getParameter("email");
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
					+ " where ID=?");
			psta.setString(1, userID);
			rs=psta.executeQuery();
			if(!rs.next()) {
			psta=conn.prepareStatement(
					"insert into applicant(ID,name,pwHash,gender,country,avator,phone,email)"
					+ " value(?,?,?,?,?,?,?,?) ");
			psta.setString(1, userID);
			psta.setString(2, username);
			psta.setString(3, pwHash);
			psta.setString(4, gender);
			psta.setString(5, country);
			psta.setString(6, "images/origin.jpg");
			psta.setString(7, phone);
			psta.setString(8, email);
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
			psta=conn.prepareStatement("select * from applicant"
					+ " where ID=?");
			psta.setString(1, ID);
			rs=psta.executeQuery();
			if(rs.next()) {
				if(rs.getString("com_ID")==null) {
			psta=conn.prepareStatement("select * from apply"
					+ " where app_ID=? and com_ID=? and name=?");
			psta.setString(1, ID);
			psta.setString(2, com_ID);
			psta.setString(3, pos_name);
			rs=psta.executeQuery();
			if(!rs.next()) {
			psta=conn.prepareStatement(
					"insert into apply(app_ID,com_ID,name,material,status,information,datetime)"
					+ " value(?,?,?,?,'active','',now()) ");
			psta.setString(1, ID);
			psta.setString(2, com_ID);
			psta.setString(3, pos_name);
			psta.setString(4, filepath);
			psta.executeUpdate();
			psta=conn.prepareStatement(
					"update positions"
					+ " set hits=hits+1"
					+ " where com_ID=? and name=? ");
			psta.setString(1, com_ID);
			psta.setString(2, pos_name);
			psta.executeUpdate();
			}
			else {
				info="请勿重复申请！";
			}
				}
				else {
					info="已任职，无法申请！";
				}
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
				out[i].setavator(rs.getString("avator"));
				out[i].setstatus(rs.getString("status"));
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
		return out;
	}
	public void getemployee(HttpServletRequest request,int size) {
		HttpSession session = request.getSession();
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from applicant"
					+ " where com_ID=? and LOCATE(?, ID)>0"
					+ " order by ID asc limit ?, 1");
			psta.setString(1, (String)session.getAttribute("ID"));
			psta.setString(2, (String)request.getParameter("ID"));
			psta.setInt(3, size-1);
			rs=psta.executeQuery();
			dealinfo(rs);
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
	}
	void dealinfo(ResultSet rs) {
		try {
			if(rs.next()) {
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
				this.country=rs.getString("country");
				this.gender=rs.getString("gender");
				this.phone=rs.getString("phone");
				this.email=rs.getString("email");
				this.pos_name=rs.getString("pos_name");
				this.avator=rs.getString("avator");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void disemploy(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("update applicant"
					+ " set pos_name=null , com_ID=null "
					+ " where ID=?");
			psta.setString(1, (String)request.getParameter("app_ID"));
			psta.executeUpdate();
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
	}
	@Override
	public JSONObject getinfo(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		JSONObject json=new JSONObject();
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from applicant"
					+ " where ID=?");
			psta.setString(1, (String)request.getParameter("ID"));
			rs=psta.executeQuery();
			if(rs.next()) {
				json.put("ID", rs.getString("ID"));
				json.put("name", rs.getString("name"));
				json.put("country", rs.getString("country"));
				if(rs.getString("gender").equals("male"))
				json.put("gender", "男");
				else
					json.put("gender", "女");
				String phone=rs.getString("phone");
				if(phone!=null&&phone.length()!=0)
				json.put("phone", phone);
				else
					json.put("phone", "无");
				String email=rs.getString("email");
				if(email!=null&&email.length()!=0)
				json.put("email", email);
				else
					json.put("email", "无");
				String pos_name=rs.getString("pos_name");
				if(pos_name!=null&&pos_name.length()!=0)
				json.put("pos_name", pos_name);
				else
					json.put("pos_name", "无");
				String com_ID=rs.getString("com_ID");
				if(com_ID!=null&&com_ID.length()!=0)
				json.put("com_ID", com_ID);
				else
					json.put("com_ID", "无");
				json.put("avator", rs.getString("avator"));
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
		return json;
	}
	@Override
	public String updateavator(HttpServletRequest request, String filepath) {
		HttpSession session = request.getSession();
        String ID=(String) session.getAttribute("ID");
        
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		String oldfile=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from applicant"
					+ " where ID=?");
			psta.setString(1, ID);
			rs=psta.executeQuery();
			if(rs.next()) {
				oldfile=rs.getString("avator");
			psta=conn.prepareStatement(
					"update applicant"
					+ " set avator=?"
					+ " where ID=? ");
			psta.setString(1, filepath);
			psta.setString(2, ID);
			psta.executeUpdate();
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oldfile;
	}
	@Override
	public String updateinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String info="";
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement(
					"update applicant"
					+ " set name=?, phone=?, email=?"
					+ " where ID=? ");
			psta.setString(1, name);
			psta.setString(2, phone);
			psta.setString(3, email);
			psta.setString(4, (String) session.getAttribute("ID"));
			psta.executeUpdate();
			session.setAttribute("name", name);
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}
}
