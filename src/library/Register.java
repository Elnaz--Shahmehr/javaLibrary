package library;

import java.util.Scanner; 
import java.util.ArrayList;



public class Register {
	private FileManager line;
	private UserIneraction ui = new UserIneraction();
	private Scanner keyIn = new Scanner(System.in);
	private ArrayList list = new ArrayList();

	public void start(){

		registerNewBooks();

	}

	public void registerNewBooks() {
		list.clear();
		System.out.println("Where is \"NewBook.txt\"?  "
		);

		String filePath = keyIn.nextLine();
		readInBookfile(filePath);
		userMenu();
		outputToFile(filePath);
	}

	private void readInBookfile(String filePath) {
		line = new FileManager();
		System.out.println("Where is \"NewBook.txt\" ? ");

	}

	private void outputToFile(String filenameStr) {
		line.outputList(list, filenameStr);
	}

	private void userMenu() {
		boolean quit = false;
		help();
		while (!quit) {
			String lineStr = keyIn.nextLine();

			if (lineStr.equalsIgnoreCase("quit")) {
				quit = true;
			}
			if (lineStr.equalsIgnoreCase("help")) {
				help();
			} else {
				if (addBook(lineStr)) {
					System.out.println(lineStr+" was added successfully.");
				} else {
					System.out.println("The line was not added.");
				}
			}
		}
	}


	private void help() {
		System.out.println("\nEnter \"help\" ===> help and ====>  \"quit\" to quit .\n");
		System.out.println("Enter the line or textfile with books");
		System.out.println("each field with #");

	}

	private boolean addBook(String line) {
		String lineStr = line;
		//line = ui.returnValidRow(line);
		if (line == null) {
			list.add(lineStr);
			return true;
		} else {
			System.out.println(line+" is not a valid row");
			return false;
		}
	}
}