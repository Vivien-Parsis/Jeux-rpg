package game;

public class Weapon extends Item
{
	private int attackpoint;
	
	public Weapon(String nameItem, int weight, int attackpoint)
	{
		this.nameItem = nameItem;
		this.weight = weight;
		this.attackpoint = attackpoint;
	}
	public Weapon()
	{this("testweapon",1,1);}
	
	/**
 	*	return info of the weapon 
 	*/
	public String info()
	{return this+"(damage point:"+this.attackpoint+")";}

	public int getattackpoint()
	{return this.attackpoint;}
}
