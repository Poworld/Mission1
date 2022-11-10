package seoulApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserDB {
	private Connection conn;
	private Statement stmt;

	public UserDB() {
	}

	public String DATABASE = "D:\\sqlite\\db\\seoul-wifi.db";

	public int insertDB(String sql) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			System.out.println(sql);
			int returnRow = stmt.executeUpdate(sql);
			if (returnRow > 0) {
				System.out.println(sql + " : " + " Success ");
			} else {
				System.out.println(sql + " : " + " Fail ");
			}
			stmt.close();
			conn.commit();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public String insertSql(double lat, double lnt) {
		String sql = "";
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		String now = dataFormat.format(new java.util.Date());

		sql += "INSERT OR IGNORE INTO USER_HISTORY (\"LAT\", \"LNT\", \"REG_DTTM\") VALUES( \"" + lat + "\", \"" + lnt + "\", \""
				+ now + "\" );";

		return sql;
	}
	
	public ArrayList<User> readDB(String sql) {
		ArrayList<User> list = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE);
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				int ID = rs.getInt("ID");
				double LAT = rs.getDouble(2);
				double LNT = rs.getDouble(3);
				String REG_DTTM = rs.getString(4);
				
				list.add(new User(ID, LAT, LNT, REG_DTTM));
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
