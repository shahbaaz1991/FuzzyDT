
public class DataStore {
	
	private int row; //Holds the amount of rows in the dataset
	private int column;// Holds the amount of columns in the dataset
	private String [][] dataSet; //Holds the dataset in a 2D array of type String
	
	public DataStore(String path) // Constructor which populates the dataStore using the RuleReader class.
	{
		dataSet= new RuleReader(path).csvRead(); //uses the rule reader class to obtain the data
		column=dataSet[0].length;
		row=dataSet.length;
	}
	public String[][] getData() //returns 2D array of the data
	{
		return this.dataSet;
	}
	//setData(String[][] newData) //Replaces the current data with a new dataset
	public int getColumn() //Returns number of columns
	{
		return this.column;
	}
	public int getRow() //Returns number of rows
	{
		return this.row;
	}
	public void display() // returns data in a readable format
	{
		//displays data to the console
		for (int i=column-1; i>=0; i--)
		{
			System.out.print(" "+ dataSet[0][i]);
		}
		System.out.println("");
		for (int j=row-1;j>0;j--)
		{
			for(int x = column-1;x>=0;x--)
			{
				System.out.print(" "+ dataSet[j][x]);
			}
			System.out.println("");
		}
	}

}
