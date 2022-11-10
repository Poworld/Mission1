package seoulApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SaveDB {
	private Connection conn;
	private Statement stmt;

	public SaveDB() {

	}

	public String DATABASE = "D:\\sqlite\\db\\seoul-wifi.db";

	public int insertDB(String sql) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

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

	public ArrayList<SeoulWifi> readDB(String sql) {
		ArrayList<SeoulWifi> list = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE);
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int index = 1;
				double DISTANCE = rs.getDouble(index++);
				String MGR_NO = rs.getString(index++);
				String WRDOFC = rs.getString(index++);
				String MAIN_NM = rs.getString(index++);
				String ADRES1 = rs.getString(index++);
				String ADRES2 = rs.getString(index++);
				String INSTL_FLOOR = rs.getString(index++);
				String INSTL_TY = rs.getString(index++);
				String INSTL_MBY = rs.getString(index++);
				String SVC_SE = rs.getString(index++);
				String CNSTC_YEAR = rs.getString(index++);
				String CMCWR = rs.getString(index++);
				String INOUT_DOOR = rs.getString(index++);
				String REMARS3 = rs.getString(index++);
				double LAT = rs.getDouble(index++);
				double LNT = rs.getDouble(index++);
				String WORK_DTTM = rs.getString(index++);
		
				list.add(new SeoulWifi(DISTANCE, MGR_NO, WRDOFC, MAIN_NM, ADRES1, ADRES2, INSTL_FLOOR, INSTL_TY, INSTL_MBY,
						SVC_SE, CNSTC_YEAR, CMCWR, INOUT_DOOR, REMARS3, LAT, LNT, WORK_DTTM));
				
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
