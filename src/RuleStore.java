import java.util.ArrayList;

// A class that stores the rule objects 
public class RuleStore {

	
	private ArrayList<Rule> rules; // stores all rule objects in arrayList
		//read: RuleReader // uses the Rule Reader class to return rules
	private String classificationA; //classification value 
	private String classificationB;  //classification value

	public RuleStore(String path) //Populates rule base by reading the rules
	{
		classificationA = "";
		classificationB = "";
		rules = new ArrayList<Rule>(new RuleReader(path).ruleRead()); // rule reader returns a arraylist of type rule
	}
	
	public void setClassifications()
	{
		for(int i = 0; i<rules.size(); i++)
		{
			if(classificationA.equals(""))
			{
				this.classificationA = rules.get(i).getClassification();
			}
			else
			{
				if(!classificationA.equalsIgnoreCase(rules.get(i).getClassification()))
						{
							classificationB = rules.get(i).getClassification();
						}
			}
		}
	}
		public ArrayList<Rule>getRules() //returns the array list of rules
		{
			return rules;
		}
		
		public String toString() //returns all the rules in the ruleStore as string
		{
			String s = "";
			return s;
		}
		
		public String getClassB()
		{
			return this.classificationB;
		}
		
		public String getClassA()
		{
			return this.classificationA;
		}

}
