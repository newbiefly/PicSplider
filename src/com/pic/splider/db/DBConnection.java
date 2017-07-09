package com.pic.splider.db;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
	private   Connection connection ;
	
	
	public DBConnection()
	{
		String databaseName = Config.MYSQLNAME;
		String host = Config.MYSQLHOST;
		String port = Config.MYSQLPORT;
		String username = Config.USER;
		String password = Config.PWD;
		String driverName = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://";
		String serverName = host + ":" + port + "/";
      	String connName = dbUrl + serverName + databaseName+"?useUnicode=true&characterEncoding=utf-8";
      	
      
		/******接着连接并选择数据库名为databaseName的服务器******/
      	try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(connName, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public  Connection getConnection() {
		return connection;
	}

}
