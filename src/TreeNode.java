import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 
 */

/**
 * @author Shahbaaz
 *
 */
public class TreeNode {
	private LinkedHashMap<String,ArrayList<String>> treeNodes; // Will use the decision threshold and name as these are unique values to the node.
	
	public TreeNode(DataStore store, RuleStore rules) 
	{
	// Will initialize the treeNode object and populate the Linked Hash Map
		this.treeNodes= new LinkedHashMap<String,ArrayList<String>>(setNodes(store,rules));
	}
	
	// Locates all the nodes that require a membership function and adds them to the LinkedHashMap object
	private LinkedHashMap<String,ArrayList<String>>setNodes(DataStore store, RuleStore rules)
	{
		LinkedHashMap<String,ArrayList<String>> attributes = new LinkedHashMap<String,ArrayList<String>>();
		for(int i=0;i<store.getColumn();i++)//loops through data store
		{
			for(int j=0;j<rules.getRules().size();j++)//loops through the RuleStore
			{
				for(int k=0;k<rules.getRules().get(j).getAttributes().size();k++)//identify how many components make the rule
				{
					//check if attribute is valid 
					if((store.getData()[0][i].equals(rules.getRules().get(i).getAttributes().get(k))) && (!rules.getRules().get(j).getOperators().get(k).equalsIgnoreCase("!=")) && (!rules.getRules().get(j).getOperators().get(k).equalsIgnoreCase("=")));
					
					//check if there is already a key/value for the attribute
					if(!attributes.containsKey(rules.getRules().get(j).getAttributes().get(k)))
					{
						try
						{
							attributes.put((rules.getRules().get(j).getAttributes().get(k)), new ArrayList<String>());// new key/value created
							attributes.get(rules.getRules().get(j).getAttributes().get(k)).add(rules.getRules().get(j).getValues().get(k)); //adds the value
						}catch(Exception e)
						{
							
						}
					}
					else if(attributes.containsKey(rules.getRules().get(j).getAttributes().get(k)))//chekcing if attribute is already in use
					{
						//if the value for the attribute isnt in the arraylist then add new value to the arraylist
						if(!attributes.get(rules.getRules().get(j).getAttributes().get(k)).contains(rules.getRules().get(j).getValues().get(k)))
								{
									attributes.get(rules.getRules().get(j).getAttributes().get(k)).add(rules.getRules().get(j).getValues().get(k));
								}
					}
				}
			}
		}
		return attributes;
	}
	
	
	
	public LinkedHashMap<String,ArrayList<String>> getNodes() // Returns the LinkedHashMap of all the nodes that require a membership function
	{
		return new LinkedHashMap<String,ArrayList<String>>(this.treeNodes);
	}
}
