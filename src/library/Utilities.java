package library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class Utilities {

	
	public static String formatFileName(String fileName, String path){
		
		String filePath = path.toLowerCase().replaceAll(fileName.toLowerCase(), "");
		
		if (! filePath.endsWith("\\")) {
			filePath += "\\";
		}
		return(filePath + fileName);
	}
	
	public static void createDbSchema(String db, String user, String password){

		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1/information_schema";
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("The connection has been established successfully");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("An error happend!!!");
		}



		try {
			Statement s = conn.createStatement();
			ResultSet results = s.executeQuery(
					"SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + db + "';");
			if(!results.next()){
				s.execute("create database " + db + ";");
			}
			results.close();
			s.close();
		} catch (SQLException e) {
			System.out.println("An error happend!!!");
			e.printStackTrace();
		}

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		url = "jdbc:mysql://127.0.0.1/" + db;
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("The connection has been established successfully");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("An error happend!!!");
		}

		try {
			Statement s = conn.createStatement();
			ResultSet results = s.executeQuery("SELECT * " +
					" FROM information_schema.tables " +
					" WHERE table_schema = '" + db + "'" +
					"AND table_name = 'books';"); 
			if (results.next()) {
				s.execute("DROP TABLE books;");
			}
			results.close();
			s.execute("create table books (" +
					"isbn CHAR(13) ," +
					"copynumber INTEGER," +
					"title VARCHAR(50)," +
					"author VARCHAR(50)," +
					"statistics INTEGER," +
					"borrowdate DATETIME," +
					"returndate DATETIME," +
			"librarycardnumber INTEGER," +
			"PRIMARY KEY(isbn,copynumber));");
			s.close();
		} catch (SQLException e) {
			System.out.println("An error happend!!!");
			e.printStackTrace();
		}
		
		///////////////
		
		try {
			Statement s = conn.createStatement();
			ResultSet results = s.executeQuery("SELECT * " +
					" FROM information_schema.tables " +
					" WHERE table_schema = '" + db + "'" +
					"AND table_name = 'borrowers';"); 
			if (results.next()) {
				s.execute("DROP TABLE borrowers;");
			}
			results.close();
			s.execute("create table borrowers (" +
					"librarycardnumber INTEGER UNIQUE PRIMARY KEY," +		
					"name VARCHAR(50)," +
					"streetaddress VARCHAR(50)," +
					"postalcode VARCHAR(50)," +
					"city VARCHAR(50));");
			s.close();
		} catch (SQLException e) {
			System.out.println("An error happend!!!");
			e.printStackTrace();
		}
		
		///////////////
		

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
