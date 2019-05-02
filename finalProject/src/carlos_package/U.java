package carlos_package;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//class containing useful and reusable static methods
public class U {

	    //prints a line
		public static void println(String output)
		{
			System.out.println(output);
		}
		
		//method writes a line to a file
		public static void appendLineToFile(String lineToAppend, String fileName)
		{
			File file = new File(fileName); //create an instance of File class
			FileWriter fileWriter = null;
			PrintWriter printWriter = null;
			
			if (!file.exists())
			{
				try { file.createNewFile(); }
				catch (IOException e) { U.println("there was an error creating the file: " + fileName + " \n " + "maybe it already exists?"); }
			}
			
			try { fileWriter = new FileWriter(file, true); } //append to file
			catch(IOException e) { U.println("there was an error creating a FileWriter for " + file.getName()); }
			
			//PrintWriter(FileWriter) works because FileWriter inherits OutputStreamWriter
			//PrintWriter needs an OutputStream class to write to
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(lineToAppend);
			
			openFileOnDesktop(file);

			outputFileDirectory(file);
			
			printWriter.close();
			try { fileWriter.close(); } catch(IOException e) { println("trouble closing fileWriter"); }
		}

		//method tries to automatically open a file
		public static void openFileOnDesktop(File file) {
			Desktop desktop;
			if (!Desktop.isDesktopSupported())
			{ U.println("Desktop class not supported"); }
			else  
			{ 
				desktop = Desktop.getDesktop();
				  if (file.exists()) { try {desktop.open(file);} catch(IOException e) { U.println("unable to open file using Desktop class"); }
				}
			}
		}

		//look for and print the directory of where a file is located
		public static void outputFileDirectory(File file) {
			String fileDirectory;
			fileDirectory = file.getAbsolutePath();
			if (fileDirectory != null) { U.println("\n" + "The directory for the file is: " + fileDirectory + "\n"); }
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
}
