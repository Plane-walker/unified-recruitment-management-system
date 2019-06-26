package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import util.JDBCutil_c3p0;

public class Applicant extends Account {

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
					"insert into applicant(ID,name,pwHash,gender,country)"
					+ " value(?,?,?,?,?) ");
			psta.setString(1, userID);
			psta.setString(2, username);
			psta.setString(3, pwHash);
			psta.setString(4, gender);
			psta.setString(5, country);
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

}
