package com.makaan.Utilities;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.NoSuchElementException;

public class ConnectDB {

	// JDBC driver name and database URL
	// public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// public static String DB_URL =
	// "jdbc:mysql://prop-mp-slave.clnbagmrulmr.ap-southeast-1.rds.amazonaws.com";

	// public static String DB_URL = "jdbc:mysql://beta-db.proptiger-ws.com";

	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	// Database credentials
	// public static String USER = "s3l3ct_us3r";
	// public static String PASS = "6E6q:vWMT^F";

	// public static String USER = "select-beta";
	// public static String PASS = "root@123";
	public static Connection conn = null;
	public static Statement stmt = null;

	public void Connect() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			String DB_URL = ReadSheet("DataBase", "DB_URL", 2);
			String USER = ReadSheet("DataBase", "User", 2);
			String PASS = ReadSheet("DataBase", "Password", 2);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String ReadSheet(String Sheet, String Col_Name, int row_id)
			throws IOException, NoSuchElementException, TimeoutException {

		String data = xls.getCellData(Sheet, Col_Name, row_id);
		System.out.println("Data from sheet " + data);

		return (data);
	}

	public ResultSet Execute(String Query, String Database) throws SQLException {
		ResultSet rs = null;
		ResultSet rs1 = null;

		try {
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql, sql1;
			ArrayList<String> arr = new ArrayList();
			sql = Database;
			sql1 = Query;
			System.out.println("connected to proptiger");
			rs = stmt.executeQuery(sql);
			rs1 = stmt.executeQuery(sql1);
			
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return rs1;

	}

	public void Close() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException se2) {
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

}
