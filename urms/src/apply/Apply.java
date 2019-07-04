package apply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import account.Applicant;
import account.Company;
import util.JDBCutil_c3p0;

public class Apply {
	Applicant app;
	Company com;
	String name;
	public void setstatus(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		HttpSession session = request.getSession();
		try {
			if(!request.getParameter("new_status").equals("meeting")) {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("delete from apply"
					+ " where app_ID=? and com_ID=? and name=?");
			psta.setString(1, request.getParameter("app_ID"));
			psta.setString(2, (String)session.getAttribute("ID"));
			psta.setString(3, request.getParameter("pos_name"));
			psta.executeUpdate();
			JDBCutil_c3p0.release(rs, psta, conn);
			if(request.getParameter("new_status").equals("接受")) {
				conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("update positions"
						+ " set number=number-1"
						+ " where name=? and com_ID=?");
				psta.setString(1, request.getParameter("pos_name"));
				psta.setString(2, (String)session.getAttribute("ID"));
				psta.executeUpdate();
				psta=conn.prepareStatement("select number from positions"
						+ " where name=? and com_ID=?");
				psta.setString(1, request.getParameter("pos_name"));
				psta.setString(2, (String)session.getAttribute("ID"));
				rs=psta.executeQuery();
				if(rs.next()) {
					if(rs.getInt("number")<=0) {
						psta=conn.prepareStatement("delete from positions"
								+ " where name=? and com_ID=?");
						psta.setString(1, request.getParameter("pos_name"));
						psta.setString(2, (String)session.getAttribute("ID"));
						psta.executeUpdate();
					}
				}
				psta=conn.prepareStatement("update applicant"
						+ " set com_ID=? , pos_name=?"
						+ " where ID=?");
				psta.setString(1, (String)session.getAttribute("ID"));
				psta.setString(2, request.getParameter("pos_name"));
				psta.setString(3, request.getParameter("app_ID"));
				psta.executeUpdate();
				JDBCutil_c3p0.release(rs, psta, conn);
			}
			}
			else {
				conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("update apply "
						+ " set information=?"
						+ " where app_ID=? and com_ID=? and name=?");
				psta.setString(1, request.getParameter("information"));
				psta.setString(2, request.getParameter("app_ID"));
				psta.setString(3, (String)session.getAttribute("ID"));
				psta.setString(4, request.getParameter("pos_name"));
				psta.executeUpdate();
				JDBCutil_c3p0.release(rs, psta, conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
