package library;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DbManager {

	private	Connection conn= null;
	public DbManager(String db, String user, String password){
		String url = "jdbc:mysql://127.0.0.1/" + db;
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("The connection has been established successfully");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("An error happend!!!");
		}



	}
	private String getSQLInsertInToNewBooks(Book book){
		return "INSERT INTO books " +
		" Select '" + book.getIsbn() + "',R.copynumber, '" +
		book.getTitle() + "','" +
		book.getAuthor() + "',0,0,0,0 FROM " +
		"(select (IFNull(Max(copynumber),0)+1) as copynumber from books where isbn ='" 
		+ book.getIsbn() + "') R;";
	}

	private String getSQLInsertInToBooks(Book book){
		return "INSERT INTO books " +
		"(isbn, copynumber , title , author , statistics, borrowdate, returndate, librarycardnumber) Values " +
		"('" + book.getIsbn() +"'," + 
		book.getCopyNumber() + ",'" +
		book.getTitle() + "','" +
		book.getAuthor() + "'," +
		book.getStatistics() + "," +
		book.getBorrowDate() + "," +
		book.getReturnDate() + "," +
		book.getLibraryCardNumber() + ");";
	}

	private String getSQLInsertInToNewBorrower(Borrower borrower){
		return "INSERT INTO borrowers " +
		"Select R.librarycardnumber,'" +
		borrower.getName() + "','" + 
		borrower.getStreetAddress() +"','" + 
		borrower.getPostalCode() +"','" + 
		borrower.getCity() +"' FROM " +
		"(select (IFNull(Max(librarycardnumber),0)+1) as librarycardnumber from borrowers) R;";
	}


	private String getSQLInsertInToBorrower(Borrower borrowers){
		return "INSERT INTO borrowers " +
		"(librarycardnumber, name , streetaddress , postalcode, city) Values " +
		"(" + borrowers.getLibraryCardNumber() +",'" + 
		borrowers.getName() + "','" +
		borrowers.getStreetAddress() + "','" +
		borrowers.getPostalCode() + "','" +
		borrowers.getCity() + "');";
	}

	public void insertBorrowers(ArrayList<Borrower> borrowers) {

		try {
			Statement s = conn.createStatement();
			for(Borrower b:borrowers){
				System.out.println(getSQLInsertInToBorrower(b));
				s.executeUpdate(getSQLInsertInToBorrower(b));
			}

		} catch (SQLException e) {
			System.out.println("An error happend");
			e.printStackTrace();
		}
	}

	public void insertBorrower(Borrower borrower) {

		try {
			Statement s = conn.createStatement();
			System.out.println(getSQLInsertInToNewBorrower(borrower));
			s.executeUpdate(getSQLInsertInToNewBorrower(borrower));
		} catch (SQLException e) {
			System.out.println("An error happend");
			e.printStackTrace();
		}
	}

	public void insertBooks(ArrayList<Book> validBooks) {

		try {
			Statement s = conn.createStatement();
			for(Book book:validBooks){
				System.out.println(getSQLInsertInToBooks(book));
				s.executeUpdate(getSQLInsertInToBooks(book));
			}

		} catch (SQLException e) {
			System.out.println("An error happend");
			e.printStackTrace();
		}
	}

	public void insertBook(Book book) {

		try {
			Statement s = conn.createStatement();
			System.out.println(getSQLInsertInToNewBooks(book));
			s.executeUpdate(getSQLInsertInToNewBooks(book));

		} catch (SQLException e) {
			System.out.println("An error happend");
			e.printStackTrace();
		}
	}

	public String borrowBook(String isbn,int libraryCardNumber){
		try{


			Statement s = conn.createStatement();
			ResultSet results = s.executeQuery(
					"SELECT * from borrowers where librarycardnumber = " + libraryCardNumber + ";");
			if(!results.next()){
				results.close();
				return "The Library card number cannot be found!";
			}
			results.close();

			results = s.executeQuery(
					"SELECT * FROM books where " +
					" isbn = '" + isbn + "' and librarycardnumber = 0;");
			if(!results.next()){
				results.close();
				return "The book with specified ISBN cannot be found or all copies have been borrowed!";
			}
			int copyNumber = results.getInt("copynumber");
			results.close();
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");

			c.add(Calendar.DATE, 14);
			System.out.println("update books " +
					" set librarycardnumber = " + libraryCardNumber + ", borrowdate = '" + sdf.format(new Date()) + "' , returndate ='" + sdf.format(c.getTime()) +
					"' Where copynumber = " + copyNumber +
			" And isbn = '" + isbn + "';");
			s.executeUpdate("update books " +
					" set librarycardnumber = " + libraryCardNumber + ", borrowdate = '" + sdf.format(new Date()) + "' , returndate ='" + sdf.format(c.getTime()) +
					"' Where copynumber = " + copyNumber +
			" And isbn = '" + isbn + "';");

		} catch (Exception e) {
			System.out.println("An error happend");
			e.printStackTrace();
		}
		return null;

	}

	public void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("An error happend");
			e.printStackTrace();
		}
	}

}


