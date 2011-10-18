package library;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Library {

	private ArrayList<String> validLines = new ArrayList<String>();
	private ArrayList<Book> validBooks = new ArrayList<Book>();
	private Map<Integer, Borrower> borrowers= new HashMap<Integer, Borrower>();
	private Map<Integer, ArrayList<Book>> borrowersBooks= new HashMap<Integer, ArrayList<Book>>();
	private String newBooksPath;
	private DbManager db;
	
	private ArrayList<String> invalidLines = new ArrayList<String>();


	public void doPartOne(String bookPath,String borrowersPath, String dataBase, String userName, String passWord){

		
		FileManager booksFile = new FileManager(bookPath);
		validateBooks(booksFile.processFile(),false);
		
		FileManager borrowersFile = new FileManager(borrowersPath);
		readBorrowers(borrowersFile.processFile());
		
		Utilities.createDbSchema(dataBase, userName, passWord);
		db = new DbManager(dataBase, userName, passWord);
		db.insertBooks(validBooks);
		
		db.insertBorrowers(new ArrayList<Borrower>(borrowers.values()));
		
		
	}

	public DbManager getDb() {
		return db;
	}

	public void setDb(DbManager db) {
		this.db = db;
	}

	private void doPartTwo(){

	
	}

	private void printReciept(String borrowersPath) {

		for(Book book:validBooks){
			if (book.getLibraryCardNumber() != 0 && book.getReturnDate() != 0) {
				if(borrowersBooks.containsKey(book.getLibraryCardNumber())) {
					borrowersBooks.get(book.getLibraryCardNumber()).add(book);
				} else {
					ArrayList<Book> individualBooks = new ArrayList<Book>();
					individualBooks.add(book);
					borrowersBooks.put(book.getLibraryCardNumber(), individualBooks);
				}
			}
		}

		for(Borrower borrower:borrowers.values()){

			boolean hasAtLeastOneDueBook = false;
			String lateBooks = "";
			int totalOwe = 0;
			if(borrowersBooks.containsKey(borrower.getLibraryCardNumber())) {
				for (Book book:borrowersBooks.get(borrower.getLibraryCardNumber())) {
					if(book.isLate()>0) {
						hasAtLeastOneDueBook = true;
						int owe = book.isLate() * 2;
						totalOwe += owe;
						lateBooks = lateBooks + 
						book.getTitle() + ", amount due " + owe + ":-\n";
					}
				}

				if (hasAtLeastOneDueBook) {
					System.out.println(borrower + "\n");
					System.out.println("As of today " + new Date() +
							" you owe \"The little library\" " + totalOwe + " kr for the books \n");
					System.out.println(lateBooks + "\n");

				}
			}
		}

	}

	private void readBorrowers(ArrayList<String> lines) {

		String[] splittedText;
		for (String line:lines) {
			splittedText = line.split("#");
			Borrower borrower = new Borrower(
					splittedText[4], 
					Integer.parseInt(splittedText[0]),
					splittedText[1],
					splittedText[3],
					splittedText[2]);
			borrowers.put(borrower.getLibraryCardNumber(), borrower);
		}

	}

	private void doPartThree(){
		UserIneraction.help();
		
		Scanner keyIn = new Scanner(System.in);
		boolean quit = false;
		while (!quit) {
			String lineStr = keyIn.nextLine();

			if (lineStr.equalsIgnoreCase("quit")) {
				quit = true;
			}
			if (lineStr.equalsIgnoreCase("help")) {
				UserIneraction.help();
			}
			
			if (lineStr.equalsIgnoreCase("add")) {
				System.out.println("enter book(s):");
				String str = keyIn.nextLine();
				ArrayList<String> temp = new ArrayList<String>();
				
				for (String aBook:str.split(",")){
					temp.add(aBook);
				}
				
				validateBooks(temp,true);
				UserIneraction.help();
				continue;
			}
			if (lineStr.equalsIgnoreCase("borrow")) {
				//
			}	
		}
		
		FileManager newBooksFile = new FileManager(newBooksPath);
		newBooksFile.writer(validLines);
		System.out.println("Changes saved in '" + newBooksPath + "'");
	}

	private void validateBooks(ArrayList<String> books, boolean echo){
		
		String error;
		String[] splittedText;
		for (String book:books) {
			error = null;
			splittedText = book.split("#");

			if (splittedText.length == 6) {
				error += Validation.isValidISBN(splittedText[0]);
				error += Validation.isValidCopyNumber(splittedText[1]);
				error += Validation.isValidTitle(splittedText[2]);
				error += Validation.isValidAuthor(splittedText[3]);
				error += Validation.isValidStatistics(splittedText[4]);
				error += Validation.isValidBorrowDate(splittedText[5]);
				error = error.replaceAll("null", "");
				if (error.equals("")){
					validLines.add(book);
					if (echo) System.out.println(book);
					Book bookInstance = new Book();
					bookInstance.setIsbn(splittedText[0]);
					bookInstance.setCopyNumber(Integer.parseInt(splittedText[1]));
					bookInstance.setTitle(splittedText[2]);
					bookInstance.setAuthor(splittedText[3]);
					bookInstance.setStatistics(Integer.parseInt(splittedText[4]));
					bookInstance.setBorrowDate(Integer.parseInt(splittedText[5]));
					validBooks.add(bookInstance);
					continue;
				}
			}

			if (splittedText.length == 8) {
				error += Validation.isValidISBN(splittedText[0]);
				error += Validation.isValidCopyNumber(splittedText[1]);
				error += Validation.isValidTitle(splittedText[2]);
				error += Validation.isValidAuthor(splittedText[3]);
				error += Validation.isValidStatistics(splittedText[4]);
				error += Validation.isValidBorrowDate(splittedText[5]);
				error += Validation.isValidReturnDate(splittedText[6]);
				error += Validation.isValidLibraryCardNumber(splittedText[7]);
				error = error.replaceAll("null", "");
				
				if (error.equals("")){
					validLines.add(book);
					if (echo) System.out.println(book);
					Book bookInstance = new Book();
					bookInstance.setIsbn(splittedText[0]);
					bookInstance.setCopyNumber(Integer.parseInt(splittedText[1]));
					bookInstance.setTitle(splittedText[2]);
					bookInstance.setAuthor(splittedText[3]);
					bookInstance.setStatistics(Integer.parseInt(splittedText[4]));
					bookInstance.setBorrowDate(Integer.parseInt(splittedText[5]));
					bookInstance.setReturnDate(Integer.parseInt(splittedText[6]));
					bookInstance.setLibraryCardNumber(Integer.parseInt(splittedText[7]));
					validBooks.add(bookInstance);

					continue;
				}
			}

			invalidLines.add(error + "Lacking data;" + book);
			if (echo) System.out.println(error + "Lacking data;" + book);
		}
	}

	public void outputListToTextFile(Object validBooks2, String filenameStr) {
		// TODO Auto-generated method stub
		
	}



}
