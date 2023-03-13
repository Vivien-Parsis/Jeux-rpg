package jeux_RPG;

public class Boss extends Person
{
	public Boss(String name, int HP, int damagePoint, int defensePoint)
	{
		this.name = name;
		this.HP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
	}
	public Boss()
	{this("bosstest",50,5,5);}
	
	/**
 	*	Return info of the boss	
 	*/
	public String info()
	{
		return 
			this+"(HP:"+this.HP+
			", damage point:"+this.damagePoint+
			", defense point:"+this.defensePoint+")";
	}

	public String toString()
	{
		return this.name;
	}

}
