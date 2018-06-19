package com.task.android.wangyiyun.util;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager {

	// 数据库连接常量
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASS = "h1616408";
	//public static final String URL = "jdbc:mysql://192.168.1.104/han";
	public static final String URL = "jdbc:mysql://47.106.70.149/android_friend";


	// 静态成员，支持单态模式
	private static DBManager per = null;
	private Connection conn = null;
	private Statement stmt = null;

	// 单态模式-懒汉模式
	private DBManager() {
	}

	public static DBManager createInstance() {
		if (per == null) {
			per = new DBManager();
			per.initDB();
		}
		return per;
	}

	// 加载驱动
	public void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 连接数据库，获取句柄+对象
	public void connectDB() {
		System.out.println("Connecting to database...");
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			per = null;
			e.printStackTrace();
			Log.i("DBManager", "connectDB: failure");
		}
		System.out.println("SqlManager:Connect to database successful.");
	}

	public Connection getConnection(){
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

		} catch (SQLException e) {

			Log.i("DBManager", "connectDB: failure");
		}
		return con;
	}

	// 关闭数据库 关闭对象，释放句柄
	public void closeDB() {
		System.out.println("Close connection to database..");
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Close connection successful");
	}

	// 查询
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			if (stmt == null){
				return null;
			}
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 增添/删除/修改
	public int executeUpdate(String sql) {
		int ret = 0;
		try {
			ret = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
