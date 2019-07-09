package apply;

import java.io.File;
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
	Company com=new Company();
	String name;
	String information;
	public void setinformation(String information) {
		if(this.information==null)
		this.information=information;
	}
	public Company getcom() {
		return com;
	}
	public String getcom_name() {
		return com.getname();
	}
	public String getinformation() {
		return information;
	}
	public String getavator() {
		return com.getavator();
	}
	public void setstatus(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		HttpSession session = request.getSession();
		try {
			if(!request.getParameter("new_status").equals("meeting")) {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select material from apply"
					+ " where app_ID=? and com_ID=? and name=?");
			psta.setString(1, request.getParameter("app_ID"));
			psta.setString(2, (String)session.getAttribute("ID"));
			psta.setString(3, request.getParameter("pos_name"));
			rs=psta.executeQuery();
			if(rs.next()) {
				String filepath=rs.getString("material");
				File file = new File(filepath);  
		        if (file.exists() && file.isFile())
		        	file.delete();
			}
			JDBCutil_c3p0.release(rs, psta, conn);
			if(request.getParameter("new_status").equals("接受")) {
				conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("delete from apply"
						+ " where app_ID=? and com_ID=? and name=?");
				psta.setString(1, request.getParameter("app_ID"));
				psta.setString(2, (String)session.getAttribute("ID"));
				psta.setString(3, request.getParameter("pos_name"));
				psta.executeUpdate();
				psta=conn.prepareStatement("update applicant"
						+ " set com_ID=? , pos_name=?"
						+ " where ID=?");
				psta.setString(1, (String)session.getAttribute("ID"));
				psta.setString(2, request.getParameter("pos_name"));
				psta.setString(3, request.getParameter("app_ID"));
				psta.executeUpdate();
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
				JDBCutil_c3p0.release(rs, psta, conn);
			}
			else {
				JDBCutil_c3p0.release(rs, psta, conn);
				conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("update apply "
						+ " set status='refuse', datetime=now() "
						+ " where app_ID=? and com_ID=? and name=?");
				psta.setString(1, request.getParameter("app_ID"));
				psta.setString(2, (String)session.getAttribute("ID"));
				psta.setString(3, request.getParameter("pos_name"));
				psta.executeUpdate();
				JDBCutil_c3p0.release(rs, psta, conn);
			}
			}
			else {
				conn=JDBCutil_c3p0.getconn();
				psta=conn.prepareStatement("update apply "
						+ " set information=?, status='meeting', datetime=now() "
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
	public Apply[] getallinfo(String app_ID) {
		Apply[] out=null;
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from apply join company on company.ID=apply.com_ID "
					+ " where apply.app_ID=? and status<>'active'"
					+ " order by datetime desc, company.ID ",ResultSet.TYPE_SCROLL_INSENSITIVE);
			psta.setString(1, app_ID);
		rs=psta.executeQuery();
		rs.last(); 
		out=new Apply[rs.getRow()];
		rs.beforeFirst(); 
		for(int i=0;rs.next();i++) {
			out[i]=new Apply();
			String status=rs.getString("status");
				if(status.equals("meeting"))
				out[i].setinformation("关于您应聘 "+rs.getString("name")+" 一职的面试通知："+rs.getString("information"));
				else
					out[i].setinformation("非常感谢您申请本公司 "+rs.getString("name")+" 一职，很遗憾因您工作履历与我们发展方向不匹配，没有录取您。给您带来了不便请谅解。");
			out[i].getcom().setname(rs.getString("company.name"));
			out[i].getcom().setavator(rs.getString("company.avator"));
		}
		JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
		
	}
}
