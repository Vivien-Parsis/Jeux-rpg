package game;

public class Weapon extends Item{
	final private int attackpoint;
	
	public Weapon(final String nameItem,final String description,final int weight,final int attackpoint)
	{
		super(nameItem,weight,description,100);
		this.attackpoint = attackpoint;
	}
	public Weapon()
	{this("testweapon","",1,1);}
	/**
 	*	return info of the weapon 
 	*/
	public String info()
	{return this+"(damage point:"+this.attackpoint+")";}

	public int getattackpoint()
	{return this.attackpoint;}
}
