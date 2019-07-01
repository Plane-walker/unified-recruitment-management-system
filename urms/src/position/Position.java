package position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.JDBCutil_c3p0;

public class Position {
	String com_name;
	String name;
	String information;
	public String getcom_name() {
		return com_name;
	}
	public String getname() {
		return name;
	}
	public String getinformation() {
		return information;
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
			}
			JDBCutil_c3p0.release(rs, psta, conn);
		} catch (SQLException e) {
			e.printStackTrace();
	}
}
}
