package position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.JDBCutil_c3p0;

public class Position {
	String com_name;
	String name;
	String information;
	String city;
	double salary;
	String academic;
	int number;
	String type;
	public String getcom_name() {
		return com_name;
	}
	public String getname() {
		return name;
	}
	public String getinformation() {
		return information;
	}
	public String getcity() {
		return city;
	}
	public String getacademic() {
		return academic;
	}
	public double getsalary() {
		return salary;
	}
	public int getnumber() {
		return number;
	}
	public String gettype() {
		return type;
	}
	public void setcom_name(String com_name) {
		this.com_name=com_name;
	}
	public void setname(String name) {
		this.name=name;
	}
	public void setacademic(String academic) {
		this.academic=academic;
	}
	public void setcity(String city) {
		this.city=city;
	}
	public void setsalary(double salary) {
		this.salary=salary;
	}
	public void setinformation(String information) {
		this.information=information;
	}
	public void setnumber(int number) {
		this.number=number;
	}
	public void settype(String type) {
		this.type=type;
	}
	public void gettop(int size) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement("select * from positions"
					+ " order by hits desc limit ?, 1 ");
			psta.setInt(1, size-1);
			rs=psta.executeQuery();
			if(rs.next()) {
				this.com_name=rs.getString("com_name");
				this.name=rs.getString("name");
				this.information=rs.getString("information");
				this.city=rs.getString("city");
				this.academic=rs.getString("academic");
				this.salary=Double.valueOf(rs.getString("salary"));
				this.number=Integer.valueOf(rs.getString("number"));
				this.type=rs.getString("type");
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
}
	public String publish(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String info="";
		String posname=request.getParameter("posname");
        String city=request.getParameter("city");
		String academic=request.getParameter("academic");
		String information=request.getParameter("information");
		String type=request.getParameter("pos_type");
		if(posname == null || posname.equals(""))
			info="职位名称不能为空！";
		else if (request.getParameter("salary") == null || request.getParameter("salary").equals("")) 
			info="月薪不能为空！";
		else if (city == null || city.equals(""))
				info="工作地点不能为空!";
		else if (request.getParameter("number") == null || request.getParameter("number").equals("")) 
				info="人数不能为空 ";
		else {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			psta=conn.prepareStatement(
					"insert into positions(com_ID,com_name,name,information,hits,salary,city,academic,number,type)"
					+ " value(?,?,?,?,0,?,?,?,?,?) ");
			psta.setString(1, (String) session.getAttribute("ID"));
			psta.setString(2, (String) session.getAttribute("name"));
			psta.setString(3, posname);
			psta.setString(4, information);
			psta.setDouble(5, Double.valueOf(request.getParameter("salary")));
			psta.setString(6, city);
			psta.setString(7, academic);
			psta.setInt(8, Integer.valueOf(request.getParameter("number")));
			psta.setString(9, type);
			psta.executeUpdate();
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		return info;
		
	}
	public Position[] getspec(HttpServletRequest request) {
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement psta=null;
		Position[] out=null;
		try {
			conn=JDBCutil_c3p0.getconn();
			if(((String)request.getParameter("com_ID")).length()!=0) {
			psta=conn.prepareStatement("select * from positions"
					+ " where com_ID=? and LOCATE(?, name)>0 and LOCATE(?, com_name)>0",ResultSet.TYPE_SCROLL_INSENSITIVE);
			psta.setString(1, (String)request.getParameter("com_ID"));
			psta.setString(2, (String)request.getParameter("pos_name"));
			psta.setString(3, (String)request.getParameter("com_name"));
			}
			else {
				psta=conn.prepareStatement("select * from positions"
						+ " where LOCATE(?, name)>0 and LOCATE(?, com_name)>0",ResultSet.TYPE_SCROLL_INSENSITIVE);
				psta.setString(1, (String)request.getParameter("pos_name"));
				psta.setString(2, (String)request.getParameter("com_name"));
			}
			rs=psta.executeQuery();
			rs.last(); 
			out=new Position[rs.getRow()];
			rs.beforeFirst(); 
			for(int i=0;rs.next();i++) {
				out[i]=new Position();
				out[i].setcom_name(rs.getString("com_name"));
				out[i].setname(rs.getString("name"));
				out[i].setinformation(rs.getString("information"));
				out[i].setcity(rs.getString("city"));
				out[i].setacademic(rs.getString("academic"));
				out[i].setsalary(Double.valueOf(rs.getString("salary")));
				out[i].setnumber(Integer.valueOf(rs.getString("number")));
				out[i].settype(rs.getString("type"));
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
		return out;
		
	}
}
