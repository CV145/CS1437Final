import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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
		
		
		fileName = "DaySale_" + U.formatDate(currentDate, "yyyyMMdd") + ".txt";
		String lineToAppend = String.format("%s   %d   %d   %d", transactionString, sp191, sp192, sp193);
		U.appendLineToFile(lineToAppend, fileName);
		
		transactionNum++;
	}

	
	//gets info from day file, adds it to month file
	//display day info
	private static void endingDayReport()
	{
		U.println("Please input the day to run the report for in the following format: mm/dd/yyyy");
		
		
		String chosenDay = kbd.nextLine();
		//String is checked using a regular expression
		if (U.CheckIfStringIsInFormat(chosenDay, "\\d{2}/\\d{2}/\\d{4}"))
		{
			U.println("it works!");
		}
		else
		{
			U.println("Please specify the desired day in mm/dd/yyyy format");
		}
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
