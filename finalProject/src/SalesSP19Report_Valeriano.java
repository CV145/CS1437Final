import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

import carlos_package.U;

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
	private static int transactionNum; //TODO save and load in a binary file
	
	
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
			input = U.inputStringAndConvertToInt();
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
	}

	//refreshes and initializes the date
	private static void refreshDate() {
		currentDate = new Date(); //instantiate Date object with no args.. sets to current date at the very moment
		fullDateString = fullDateFormat.format(currentDate); //DateFormat.format(date) returns a string representing the date
		calendar.setTime(currentDate); //calendar needs a date object to set time
		currentDay = Integer.parseInt(dayFormat.format(currentDate));
	}

	private static void displayMenu() {
		U.println("");
		print("SALE SPRING 2019 PRODUCTS--Carlos Valeriano");
		print("Today: " + currentDate);
		print("1. Sale Product");
		print("2. Ending day sale report");
		print("3. Ending month sale report");
		print("4. Ending year sale report");
		print("0. Exit");
		print("Please enter your choice below:");
		print("");
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

		print("-- Sale Product Transaction --");
		print("How many SP191 units were bought?");
		sp191 = U.inputStringAndConvertToInt();
		print("How many SP192 units were bought?");
		sp192 = U.inputStringAndConvertToInt();
		print("How many SP193 units were bought?");
		sp193 = U.inputStringAndConvertToInt();
		print("How much did the customer pay?");
		amountPaid = U.inputStringAndConvertToFloat();
		
		
		instance = new ProductSP19_Valeriano(sp191, sp192, sp193);
		String transactionString = String.format("%s%04d", dayString, transactionNum);
		instance.printReceipt(fullDateString, transactionString, amountPaid);
		
		
		fileName = "daySale_" + U.formatDate(currentDate, "yyyyMMdd") + ".txt";
		String lineToAppend = String.format("%s   %d   %d   %d", transactionString, sp191, sp192, sp193);
		try { U.appendLineToFile(lineToAppend, fileName); }
		catch (IOException io) { U.println("the file: " + fileName + " threw an IOException"); }
		
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
			U.println("Please input the day to run the report for in the following format: mm/dd/yyyy");
			String chosenDay = kbd.nextLine();
			//String is checked using a regular expression
			if (U.CheckIfStringIsInFormat(chosenDay, "\\d{2}/\\d{2}/\\d{4}"))
			{
				String mm, dd, yyyy;
				ArrayList<String> splitDate = U.tokenizeString(chosenDay, "/");
				mm = splitDate.get(0); dd = splitDate.get(1); yyyy = splitDate.get(2); 
				String fileName = "daySale_" + yyyy + mm + dd + ".txt";
				try { fileScanner = U.getScannerForFile(fileName); }
				catch (FileNotFoundException f)
				{
					U.println("No file called " + fileName + " was found. Please make sure the requested date was inputted as all numbers in the following format: mm/dd/yyyy and try again"); continue;
				}
				catch (IOException io) { U.println("IO exception occured"); continue; }
				
				
				ArrayList<Integer> sp191Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp192Sum = new ArrayList<Integer>();
				ArrayList<Integer> sp193Sum = new ArrayList<Integer>();
				
				//for each line in the scanner, return a string
				while (fileScanner.hasNextLine())
				{
					ArrayList<String> line = U.tokenizeString(fileScanner.nextLine(), null); //note, null was passed in with intention of setting delimiters as empty spaces
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
				 try { U.appendLineToFile(line, monthFileName); }
				 catch (IOException io) { U.println("there was an issue appending to " + monthFileName); }
				 repeatLoop = false;
			}
			else
			{
				U.println("Please specify the desired day in mm/dd/yyyy format");
				continue;
			}
		}
		while (repeatLoop = true);
	}
	
	//gets info from month file, adds it to year file
	//display month info
	private static void endingMonthReport()
	{
		
	}
	
	//display year info
	private static void endingYearReport()
	{
		
	}
	
	private static void tokenizeLine()
	{
		
	}
	

	private static void print(String output)
	{
		System.out.println(output);
	}
}
