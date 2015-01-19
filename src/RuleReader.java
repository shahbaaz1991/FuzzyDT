import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// reads in the rule file
public class RuleReader {

	private FileInputStream fStream;  //opens a file input stream so a specific file can be selected
	private DataInputStream InStream;  // for the application to read data from the file stream
	private BufferedReader br; // Reads text from the data stream
	private ArrayList<Rule> ruleStore;  // storage for each rule that is read in
	private StringTokenizer tokens; //This allows the string to be broken up into tokens 
	private String strLine;  // Stores a line of text read from a file
	private String filePath;  // system path of the file
	private String[][] data;  // 2D array to temporary store the data read in from text file
	private int count; 

	RuleReader(String file) // Constructor to instantiate the object, and the other objects such as the streams, br etc
	{
		try
		{
			filePath=file;
			fStream= new FileInputStream(filePath);
			InStream= new DataInputStream(fStream);
			br = new BufferedReader(new InputStreamReader(InStream));
			ruleStore = new ArrayList<Rule>();
		}catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
		}
	}
	
	public ArrayList<Rule> ruleRead() // method to read if-then rules from text file. Rule object is created and added to ruleStore Array List.
	{
		//reads the rules.txt and returns the rules objects in an Array
		try
		{
			while((strLine = br.readLine()) !=null) // loops until there are no lines left in the text file
			{
				tokens= new StringTokenizer(strLine,","); // specifying the tokenizer to split on ',' array of tokens is created
				double probability=0;
				ArrayList<String> attribute = new ArrayList<String>();
				ArrayList<String> operator = new ArrayList<String>();	
				ArrayList<String> value = new ArrayList<String>();
				String ruleClass = "";
				ruleStore.add(new Rule()); //end of each rule, a new rule object is created
				
				while(tokens.hasMoreTokens())
				{
					for(int i=0; i<tokens.countTokens(); i++) //looping through array of strings created by tokenizer, then stored in thisToken
					{
						String thisToken= tokens.nextToken();
						
						switch(thisToken.charAt(0))
						{
						//identifies which tokens make up parts of different data in the text file and then adds to correct ArrayList
						case '$' : probability= Double.parseDouble(thisToken.substring(1));break;
						case '@' : attribute.add(thisToken.substring(1));break;
						case '%' : operator.add(thisToken.substring(1));break;
						case '#' : value.add(thisToken.substring(1));break;
						default:
							if(thisToken.charAt(thisToken.length()-1) == ';')
							{
								ruleClass = (thisToken.substring(0, thisToken.length()-1)); 
								//charAt used to identify the character at the head of the string and match character with a case
							}
							else
							{
								break;
							}
						}
					}
				}
				
				//now update rule object with data from each line
				
				for(int j=0; j<attribute.size(); j++)
				{
					ruleStore.get(count).addAttribute(attribute.get(j));
					ruleStore.get(count).addOperator(operator.get(j));
					ruleStore.get(count).addValue(value.get(j));
				}
				ruleStore.get(count).setProbability(probability);
				ruleStore.get(count).setClass(ruleClass);
				
				count++; //keeps track of current position in the RuleStore arrayList
				
				
			}
			
			InStream.close();
		}catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
		}
		
		return ruleStore; // returns the ArrayList of newly created rules
	}
	
	
	public String [][] csvRead()// reads data from a csv and stores in an array. Calculates how many rows, columns the csv file has before making the array.
	{
		int row = 0;
		int col=0;
		try
		{
			StringTokenizer tokens;
			String strLine;
			
			while((strLine = br.readLine())!=null) //calc number of rows and cols the dataset contains
			{
				tokens = new StringTokenizer(strLine,",");
				col = tokens.countTokens();
				row++;
			}
			
			data = new String [row] [col]; //2 dimensional array created to store test data
			
			fStream = new FileInputStream(filePath);
			InStream = new DataInputStream(fStream);
			br= new BufferedReader(new InputStreamReader(InStream));
			int j=0;
			
			while((strLine = br.readLine()) != null)
			{
				tokens = new StringTokenizer(strLine, ",");
				while(tokens.hasMoreTokens())
				{
					String strToken = tokens.nextToken();
					for(int i=0; i<=tokens.countTokens(); i++)
					{
						data[j][i] = strToken; //populates the array, going through the dataset one row at a time and storing each col
					}
				}
				j++;
			}
			
			return data;
			
		}catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
			return null;
		}
		
		
	}

}
