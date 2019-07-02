package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCutil_c3p0 {
	private static ComboPooledDataSource ds = null;
	static {
		ds=new ComboPooledDataSource("MySQL");
	}
	public static Connection getconn() throws SQLException {
		return ds.getConnection();
	}
	public static void release(ResultSet rs,PreparedStatement psta,Connection conn) {
		try {
			if(rs!=null)
			rs.close();
			if(psta!=null)
			psta.close();
			if(conn!=null)
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
