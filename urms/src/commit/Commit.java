package commit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import account.Applicant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.JDBCutil_c3p0;

public class Commit {
	public JSONArray getcommit(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		JSONArray jsona=new JSONArray();
		try {
			conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("select * from comments join applicant on comments.app_ID=applicant.ID"
						+ " where comments.com_ID=? and comments.name=?"
						+ " order by datetime desc,app_ID limit 0, 9",ResultSet.TYPE_SCROLL_INSENSITIVE);
				psta.setString(1, request.getParameter("com_ID"));
				psta.setString(2, request.getParameter("pos_name"));
			rs=psta.executeQuery();
			rs.last(); 
			rs.beforeFirst(); 
			for(int i=0;rs.next();i++) {
				JSONObject json=new JSONObject();
				json.put("avator", rs.getString("avator"));
				json.put("app_name", rs.getString("applicant.name"));
				json.put("commit", rs.getString("comment"));
				jsona.put(json);
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
		return jsona;
		
	}
	public void publish(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		HttpSession session = request.getSession();
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement(
					"insert into comments(app_ID,name,com_ID,comment,datetime)"
					+ " value(?,?,?,?,now()) ");
			psta.setString(1, (String) session.getAttribute("ID"));
			psta.setString(2, request.getParameter("pos_name"));
			psta.setString(3, request.getParameter("com_ID"));
			psta.setString(4, request.getParameter("commit"));
			psta.executeUpdate();
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
