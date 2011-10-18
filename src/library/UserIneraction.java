package library;
import java.util.Scanner;


public class UserIneraction {

	
	public static String askFilePath(String fileName, String defaultPath, boolean checkExistance){
		Scanner keyboardInput = new Scanner(System.in);
		String filePath;
		String formattedFileName; 

		while (true) {

			System.out.println("Where's (" + fileName + ") located?");
			filePath = keyboardInput.nextLine();
			if (filePath.equals("")) filePath = defaultPath;

			formattedFileName = Utilities.formatFileName(fileName, filePath);
			if (checkExistance) {
				if (FileManager.exists(formattedFileName)) {
					return (formattedFileName);
				}
			} else {
				return (formattedFileName);
			}


			System.out.println("The file '" + formattedFileName + "' doesn't exist!" );
			System.out.println("Check the path and enter it again." );
		}

	}
	public static void help() {
		System.out.println("Enter \"help\" for getting help and \"quit\" to quit.");
		
		System.out.println("Enter \"add\" for adding a new book.");
		System.out.println("Enter books information and seperate each item with '#' and each book with ','");
		
		System.out.println("Enter \"borrow\" for borrowering books.\n");

	}
}
