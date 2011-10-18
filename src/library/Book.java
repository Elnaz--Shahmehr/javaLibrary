package library;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Book {

	private	String isbn;
	private	String title;
	private	String author;
	private	int borrowDate;
	private	int returnDate;
	private	int copyNumber;
	private	int statistics;
	private	int libraryCardNumber;

	public Book(){
		
	}
	
	public Book(String isbn, String title, String author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.copyNumber = 0;
		this.borrowDate = 0;
		this.returnDate = 0;
		this.statistics = 0;
		this.libraryCardNumber = 0;
	}

	public int isLate(){
		DateFormat df = new SimpleDateFormat("yyMMdd");
		return(Integer.parseInt(df.format(new Date())) - this.returnDate);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(int borrowDate) {
		this.borrowDate = borrowDate;
	}

	public int getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(int returnDate) {
		this.returnDate = returnDate;
	}

	public int getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}

	public int getStatistics() {
		return statistics;
	}

	public void setStatistics(int statistics) {
		this.statistics = statistics;
	}

	public int getLibraryCardNumber() {
		return libraryCardNumber;
	}

	public void setLibraryCardNumber(int libraryCardNumber) {
		this.libraryCardNumber = libraryCardNumber;
	}
	
	
}
