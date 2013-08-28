package com.encima.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.drools.KnowledgeBase;

public class DBTools {

	public static Connection dbConnect(String user, String pwd, String db) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connString = "jdbc:mysql://localhost:3306/" + db;
			Connection conn = DriverManager.getConnection(connString, user, pwd);
			return conn;
		}catch(Exception e) {
			return null;
		}
	}
	
	public static ResultSet execute(Connection conn, String query) {
		Statement stmt;
		ResultSet rs;
		try {
			stmt = conn.createStatement();
//			System.out.println(query);
			Boolean res = stmt.execute(query);
			if(res)
				rs = stmt.getResultSet();
			else
				return null; 
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error closing connection");
			e.printStackTrace();
		}
	}
	
	public static int getID(Connection conn, String table, String field, String max) {
		String query = "SELECT %s(%s) AS %s FROM %s";
		ResultSet rs = execute(conn, String.format(query, max, field, field, table));
		try {
			rs.first();
			return rs.getInt(field) + 1;
		} catch (SQLException e) {
			System.out.println(e);
			return 1;
		}
	}
   
   public static void addDBRulesFromFile(String db, String user, String pwd, String path) {
		Connection conn = dbConnect(user, pwd, db);
		String rules = FileTools.readFileAsString(path);
		int id = getID(conn, "rules", "id", "MAX");
		String insert = "INSERT INTO rules VALUE(%d, '%s');";
		execute(conn, String.format(insert, id, rules));
		close(conn);
	}
   
   public static void loadDBRules(String db, String user, String pwd, int id, KnowledgeBase kbase) {
	   String query = "SELECT * FROM rules";
	   //Only select particular rules if the user has specified
	   if(id > 0) {
		   query = "SELECT * FROM rules WHERE id = " + id + ";";
	   }
	   Connection conn = dbConnect(user, pwd, db);
	   ResultSet rs = execute(conn, query);
	   try {
			while(rs.next()) {
				String rule = rs.getString("rule");
				DroolsTools.addRule(rule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   close(conn);
   }
   
   public static Date sqlDateToJavaDate(String date) {
	   SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
	   Date parsed = null;
	   try {
		parsed = format.parse(date);
	   } catch (ParseException e) {
		e.printStackTrace();
	   }
	   return parsed;
   } 
   
   public static List<String> getLocationDescription(int locID) {
		Connection cnx = dbConnect("root", "root", "gsn");
		ResultSet rs = execute(cnx, String.format("SELECT * FROM location WHERE id=%d;", locID));
		ArrayList<String> descrip = new ArrayList<String>();
		try {
			if(rs.next()) {
				return Arrays.asList(rs.getString("description").split(","));
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
