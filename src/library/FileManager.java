package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
	private PrintWriter printW;
	private Scanner FileScanner;
	private String fileName;
	private ArrayList<String> fileLines;

	public FileManager (String fileName){
		this.fileName = fileName;
	}

	public FileManager() {
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public static boolean exists(String fileName){
		File file = new File(fileName);
		return(file.exists());
	}

	public ArrayList<String> processFile(){
		fileLines = readFile(openFile(fileName));
		return fileLines;
	}

	public ArrayList<String> getFileLines() {
		return fileLines;
	}

	public void setFileLines(ArrayList<String> fileLines) {
		this.fileLines = fileLines;
	}

	private BufferedReader openFile(String fileName){
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			if (!br.ready()){
				System.out.println("The file '" + fileName  + "' can not be read");
				return null;
			}
			return br;	
		} catch(Exception e){
			System.out.println("The file '" + fileName  + "' can not be read");
		}
		return null;
	}

	private ArrayList<String> readFile(BufferedReader br) {
		String line;
		ArrayList<String> lines = new ArrayList<String>();

		try {
			while ( (line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.out.println("An Error happened while reading the file.");;
		}
		return lines;
		//Scanner inputFileScanner;

	}
	public void outputList(ArrayList list, String fileName) {
		if (useOutput(fileName)) {
			for (int i = 0; i < list.size(); i++)
			{
				printW.println(list.get(i));
			}
			printW.close();
		}
	}

	public boolean useOutputF(String filename) {
		try {
			this.printW = new PrintWriter(filename);
			return true;
		} catch (Exception e) {
			System.out.println("An Error to use the file: \""+ filename +"\"!");
			return false;
		}
	}

	private boolean useOutput(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean useInputF(String filename) {
		File fO = new File(filename);
		if (!fO.exists()) {
			System.out.println(filename +" An Error. Try again.");
			return false;
		} else {
			return true;
		}

	}

	public ArrayList<String> processFile(String string) {
		return null;
	}
	public void writer(ArrayList<String> lines){
		Writer output = null;
		File file = new File(fileName);
		try {
			output = new BufferedWriter(new FileWriter(file));
		
		for(String line : lines){
			output.write(line + "\n");
		}
		output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}

	public void useInputFile(String borrowersFile) {
		
	}
}