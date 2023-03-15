package game;

public class Item
{
	protected String nameItem;
	protected String description;
	protected int weight;
	
	public Item(String nameItem, int weight, String description)
	{
		this.nameItem = nameItem;
		this.description = description;
		this.weight = weight;
	}
	public Item()
	{this("testitem",1, "test item");}

	final public String toString()
	{return this.nameItem;}

	public String info()
	{return this+"(weight:"+this.weight+". "+description+")";}
}
