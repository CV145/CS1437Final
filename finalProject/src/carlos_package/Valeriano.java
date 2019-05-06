package carlos_package;

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;

//class containing useful and reusable static methods
//helps keep the other classes clean and focused
public class Valeriano {

	    //prints a line
		public static void println(String output)
		{
			System.out.println(output);
		}
		
		//method writes a line to a file
		public static void appendLineToFile(String lineToAppend, String fileName) throws IOException
		{
			File file = new File(fileName); //create an instance of File class
			FileWriter fileWriter = null;
			PrintWriter printWriter = null;
			
			if (!file.exists())
			{
				file.createNewFile(); 
			}
			
			try { fileWriter = new FileWriter(file, true); } //append to file
			catch(IOException e) { Valeriano.println("there was an error creating a FileWriter for " + file.getName()); }
			
			//PrintWriter(FileWriter) works because FileWriter inherits OutputStreamWriter
			//PrintWriter needs an OutputStream class to write to
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(lineToAppend);
			
			try { openFileOnDesktop(file); } catch (IOException io) { println("issue opening on desktop"); }

			outputFileDirectory(file);
			
			printWriter.close();
			try { fileWriter.close(); } catch(IOException e) { println("trouble closing fileWriter"); }
		}

		//method tries to automatically open a file
		public static void openFileOnDesktop(File file) throws IOException {
			Desktop desktop;
			if (!Desktop.isDesktopSupported())
			{ Valeriano.println("Desktop class not supported"); }
			else  
			{ 
				desktop = Desktop.getDesktop();
				  if (file.exists()) { desktop.open(file); }
				  else { println(file.getName() + " file does not exist"); }
				}
			}

		//look for and print the directory of where a file is located
		public static void outputFileDirectory(File file) {
			String fileDirectory;
			fileDirectory = file.getAbsolutePath();
			if (fileDirectory != null) { Valeriano.println("\n" + "The directory for the file is: " + fileDirectory + "\n"); }
		}

		//method returns a string of a formatted date
		public static String formatDate(Date dateToFormat, String formatPattern)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
			//println("\n" + "debug - formatted date: " + simpleDateFormat.format(dateToFormat) + "\n");
			return simpleDateFormat.format(dateToFormat);
		}
		
		//keep taking user input until it successfully converts to an int
		public static int inputStringAndConvertToInt() {
			Scanner kbd = new Scanner(System.in);
			boolean success = false;
			int output = 0;
			String input;
			do
			{
				input = kbd.nextLine();
				try {
				output = Integer.parseInt(input);
				success = true;
				}
				catch (NumberFormatException e)
				{
					success = false;
					println("Input not a number please try again.");
				}
			}
			while (!success);
			return output;
		}

		//similar to previous method but converts to a float instead
		public static float inputStringAndConvertToFloat()
		{
			Scanner kbd = new Scanner(System.in);
			boolean success = false;
			float output = 0;
			String input;
			do
			{
				input = kbd.nextLine();
				try {
				output = Float.parseFloat(input);
				success = true;
				}
				catch (NumberFormatException e)
				{
					success = false;
					println("Input not a number please try again.");
				}
			}
			while (!success);
			return output;
		}
		
		//method checks if a string matches a pattern
		public static boolean CheckIfStringIsInFormat(String input, String regularExpressionPattern)
		{
		//format pattern... ".*/.*"  is a regular expression
		 return Pattern.matches(regularExpressionPattern, input);
		}
		
		//tokenizes a string and returns an array list of tokens
		public static ArrayList<String> tokenizeString(String stringToTokenize, String delimiter)
		{
		 StringTokenizer tokenizer;
		 if (delimiter != null)
		 {
			 tokenizer = new StringTokenizer(stringToTokenize, delimiter); 
		 }
		 else
		 {
			 tokenizer = new StringTokenizer(stringToTokenize); //in case null was passed in
		 }
		 
		 ArrayList<String> tokens = new ArrayList<String>();
		 while (tokenizer.hasMoreTokens())
		 {
		  tokens.add(tokenizer.nextToken());
		 }
		 return tokens;
		}
		
		//gets a scanner for an inputted file name, which is searched for 
		public static Scanner getScannerForFile(String fileName) throws FileNotFoundException, IOException
		{
			FileReader fileToOpen = new FileReader(fileName); 	
			return new Scanner(fileToOpen);
		}

}
