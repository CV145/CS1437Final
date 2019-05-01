import java.io.*;
import java.util.*;
import java.awt.Desktop;

//data type class
public class ProductSP19_Valeriano {
	int _191UnitsSold;
	int _192UnitsSold;
	int _193UnitsSold;
	final float _191Cost = 12.99f;
	final float _192Cost = 14.99f;
	final float _193Cost = 15.99f;
	
	//constructor initializes # of units sold
	public ProductSP19_Valeriano(int _191Sales, int _192Sales, int _193Sales)
	{
		_191UnitsSold = _191Sales;
		_192UnitsSold = _192Sales;
		_193UnitsSold = _193Sales;
	}
	
	//Methods for calculating earnings
	private float Calculate191Earnings()
	{
		return _191Cost * _191UnitsSold;
	}
	private float Calculate192Earnings()
	{
		return _192Cost * _192UnitsSold;
	}
	private float Calculate193Earnings()
	{
		return _193Cost * _193UnitsSold;
	}
	//Find the subtotal sale earnings before tax
	private float CalculateSubTotal()
	{
		return Calculate191Earnings() + Calculate192Earnings() + Calculate193Earnings();
	}
	//Find the tax
	private float CalculateTax()
	{
		return CalculateSubTotal() * .0825f;
	}
	//Find the total sale earnings after tax
	private float CalculateTotal()
	{
		return CalculateSubTotal() + CalculateTax();
	}
	//Amount paid minus the total 
	private float CalculateBalance(float payment)
	{
		return payment - CalculateTotal();
	}
	
	//method writes a line to a file
	public void AppendLineToFile(String lineToAppend, String fileName)
	{
		File file = new File(fileName); //create an instance of File class
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		if (!file.exists())
		{
			try { file.createNewFile(); }
			catch (IOException e) { println("there was an error creating the file: " + fileName + " \n " + "maybe it already exists?"); }
		}
		
		try { fileWriter = new FileWriter(file, true); } //append to file
		catch(IOException e) { println("there was an error creating a FileWriter for " + file.getName()); }
		
		//PrintWriter(FileWriter) works because FileWriter inherits OutputStreamWriter
		//PrintWriter needs an OutputStream class to write to
		printWriter = new PrintWriter(fileWriter);
		printWriter.println(lineToAppend);
		
		openFileOnDesktop(file);

		outputFileDirectory(file);
		
		printWriter.close();
	}

	//method tries to automatically open a file
	private void openFileOnDesktop(File file) {
		Desktop desktop;
		if (!Desktop.isDesktopSupported())
		{ println("Desktop class not supported"); }
		else  
		{ 
			desktop = Desktop.getDesktop();
			  if (file.exists()) { try {desktop.open(file);} catch(IOException e) { println("unable to open file using Desktop class"); }
			}
		}
	}

	//look for and print the directory of where a file is located
	private void outputFileDirectory(File file) {
		String fileDirectory;
		fileDirectory = file.getAbsolutePath();
		if (fileDirectory != null) { println("The directory for the file is: " + fileDirectory); }
	}
	
	/*//Output a line to a file for a day
	public void outputToDayFile(String fileName, String transactionNum)
	{
		String lineOutput;
		FileWriter dayFile; //does this create a new file?
		PrintWriter printWriter = null;
		
		try {
		dayFile = new FileWriter(fileName, true);
		printWriter = new PrintWriter(dayFile);
		}
		catch (IOException io)
		{
			println("IO exception when initializing print and file writers");
		}
		
		lineOutput = String.format("%-10s %-7d %-7d %-7d", transactionNum, _191UnitsSold, _192UnitsSold, _193UnitsSold);
		printWriter.println(lineOutput);
		
	}
	
	//Output a line to a file for a month
	public void outputToMonthFile(String fileName, String day)
	{
		String lineOutput;
		FileWriter monthFile; //does this create a new file?
		PrintWriter printWriter = null;
		
		try {
		monthFile = new FileWriter(fileName, true);
		printWriter = new PrintWriter(monthFile);
		}
		catch (IOException io)
		{
			println("IO exception when initializing print and file writers");
		}
		
		lineOutput = String.format("%-10s %-7d %-7d %-7d", day, _191UnitsSold, _192UnitsSold, _193UnitsSold);
		printWriter.println(lineOutput);
	}
	
	//Output a line to a file for a year
	public void outputToYearFile(String fileName, String month)
	{
		String lineOutput;
		FileWriter yearFile; //does this create a new file?
		PrintWriter printWriter = null;
		
		try {
		yearFile = new FileWriter(fileName, true);
		printWriter = new PrintWriter(yearFile);
		}
		catch (IOException io)
		{
			println("IO exception when initializing print and file writers");
		}
		
		lineOutput = String.format("%-10s %-7d %-7d %-7d", month, _191UnitsSold, _192UnitsSold, _193UnitsSold);
		printWriter.println(lineOutput);
	}*/
	
	
	//Method for displaying report
	public void displayReport(String date)
	{
		System.out.printf("%6s%s\n", "SALE SP19 PRODUCTS-", date);
		
		System.out.printf("%s   %d   %f\n", "Model SP191 (12.99/per unit)", _191UnitsSold, Calculate191Earnings());
		
		System.out.printf("%s   %d   %f\n", "Model SP192 (14.99/per unit)", _192UnitsSold, Calculate192Earnings());
		
		System.out.printf("%s   %d   %f\n\n", "Model SP193 (15.99/per unit)", _193UnitsSold, Calculate193Earnings());
		
		println(".............................................");
		println("Sub total:");
		System.out.printf("%45f\n", CalculateSubTotal());
		println("Tax(8.25%)");
		System.out.printf("%45f", CalculateTax());
		println("Total:");
		System.out.printf("%45f", CalculateTotal());
	}

	
	//Method prints out a receipt
	public void printReceipt(String currentDate, String transactionNum, float amountPaid)
	{
		println(".............................................");
		System.out.printf("%7s\n", "RECEIPT - SALE SP19 PRODUCT");
		println(".............................................");
		println("Date:");
		System.out.printf("%45s\n", currentDate);
		println("Sale transaction:");
		System.out.printf("%45s\n", transactionNum);
		println(".............................................");
		System.out.printf("%s %5s %10s\n", "Model SP191 (12.99/per unit)", _191UnitsSold, Calculate191Earnings());
		System.out.printf("%s %5s %10s\n", "Model SP192 (14.99/per unit)", _192UnitsSold, Calculate192Earnings());
		System.out.printf("%s %5s %10s\n", "Model SP193 (15.99/per unit)", _193UnitsSold, Calculate193Earnings());
		println(".............................................");
		println("Sub total:");
		System.out.printf("%45f\n", CalculateSubTotal());
		println("Tax(8.25%)");
		System.out.printf("%45f\n", CalculateTax());
		println("Total:");
		System.out.printf("%45f\n", CalculateTotal());
		println("Amount paid:");
		System.out.printf("%45f\n", amountPaid);
		println("Balance:");
		System.out.printf("%45f\n", CalculateBalance(amountPaid));
	}
	
	//print method
	private void println(String output)
	{
		System.out.println(output);
	}
}
