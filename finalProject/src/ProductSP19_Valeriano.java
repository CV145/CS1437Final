import carlos_package.U;

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
	
	//Method for displaying report
	public void displayReport(String date)
	{
		System.out.printf("\n%6s%s\n", "SALE SP19 PRODUCTS-", date);
		
		System.out.printf("%s   %d   %.2f\n", "Model SP191 (12.99/per unit)", _191UnitsSold, Calculate191Earnings());
		
		System.out.printf("%s   %d   %.2f\n", "Model SP192 (14.99/per unit)", _192UnitsSold, Calculate192Earnings());
		
		System.out.printf("%s   %d   %.2f\n\n", "Model SP193 (15.99/per unit)", _193UnitsSold, Calculate193Earnings());
		
		U.println(".............................................");
		U.println("Sub total:");
		System.out.printf("%45.2f\n", CalculateSubTotal());
		U.println("Tax(8.25%)");
		System.out.printf("%45.2f\n", CalculateTax());
		U.println("Total:");
		System.out.printf("%45.2f\n", CalculateTotal());
	}

	
	//Method prints out a receipt
	public void printReceipt(String currentDate, String transactionNum, float amountPaid)
	{
		U.println(".............................................");
		System.out.printf("%7s\n", "RECEIPT - SALE SP19 PRODUCT");
		U.println(".............................................");
		U.println("Date:");
		System.out.printf("%45s\n", currentDate);
		U.println("Sale transaction:");
		System.out.printf("%45s\n", transactionNum);
		U.println(".............................................");
		System.out.printf("%s %5s %10s\n", "Model SP191 (12.99/per unit)", _191UnitsSold, Calculate191Earnings());
		System.out.printf("%s %5s %10s\n", "Model SP192 (14.99/per unit)", _192UnitsSold, Calculate192Earnings());
		System.out.printf("%s %5s %10s\n", "Model SP193 (15.99/per unit)", _193UnitsSold, Calculate193Earnings());
		U.println(".............................................");
		U.println("Sub total:");
		System.out.printf("%45.2f\n", CalculateSubTotal());
		U.println("Tax(8.25%)");
		System.out.printf("%45.2f\n", CalculateTax());
		U.println("Total:");
		System.out.printf("%45.2f\n", CalculateTotal());
		U.println("Amount paid:");
		System.out.printf("%45.2f\n", amountPaid);
		U.println("Balance:");
		System.out.printf("%45.2f\n", CalculateBalance(amountPaid));
	}
	
	
}
