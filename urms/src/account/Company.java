package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import util.JDBCutil_c3p0;

public class Company extends Account{

	@Override
	public
	String login(HttpServletRequest request) {
		String info=null;
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from company"
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
		else {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from company"
					+ " where ID=? and pwHash=?");
			psta.setString(1, userID);
			psta.setString(2, pwHash);
			rs=psta.executeQuery();
			if(!rs.next()) {
			psta=conn.prepareStatement(
					"insert into company(ID,name,pwHash,country,avator)"
					+ " value(?,?,?,?,?) ");
			psta.setString(1, userID);
			psta.setString(2, username);
			psta.setString(3, pwHash);
			psta.setString(4, country);
			psta.setString(5, "images/origin.jpg");
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
