package game;

public class Boss extends Person implements Info{
	public Boss(String name, int HP, int damagePoint, int defensePoint)
	{
		this.name = name;
		this.maxHP = HP;
		this.currentHP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
	}
	public Boss()
	{this("goblin",50,12,5);}
	
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
	public void hurtBoss(int damage)
    {this.currentHP-=damage;}
}
