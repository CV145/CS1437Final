import java.util.*;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.awt.Desktop;
import java.io.*;

//driver class
public class SalesSP19Report_Valeriano {

	private static Scanner kbd;
	private static Calendar calendar;
	private static SimpleDateFormat fullDateFormat;
	private static SimpleDateFormat dayFormat;
	private static Date currentDate;
	private static String fullDateString;
	private static int currentDay;
	private static ProductSP19_Valeriano instance;
	private static int transactionNum = 1; //set to 1 every time program is run 
	
	
	public static void main(String[] args) {
	    calendar = Calendar.getInstance();
		fullDateFormat = new SimpleDateFormat();
		dayFormat = new SimpleDateFormat("dd");
		kbd = new Scanner(System.in);
		menuLoop();
	}
	
	//loops through the menu as long as the user wants
	private static void menuLoop()
	{
		int input;
		do
		{
			refreshDate();
			displayMenu();
			input = inputStringAndConvertToInt();
			switch(input)
			{
			case 1:
				saleProduct();
				break;
			case 2:
				endingDayReport();
				break;
			case 3:
				endingMonthReport();
				break;
			case 4:
				endingYearReport();
				break;
			case 0:
				break;
			}
		}
		while (input != 0);
		println("Have a nice day!");
	}

	//refreshes and initializes the date
	private static void refreshDate() {
		currentDate = new Date(); //instantiate Date object with no args.. sets to current date at the very moment
		fullDateString = fullDateFormat.format(currentDate); //DateFormat.format(date) returns a string representing the date
		calendar.setTime(currentDate); //calendar needs a date object to set time
		currentDay = Integer.parseInt(dayFormat.format(currentDate));
	}

	private static void displayMenu() {
		println("");
		println("SALE SPRING 2019 PRODUCTS--Carlos Valeriano");
		println("Today: " + currentDate);
		println("1. Sale Product");
		println("2. Ending day sale report");
		println("3. Ending month sale report");
		println("4. Ending year sale report");
		println("0. Exit");
		println("Please enter your choice below:");
		println("");
	}
	
	//method used to make a transaction
	//first check if it's still the same day, if not then update the day to today and reset the transaction number
	//ask user for input to gather information, create an instance of data type class, then pass the information to it and call methods for printing receipt and outputting to file
	private static void saleProduct()
	{
		int sp191, sp192, sp193;
		float amountPaid;
		String fileName;
		
		
		if (currentDay != Integer.parseInt(dayFormat.format(currentDate)))
		{
			currentDay = Integer.parseInt(dayFormat.format(currentDate));
			transactionNum = 1;
		}
		
		String dayString;
		if (currentDay < 10) { dayString = "0" + currentDay; }
		else { dayString = "" + currentDay; }

		println("-- Sale Product Transaction --");
		println("How many SP191 units were bought?");
		sp191 = inputStringAndConvertToInt();
		println("How many SP192 units were bought?");
		sp192 = inputStringAndConvertToInt();
		println("How many SP193 units were bought?");
		sp193 = inputStringAndConvertToInt();
		println("How much did the customer pay?");
		amountPaid = inputStringAndConvertToFloat();
		
		
		instance = new ProductSP19_Valeriano(sp191, sp192, sp193);
		String transactionString = String.format("%s%04d", dayString, transactionNum);
		instance.printReceipt(fullDateString, transactionString, amountPaid);
		
		
		fileName = "daySale_" + formatDate(currentDate, "yyyyMMdd") + ".txt";
		String lineToAppend = String.format("%s   %d   %d   %d", transactionString, sp191, sp192, sp193);
		try { appendLineToFile(lineToAppend, fileName); }
		catch (IOException io) { println("the file: " + fileName + " threw an IOException"); }
		
		transactionNum++;
	}

	
	//gets info from day file, adds it to month file
	//display day info
	private static void endingDayReport()
	{
		Scanner fileScanner;
		boolean repeatLoop = true;
		do
		{
			println("Please input the day to run the report for in the following format: mm/dd/yyyy");
			println("(Enter 0 to return to the menu)");
			String chosenDay = kbd.nextLine();
			
			//String is checked using a regular expression
			if (CheckIfStringIsInFormat(chosenDay, "\\d{2}/\\d{2}/\\d{4}"))
			{
				String mm, dd, yyyy;
				ArrayList<String> splitDate = tokenizeString(chosenDay, "/");
				mm = splitDate.get(0); dd = splitDate.get(1); yyyy = splitDate.get(2); 
				String fileName = "daySale_" + yyyy + mm + dd + ".txt";
				try { fileScanner = getScannerForFile(fileName); }
				catch (FileNotFoundException f)
				{
					println("No file called " + fileName + " was found. Please make sure the requested date was inputted as all numbers in the following format: mm/dd/yyyy and try again"); continue;
				}
				catch (IOException io) { println("IO exception occured"); continue; }
				
				
				ArrayList<Integer> sp191Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp192Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp193Sum = new ArrayList<Integer>();
				
				//for each line in the scanner, return a string
				while (fileScanner.hasNextLine())
				{
					ArrayList<String> line = tokenizeString(fileScanner.nextLine(), null); //note, null was passed in with intention of setting delimiters as empty spaces
					sp191Sum.add(Integer.parseInt(line.get(1)));
					sp192Sum.add(Integer.parseInt(line.get(2)));
					sp193Sum.add(Integer.parseInt(line.get(3)));
				}
				
				//each sum needs to be added up for a final sum
				int total191 = 0, total192 = 0, total193 = 0;
				for (Integer integer : sp191Sum)
				{
					total191 += integer;
				}
				for (Integer integer : sp192Sum)
				{
					total192 += integer;
				}
				for (Integer integer : sp193Sum)
				{
					total193 += integer;
				}
				
				String line = String.format("%s    %d    %d    %d", dd, total191, total192, total193); 
				String monthFileName = String.format("monthSale_%s%s.txt", yyyy, mm);
				instance = new ProductSP19_Valeriano(total191, total192, total193);
				instance.displayReport(chosenDay); 
				 try { appendLineToFile(line, monthFileName); }
				 catch (IOException io) { println("there was an issue appending to " + monthFileName); }
				 repeatLoop = false; 
			}
			else
			{
				if (chosenDay.contentEquals("0")) { return; }
				println("Please specify the desired day in mm/dd/yyyy format");
				continue;
			}
		}
		while (repeatLoop);
	}
	
	//gets info from month file, adds it to year file
	//display month info
	private static void endingMonthReport()
	{
		Scanner fileScanner;
		boolean repeatLoop = true;
		do
		{
			println("Please input the month to run the report for in the following format: mm/yyyy");
			println("(Enter 0 to return to the menu)");
			String chosenMonth = kbd.nextLine();

			if (CheckIfStringIsInFormat(chosenMonth, "\\d{2}/\\d{4}"))
			{
				String mm, yyyy;
				ArrayList<String> splitDate = tokenizeString(chosenMonth, "/");
				mm = splitDate.get(0); yyyy = splitDate.get(1); 
				String fileName = "monthSale_" + yyyy + mm + ".txt";
				try { fileScanner = getScannerForFile(fileName); }
				catch (FileNotFoundException f)
				{
					println("No file called " + fileName + " was found. Please make sure the requested date was inputted as all numbers in the following format: mm/yyyy and try again"); continue;
				}
				catch (IOException io) { println("IO exception occured"); continue; }
				
				
				ArrayList<Integer> sp191Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp192Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp193Sum = new ArrayList<Integer>();
				
				//for each line in the scanner, return a string
				while (fileScanner.hasNextLine())
				{
					ArrayList<String> line = tokenizeString(fileScanner.nextLine(), null); 
					sp191Sum.add(Integer.parseInt(line.get(1)));
					sp192Sum.add(Integer.parseInt(line.get(2)));
					sp193Sum.add(Integer.parseInt(line.get(3)));
				}
				
				//each sum needs to be added up for a final sum
				int total191 = 0, total192 = 0, total193 = 0;
				for (Integer integer : sp191Sum)
				{
					total191 += integer;
				}
				for (Integer integer : sp192Sum)
				{
					total192 += integer;
				}
				for (Integer integer : sp193Sum)
				{
					total193 += integer;
				}
				
				String line = String.format("%s    %d    %d    %d", mm, total191, total192, total193); 
				String yearFileName = String.format("yearSale_%s.txt", yyyy);
				instance = new ProductSP19_Valeriano(total191, total192, total193);
				instance.displayReport(chosenMonth); 
				 try { appendLineToFile(line, yearFileName); }
				 catch (IOException io) { println("there was an issue appending to " + yearFileName); }
				 repeatLoop = false; 
			}
			else
			{
				if (chosenMonth.contentEquals("0")) { return; }
				println("Please specify the desired day in mm/yyyy format");
				continue;
			}
		}
		while (repeatLoop);
	}
	
	//display year info
	private static void endingYearReport()
	{
		Scanner fileScanner;
		boolean repeatLoop = true;
		do
		{
			println("Please input the year to run the report for in the following format: yyyy");
			println("(Enter 0 to return to the menu)");
			String chosenYear = kbd.nextLine();

			if (CheckIfStringIsInFormat(chosenYear, "\\d{4}"))
			{
				String fileName = "yearSale_" + chosenYear + ".txt";
				try { fileScanner = getScannerForFile(fileName); }
				catch (FileNotFoundException f)
				{
					println("No file called " + fileName + " was found. Please make sure the requested date was inputted as all numbers in the following format: yyyy and try again"); continue;
				}
				catch (IOException io) { println("IO exception occured"); continue; }
				
				
				ArrayList<Integer> sp191Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp192Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp193Sum = new ArrayList<Integer>();
				
				//for each line in the scanner, return a string
				while (fileScanner.hasNextLine())
				{
					ArrayList<String> line = tokenizeString(fileScanner.nextLine(), null); 
					sp191Sum.add(Integer.parseInt(line.get(1)));
					sp192Sum.add(Integer.parseInt(line.get(2)));
					sp193Sum.add(Integer.parseInt(line.get(3)));
				}
				
				//each sum needs to be added up for a final sum
				int total191 = 0, total192 = 0, total193 = 0;
				for (Integer integer : sp191Sum)
				{
					total191 += integer;
				}
				for (Integer integer : sp192Sum)
				{
					total192 += integer;
				}
				for (Integer integer : sp193Sum)
				{
					total193 += integer;
				}
				
				String line = String.format("%s    %d    %d    %d", chosenYear, total191, total192, total193); 
				instance = new ProductSP19_Valeriano(total191, total192, total193);
				instance.displayReport(chosenYear); 
				 repeatLoop = false; 
			}
			else
			{
				if (chosenYear.contentEquals("0")) { return; }
				println("Please specify the desired day in yyyy format");
				continue;
			}
		}
		while (repeatLoop);
	}

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
		catch(IOException e) { println("there was an error creating a FileWriter for " + file.getName()); }
		
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
		{ println("Desktop class not supported"); }
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
		if (fileDirectory != null) { println("\n" + "The directory for the file is: " + fileDirectory + "\n"); }
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
