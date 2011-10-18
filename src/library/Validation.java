package library;
import java.io.File; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Validation {

	private static String booksText;

	/**
	 * 
	 * @param isbn
	 * @return
	 */
	public static String isValidISBN(String isbn){

		boolean valid = false;
		String isbnNumbers = isbn.toLowerCase().
		replaceAll("-", "").replaceAll("x", "a"); //khlkjlkjj;jj;l;

		char[] splittedISBN = isbnNumbers.toCharArray();
		int sum = 0;
		if(splittedISBN.length == 10){

			for(int i=0; i < 9 ; i++){
				sum += Character.digit(splittedISBN[i],10) * (10 - i);
			}
			if(sum % 11 + Character.digit(splittedISBN[9],16) == 11) {
				valid = true;
			}
		}

		return (valid ? null : "Wrong ISBN-number;" );

	}




	public static String isValidTitle(String title){

		return (title.trim().length()>0 ? null : "Wrong Title;");
	}




	public static String isValidCopyNumber(String copyNumber){

		return (isNumeric(copyNumber) ? null : "Wrong copy number;" );
	}



	private static boolean isNumeric(String copyNumber) {
		
		return(copyNumber.matches("\\d+"));
	}




	private static boolean validNum(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	public static String isValidAuthor(String author){

		return (author.trim().length()>0 ? null : "Wrong Author;");
	}

	public static String isValidBorrowDate(String borrowdate){

		return (isValidDate(borrowdate) ? null :  "Wrong BorrowDate;" );
	}


	private static boolean isValidDate(String date){

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		dateFormat.setLenient(false);

		try {
			//parse the inDate parameter
			dateFormat.parse(date.trim());
		}
		catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static String isValidReturnDate(String returnDate){

		return (isValidDate(returnDate) ? null : "Wrong ReturnDate;" );
	}






	public static String isValidLibraryCardNumber(String librarycardnumber){


		return (isNumeric(librarycardnumber) ? null : "Wrong LibraryCardNumber");
	}



	public static String isValidStatistics(String statistics){

		return (isNumeric(statistics) ? null:"Wrong statistics");
	}

}

