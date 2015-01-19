import java.util.ArrayList;


public class Calculation {

	private DataStore data;   //Uses the datastore object to perform calculations of rows
	private Double sum;  //stores the sum of numbers for a particular column
	private Double mean;  //stores the mean for a particular column
	private Double population;  //stores the statistical population for a particular column
	private Double stdDev;  //stores the standard deviation of a particular column
	private ArrayList<Double> values; //stores the values of every row for a particular column
	private int columnNo;  //stores the column to be processed
	private String name; //the attribute name of the column being processed

	public Calculation(DataStore store, int col, String nme) 
	//Constructor that initializes the class to perform statistics on a column in the dataset
	{
		store=this.data;
		col=this.columnNo;
		nme=this.name;
	}
	
	private ArrayList<Double> getValues() //Gets all data from the column, and stores them in a ArrayList<>.
	{
		ArrayList<Double> val = new ArrayList<Double>();
		for (int i=0;i<data.getRow();i++)
		{
			double d= Double.parseDouble(data.getData()[i][this.getColumn()]);
			val.add(d);
		}
		return val;
	}
	
	private double calSum() // Calculates the sum of the data stored in the values ArrayList
	{
		double sum=0;
		for(double d: this.values)
		{
			sum+=d;
		}
		return sum;
	}
	
	private double calMean() //Calculates the mean of the data stored in the values ArrayList
	{
		return (this.getSum()) /(this.values.size()-1);
	}
	
	private double calPop() //Returns the population of the ArrayList, calculates the variance between values.
	{
		double variance=0;
		double m =this.getMean();
		for (double d: this.values)
		{
			variance += (m-d)*(m-d);
		}
		
		return variance/(this.values.size()-1);
	}
	
	private double calStdDev() //Calculates the standard deviation of the arraylist
	{
		return Math.sqrt(getPop());
	}
	
	public double getSum() //returns the sum 
	{
		return this.sum;
	}
	public double getMean() //returns the mean
	{
		return this.mean;
	}
	public double getPop() //returns the population
	{
		return this.population;
	}
	public double getStdDev() //returns standard deviation
	{
		return this.stdDev;
	}
	public int getColumn()
	{
		return this.columnNo;
	}
	public String getName() //returns the name of the attribute that the calculations are from
	{
		return this.name;
	}

}
