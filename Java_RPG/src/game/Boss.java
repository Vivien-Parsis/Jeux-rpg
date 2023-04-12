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
			this.getname()+"(HP:"+this.getcurrentHP()+"/"+this.getmaxHP()+
			", damage point:"+this.getdamagePoint()+
			", defense point:"+this.getdefensePoint()+")";
	}
	public String save()
	{return "boss;"+this.getname()+";"+this.getmaxHP()+";"+this.getcurrentHP()+";"+this.getdamagePoint()+";"+this.getdefensePoint()+";"+isFinalBoss;}
	
	public String toString()
	{return this.getname() + "(HP:"+this.getcurrentHP()+"/"+this.getmaxHP()+")";}
	/**
 	* hurt the boss
 	*/
	public void hurtBoss(final int damage)
	{this.setcurrentHP(getcurrentHP()-damage);}
	public boolean getisFinalBoss()
	{return this.isFinalBoss;}
}