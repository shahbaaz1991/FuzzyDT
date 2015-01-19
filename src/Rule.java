import java.util.ArrayList;


public class Rule {

	ArrayList<String> attribute;
	ArrayList<String> operator;
	ArrayList<String> value;
	String classification;
	Double probability;
	
	Rule()
	{
		this.attribute= new ArrayList<String>();
		this.operator= new ArrayList<String>();
		this.value = new ArrayList<String>();
		// this.classification = clss; 
		
	}
	
	public void addAttribute(String Att)
	// adds the attributes to the Attribute Array List 
	{
		this.attribute.add(Att);
		
	}
	
	public void addOperator(String Operator) // adds operators to the Operators Array List
	{
		this.operator.add(Operator);
	}
	
	public void addValue(String Val) // Adds values that the split occurred on to the Array List
	{
		this.value.add(Val);
	}
	
	public void setClass(String Class) // Sets the classification of the rule
	{
		this.classification=Class;
	}
	
	public void setProbability(double prob) 
	{
		this.probability=prob;
		
	}

	public int getNumberOfAttributes() // returns size of Attribute , which can also be searched
	{
		return attribute.size();
	}
	public ArrayList<String> getAttributes() // Returns Attribute ArrayList
	{
		return attribute;
	}
	public ArrayList<String> getOperators() // Returns Operator ArrayList
	{
		return operator;
	}
	public ArrayList<String> getValues() //Returns Values ArrayList
	{
		return operator;
	}
	public String getClassification() //Returns classification of rule object
	{
		return classification;
	}
	public String toString() // Returns rule as a string
	{
		String s= "Rule: ";
		return s;
	}
	
	public double getProbability()
	{
		return this.probability;
	}



	
	
	
}
