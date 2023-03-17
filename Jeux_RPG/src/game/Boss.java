package game;

public class Boss extends Person {
	public Boss(final String name,final int HP,final int damagePoint,final int defensePoint)
	{
		super(name, HP, damagePoint, defensePoint);
	}
	public Boss()
	{super("goblin",50,12,5);}
	
	/**
 	*	Return info of the boss	
 	*/
	public String info()
	{
		return 
			this.name+"(HP:"+this.currentHP+"/"+this.maxHP+
			", damage point:"+this.damagePoint+
			", defense point:"+this.defensePoint+")";
	}

	public String toString()
	{return this.name + "(HP:"+this.currentHP+"/"+this.maxHP+")";}
	/**
 	* hurt the boss
 	*/
	public void hurtBoss(final int damage)
    {this.currentHP-=damage;}
}