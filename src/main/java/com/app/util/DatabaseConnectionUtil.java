package com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {
	
private	static Connection connection;
	
	
	public static Connection getDatabaseConnection(){
		try {
			if(connection!=null && !connection.isClosed()){
				return connection;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Conection Exception....");
		}
		try{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_boot_jwt?serverTimezone=UTC&noDatetimeStringSync=true","root","");
			return connection;
		}catch(SQLException sqlEx){
			sqlEx.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return connection;
	}
	public static void main(String[] args) {
		
		Connection dbCon = DatabaseConnectionUtil.getDatabaseConnection();
		System.out.println(dbCon);
	}

}
