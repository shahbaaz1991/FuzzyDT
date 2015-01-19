import java.util.ArrayList;
import java.util.Map;


public class MembershipStore {

	private TreeNode nodes;  //Identifies and stores all the nodes in the tree which need a membership function.
	private DataStore store;  //A Datastore object is used for the data
	private RuleStore rule; //A Rulebase object for the rules of the system
	private ArrayList<MembershipFunction> mfStore; //Stores all the membership functions that belong to the nodes which require them
	private Double n; //Amount of fuzziness to be applied to the nodes

	public MembershipStore(DataStore store, RuleStore rule, Double n) //Constructor which initializes Object, will populate the MemberShip Function Store.
	{
		this.n=n;
		this.store=store;
		this.rule=rule;
		nodes= new TreeNode(this.store,this.rule);
		mfStore= populateMembershipFunctionStore();
	}
	
	private ArrayList<MembershipFunction> populateMembershipFunctionStore() //Identifies nodes that need a membership function, performs statistical calculations on the nodes and creates new Membership Function objects. Adds the created objects to the ArrayList.
	{
		ArrayList<MembershipFunction> calStore = new ArrayList<MembershipFunction>();
		for(Map.Entry<String, ArrayList<String>> entry: nodes.getNodes().entrySet())
		{
			for(int i=0; i<store.getColumn();i++)
			{
				if(store.getData()[0][i].equals(entry.getKey()))
				{
					try{
						Calculation cc= new Calculation(store,i,entry.getKey());
						for(String value : entry.getValue())
						{
							calStore.add(new MembershipFunction(n,cc.getStdDev(),Double.parseDouble(value),entry.getKey()));
						}
					}catch(Exception e){System.err.println(e);
				}
			}
		}
			
	}
		return calStore;
	}
	
	public ArrayList<MembershipFunction> getMFStore() //Returns the arraylist containing the membership function objects
	{
		return this.mfStore;
	}
	//DisplayMF() // Returns the membership function objects in a String.

}
