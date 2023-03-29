package game;

public class Item {
	final protected String nameItem;
	final protected int goldvalue;
	final protected int chance;
	
	public Item(String nameItem, int chance, int goldvalue)
	{
		this.nameItem = nameItem.replaceAll(" ","_");
		this.goldvalue = goldvalue;
		if(!(chance>=0 && chance<=100))
		{chance = 100;}
		this.chance = chance;
	}
	public Item()
	{this("coin",50, 1);}
	public Item(String nameItem, int goldvalue)
	{this(nameItem, 100, goldvalue);}

	final public String toString()
	{return this.nameItem;}

	public String info()
	{return this+"(gold value:"+goldvalue+")";}

	public String save()
	{return this.nameItem+";"+this.goldvalue+";"+this.chance;}
	public int getchance()
	{return this.chance;}
	public int getgoldValue()
	{return this.goldvalue;}

	public boolean equals(Item toCompare)
	{return nameItem.equals(toCompare.nameItem)&&this.goldvalue==toCompare.goldvalue;}
}