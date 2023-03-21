package game;

public class Weapon extends Item {
	final private int attackpoint;
	
	public Weapon(final String nameItem,final int attackpoint, int goldvalue)
	{
		super(nameItem,100, goldvalue);
		this.attackpoint = attackpoint;
	}
	public Weapon()
	{this("testweapon",1,0);}
	/**
 	*	return info of the weapon 
 	*/
	public String info()
	{return this+"(damage point:"+this.attackpoint+")";}

	public int getattackpoint()
	{return this.attackpoint;}
}