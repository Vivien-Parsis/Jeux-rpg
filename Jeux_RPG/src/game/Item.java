package game;

public class Item {
	final protected String nameItem;
	final protected String description;
	final protected int weight;
	final protected int chance;
	
	public Item(String nameItem, int weight, String description, int chance)
	{
		this.nameItem = nameItem;
		this.description = description;
		this.weight = weight;
		if(!(chance>=0 && chance<=100))
		{chance = 100;}
		this.chance = chance;
	}
	public Item()
	{this("coin",1, "test item",50);}

	final public String toString()
	{return this.nameItem;}

	public String info()
	{return this+"(weight:"+this.weight+". "+description+")";}

	public int getchance()
	{return this.chance;}
}