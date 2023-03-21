package game;

public class Item {
	final protected String nameItem;
	final protected int goldvalue;
	final protected int chance;
	
	public Item(String nameItem, int chance, int goldvalue)
	{
		this.nameItem = nameItem;
		this.goldvalue = goldvalue;
		if(!(chance>=0 && chance<=100))
		{chance = 100;}
		this.chance = chance;
	}
	public Item()
	{this("coin",50, 1);}

	final public String toString()
	{return this.nameItem;}

	public String info()
	{return this+"(gold value:"+goldvalue+")";}

	public int getchance()
	{return this.chance;}
	public int getgoldValue()
	{return this.goldvalue;}
}