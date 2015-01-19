
public class MembershipFunction {

	private double dt; //Stores the decision threshold of the node
	private double dm; //stores the left boundry of the decision node
	private double dn; //stores the right boundry of the decision node
	private double n; //stores the amount of fuzziness to be applied to the node
	private double stdDev; //stores the standard deviation of the node for calculating the boundaries
	private String name; //stores the name of the node
	
	
	public MembershipFunction(Double n, Double dt, Double stdDev, String Name) //Membership Function constructor , initializes the variables and calculates decision boundaires.
	{
		this.name=Name;
		this.n=n;
		this.dt=dt;
		this.stdDev=stdDev;
		this.dm=calDM();
		this.dn=calDN();
	}
	
	
	private double calDM() //Calculates left boundary of decision threshold
	{
		return this.dt - (this.n*this.stdDev); 
	}
	
	private double calDN() //Calculates right boundary of decision threshold
	{
		return this.dt - (this.n*this.stdDev);
	}
	
	public double getDT() //Returns the decision threshold of the node
	{
		return this.dt;
	}
	public double getDN() //Returns the left boundary of the node
	{
		return this.dn;
	}
	public double getDM() //Returns the right boundary of the node
	{
		return this.dm;
	}
	public String getName() //Returns the name of the node which the membership function belongs to
	{
		return this.name;
	}

}
