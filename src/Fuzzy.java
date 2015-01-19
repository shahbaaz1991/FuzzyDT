import java.util.ArrayList;



/*Inference,Defuzzification are also part of this class, as by doing this it enabled the FS to be processed a record at a time
using the same loop which created the FS, then the defuzzification of the rule is parsed concurrently without there being a 
need to feed it in another data structure and creating more loops, also it made sense to include the output in this class
as the defuzzification is keeping track of incorrect and correctly classified records
*/
public class Fuzzy {

	RuleStore rule; //Stores the rules
	DataStore store ;//Stores the DataStore
	ArrayList<MembershipFunction> mFStore; //Will provide access to the membership functions
	Double[] fuzzySet; //Array of type Double to store each fuzzy set per a rule
	ArrayList<Double[]> ruleGrades; //Stores fuzzy sets for every rule
	ArrayList<ArrayList<Double>> allGrades; //A ArrayList that stores all the rule grades of all fuzzy sets for every test case.
	
	ArrayList<ArrayList<Double>> fuzzyGrades; //Stores all fuzzy sets of the rule grades for each test case ready for inference. 
	ArrayList<Double> min; //store a minimum number for each rule
	Double max; //stores the maximum of all minimums/products for each test case
	Double product; //stores the products value of each rule
	int ruleCount; //Keeps count of the current rule
	int ruleNo; //the rule number that is the current rule 
	
	int correct; //Stores the total number of correctly classified records
	int incorrect;//Stores the total number of incorrectly classified records
	int classA;//Keeps track of classification A that are correct
	int classB;//Keeps track of classification B that are correct
	int classAIncorrect;//Keeps track of classification A that are misclassified
	int classBIncorrect;//Keeps track of classification B that are misclassified 
	String classification;//Stores the classification being compare to

    double minimum;
	boolean flag;
	boolean maxmin;
	boolean maxproduct;
	private boolean weight;
	
	double correctPerc;
	double incorrectPerc;
	double n =0.0;

	//Fuzzification(DataStore store, RuleStore rule, MembershipStore mfStore, Double n) // Constructor to create all the fuzzy sets for each rule and for each test case.
	
	
	public void createFuzzySets() //Creates fuzzy values for all applicable nodes when fuzziness is applied. Stores each rule in a array which then gets stored in a arraylist, then the next rule is fired and also stored. After all rules have fired they are added to the ArrayList which will be accessible. 
	{
	for(int i=0; i<store.getRow(); i++)
		//main loop includes the fuzzification,inference,defuz methods. Loops through each record of data store
		//in each increment of the loop, the variables are reset to default values to maintain integrity
	{
		minimum=2.0;
		product =0.0;
		min.clear();
		ruleCount=0;
		ruleNo=0;
		ruleGrades.clear();
		product=1.0;
		
		for(int x=0;x<rule.getRules().size();x++)//loop through each rule
		{
			//each record has FS generated for each rule, each time loop goes around, a new array is specified to match
			//the size of the rule components, this is to store the fuzzy set for each rule
			fuzzySet = new Double[rule.getRules().get(x).getAttributes().size()];
			int pos=0;
			for(int y=0;y<rule.getRules().get(x).getAttributes().size();y++) //loop through the conditions
			{
				for(int z=0;z<store.getColumn();z++)//check data attribute with rule attribute
					//two more loops , to go through components of each rule and through the row of attribute names in datastore
					// this will determine if the rule attribute and column name match
				{
					if(rule.getRules().get(x).getAttributes().get(y).equalsIgnoreCase(store.getData()[0][z]))
					{
						 flag = false;
						 for(MembershipFunction grade: mFStore)//check if attribute is continous and if it is then cal MG
						 {
							 //loop is initiated is att and col are equal, then MF is checked against rule att if equal then continous value
							 if((rule.getRules().get(x).getAttributes().get(y).equalsIgnoreCase(grade.getName()) && (Double.parseDouble(rule.getRules().get(x).getValues().get(y)) == grade.getDT())))
							 {
								 if(n==0.0)
								 {
									 /*if n is 0 then it is treated as a crisp set
									  * firstly att being tested against DT is parsed from the DATASTORE
									  * then a check is made to see if the value if > or <
									  * depending on the operator of the rule, the value is assigned a crisp MG
									  * if the rule op is > and the value is greater than the DT of the rule then MG=1
									  * if it doesnt meet criteria then MG=0 and assigned to FS
									  * if less than or equal then MG =1
									  */
									 double cond = Double.parseDouble(store.getData()[i][z]);
									 if(rule.getRules().get(x).getOperators().get(y).equalsIgnoreCase(">"))
									 {
										 if(cond>Double.parseDouble(rule.getRules().get(x).getValues().get(y)))
										 {
											 fuzzySet[pos]=1.00;
											 pos++;
											 break;
										 }
										 else
										 {
											 fuzzySet[pos]=0.00;
											 pos++;
											 break;
										 }
									 }else if(rule.getRules().get(x).getOperators().get(y).equalsIgnoreCase("<"))
									 {
										 if(cond<=Double.parseDouble(rule.getRules().get(x).getValues().get(y)))
										 {
											 fuzzySet[pos]=1.00;
											 pos++;
											 break;
										 }
										 else
										 {
											 fuzzySet[pos]=0.00;
											 pos++;
											 break;
										 }
									 }
								 }
								 // now for fuzzy values
								 else
								 {
									 flag=true;
									 //test the rule using IF statement
									 if(rule.getRules().get(x).getOperators().get(y).equalsIgnoreCase(">") && Double.parseDouble(rule.getRules().get(x).getValues().get(y))==grade.getDT())
									 {
										 /*
										  * fuzziness is applied here, works same way as the crisp above, rule is checked to see which op it is
										  * value is processed by fuzzification cals rather than crisp grade of membership
										  * additional check in the if , to determine if the MF used is correct, 
										  * condition of rule is checked against the DT stored in current MF thats being looped
										  * if rule condition and MF DT match then process and can be assigned MG
										  */
										 fuzzySet[pos]=calRight(grade.getDM(),grade.getDN(),Double.parseDouble(store.getData()[i][z]));
										 pos++;
										 break;
									 }
									 if(rule.getRules().get(x).getOperators().get(y).equalsIgnoreCase("<=") && Double.parseDouble(rule.getRules().get(x).getValues().get(y))==grade.getDT())
									 {
										 fuzzySet[pos]=calLeft(grade.getDM(),grade.getDN(),Double.parseDouble(store.getData()[i][z]));
										 pos++;
										 break;
										 /*
										  * MF from the loop retreives DM, DN , current row and col values to identify correct data
										  * after value inserted into fuzzyset , loop can be broken
										  */
									 }
								 }
							  }
						 }
						 if(!flag)
						 {
							 /*below module handles discrete values, they either = or != the rule
							  * operator array is used
							  * MG can be assigned depending if the record being tested is equal to the rule or not
							  */
							 if(rule.getRules().get(x).getValues().get(y).equalsIgnoreCase(store.getData()[i][z]))
								{
								 if(rule.getRules().get(x).getValues().get(y).equalsIgnoreCase("="))
								 {
									 fuzzySet[pos]=1.00;
									 pos++;
								 }
								 else if(rule.getRules().get(x).getValues().get(y).equalsIgnoreCase("!="))
								 {
									 fuzzySet[pos]=0.00;
									 pos++;
								 }
								}
							 else if(!rule.getRules().get(x).getValues().get(y).equalsIgnoreCase(store.getData()[i][z]))
							 {
								 if(rule.getRules().get(x).getValues().get(y).equalsIgnoreCase("!="))
								 {
									 fuzzySet[pos]=1.00;
									 pos++;
								 }
								 else if(rule.getRules().get(x).getValues().get(y).equalsIgnoreCase("!="))
								 {
									 fuzzySet[pos]=0.00;
									 pos++;
								 }
							 }
						 }
					}
				}
			}
			ruleGrades.add(fuzzySet);
			/*
			 * once a rule is processed, its added to the ruleGrades ArrayList and then the 2nd loop is incremented and the next
			 * rule can be processed and a Fuzzy set can be generated
			 * after all the rules are processed , the arraylist is used for inference and defuzzification
			 */
		}
		
		/*
		 * deals with fuzzy sets for the current record of data as its incorporated in this loop
		 * 
		 */
		
		if(maxmin)
		{
			for(int a=0;a<ruleGrades.size();a++)
			{
				minimum=2.0;
				//for all double values in the array
				for(Double grades : ruleGrades.get(a))
				{
					if(grades<minimum)
					{
						minimum=grades.doubleValue();
					}
				}
				min.add(minimum);
			}
		}
		
		/*
		 * calculates product of each fuzzy set and adds to min array
		 * will be apparent when looking at the newly created FS in the array
		 */
		
		if(maxproduct)
		{
			for(int a=0;a<ruleGrades.size();a++)
			{
				product=1.0;
				//for all double values in the array
				for(Double grades : ruleGrades.get(a))
				{
					if(grades<minimum)
					{
						product*=grades;
					}
				}
				min.add(product);
			}
		}
		/*
		 * now use the min array to find max value and defuzzication
		 * finding the max val is 2nd part to the above inference techinques
		 * the newly created FS from finding the prod or min is needed to find the max
		 * array pos of the max val can map to the rule num that the val represents, providing a fuzzy singleton
		 */
		//set rule pos to 0
		int rulePos=0;
		//select max prod or max min(depends on inference used)
		for(Double minGrades : min)
		{
			if(minGrades>max)
			{
				max=minGrades;
				ruleNo=ruleCount;
			}
			/*could be conflict here, what if array value == max values in array ?
			 * 1. choose rule with best probability (weighting)
			 * 2. choose current ruleNumber
			 * 3. keep first rule (if no weight)
			 */
		else if(minGrades == max)
		{
			if((minGrades*rule.getRules().get(rulePos).getProbability())>(max*rule.getRules().get(ruleNo).getProbability()) && this.weight == true)
			{
				max=minGrades;
				ruleNo=ruleCount;
			}//might be problems of increased accurancy
			else if(weight==true)
			{
				max=minGrades;
				ruleNo=ruleCount;
			}
		}
			rulePos++;
			ruleCount++;
		
	}
		/*
		 * 
		 * Defuzzifiation , determine correct classes
		 */
		classification=rule.getRules().get(ruleNo).getClassification();
		if(classification.equalsIgnoreCase(store.getData()[i][0]))
		{
			correct++;	
			if(store.getData()[i][0].equalsIgnoreCase(rule.getClassB()))
			{
				classB++;
			}
			else
			{
				classA++;
			}
		}
		else
		{
			incorrect+=1;
			if(store.getData()[i][0].equalsIgnoreCase(rule.getClassA()))
			{
				classAIncorrect++;
			}
			else
			{
				classBIncorrect++;
			}
			
		}
		
	}
	}
	
	public void output()
	{
		/*
		 * present data gathered from fuzzification,inference and defuzz
		 *
		 */
		double rows=store.getRow()-1;
		correctPerc=(correct/rows)*100;
		incorrectPerc=(incorrect/rows)*100;
	}
	
	public double getAcc() //returns correct acc of the tree only and can get acc of fuzzy objects for comparisions
	{
		double acc;
		double rows = store.getRow()-1;
		acc=(correct/rows)*100;
		
		return acc;
	}
	
	public String DisplayRes()
	{
		String s;
		StringBuilder sb=new StringBuilder();
		
		double rows=store.getRow()-1;
		correctPerc=(correct/rows)*100;
		incorrectPerc=(incorrect/rows)*100;
		
		sb.append("Correctly classified records: "+correct+ ", Incorrectly classified records: "+incorrect+ " Total records classified: "+(correct+incorrect));
		sb.append("\n");
		sb.append("Class "+"\""+rule.getClassA()+"\""+" Correct Classification: "+classA+" Inccorect Classification: "+ classAIncorrect	);
		sb.append("\n");
		sb.append("Class "+"\""+rule.getClassB()+"\""+" Correct Classification: "+classB+" Inccorect Classification: "+ classBIncorrect	);
		sb.append("\n");
		sb.append("Accuracy: "+correctPerc+" Incorrect Accuracy "+ incorrectPerc);
		s=sb.toString();
		return s;
	}
	
	private double  calRight(double dm, double dn, double value) //value to be fuzzified) //each case that fires down the right branch of the node has a membership function, this function calculates to what degree it is a member of the fuzzy set.
	{
		double mg= (value-dm) / (dn-dm);
		
		if(mg>1.0)
		{
			return 1.0;	
		}
		else if (mg<0.0)
		{
			return 0.0;
		}
		else
		{
			return mg;
		}
	}
	
	private double calLeft(double dm, double dn, double value) //each case that fires down the left branch of the node has a membership function, this function calculates what degree it is a member of the fuzzy set.
	{
		double mg= (value-dn) / (dm-dn);
		
		if(mg>1.0)
		{
			return 1.0;	
		}
		else if (mg<0.0)
		{
			return 0.0;
		}
		else
		{
			return mg;
		}
	}
	
	public void setFuzz(double fuzz) 
	{
		this.n=fuzz;
		
	}
	//getFuzzySets() //returns arraylist of all fuzzy set for each test instance
	
//	FuzzyInference(ArrayList<ArrayList<Double>>) //Constructor for fuzzy inference, uses ArrayList from the fuzzification class
	
	//MaxMin() //For performing MaxMin inference
	
	
	//MaxProduct() //for performing maxProduct inference 
	
	
	//GetRuleNo() //gets rule number from inference
	
	//Defuzzification(RuleStore r,Integer  ruleNo) //The constructor uses the RuleStore from FuzzyInference class to perform defuzzification and classify data
	//GetCorrect()//returns number of correctly classified records
	//GetIncorrect()//returns number of incorrectly classified records 
	//GetClassA()//returns number of correctly classified class A records
	//GetClassB()//returns number of correctly classified class B records
	//GetClassAIncorrect()//returns number of incorrectly classified class A records
	//GetClassBIncorrect()//returns number of incorrectly classified class B records



}
