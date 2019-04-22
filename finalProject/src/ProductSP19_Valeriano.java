import java.io.*;
import java.util.*;

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
	
	//Output a line to a file for a day
	private void outputToDayFile(File dayFile)
	{
		//get last two numbers from dayFile.getName() for transaction #?
	}
	
	//Output a line to a file for a month
	private void outputToMonthFile(File monthFile)
	{
		
	}
	
	//Output a line to a file for a year
	private void outputToYearFile(File yearFile)
	{
		
	}
	
	
	//Methods for displaying reports quickly
	private void displayDayReport()
	{
		
	}
	private void displayMonthReport()
	{
		
	}
	private void displayYearReport()
	{
		
	}
	
	//Method prints out a receipt
	private void printReceipt()
	{
		
	}
}
