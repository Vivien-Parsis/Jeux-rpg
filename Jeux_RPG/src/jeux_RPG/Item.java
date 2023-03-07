package jeux_RPG;

public class Item {
	String nameItem;
	int weight;
	
	public Item(String nameItem, int weight)
	{
		this.nameItem = nameItem;
		this.weight = weight;
	}
	public Item()
	{this("testitem",0);}
}
