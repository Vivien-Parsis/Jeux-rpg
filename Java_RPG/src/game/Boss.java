package game;

public class Boss extends Person {
	private final boolean isFinalBoss;
	public Boss(final String name,final int HP,final int damagePoint,final int defensePoint, boolean isFinalBoss)
	{
		super(name, HP, damagePoint, defensePoint);
		this.isFinalBoss = isFinalBoss;
	}
	public Boss(final String name,final int HP,final int damagePoint,final int defensePoint)
	{this(name,HP,damagePoint,defensePoint,false);}
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
	public String save()
	{
		return "boss;"+super.save()+";"+isFinalBoss;
	}
	public String toString()
	{return this.name + "(HP:"+this.currentHP+"/"+this.maxHP+")";}
	/**
 	* hurt the boss
 	*/
	public void hurtBoss(final int damage)
    {this.currentHP-=damage;}

	public boolean getisFinalBoss()
	{return this.isFinalBoss;}
}