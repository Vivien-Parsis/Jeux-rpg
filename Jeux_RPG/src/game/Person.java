package game;

public class Person{
	final protected String name;
	final protected int maxHP;
	protected int currentHP;
	final protected int damagePoint;
	protected int defensePoint;
	
	public Person(String name,int HP, int damagePoint, int defensePoint)
	{
		this.name = name;
		this.maxHP = HP;
		this.currentHP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
	}
	
	public Person() 
	{this("test",50,5,5);}
	
	public String toString()
	{return this.name;}

	public int getmaxHP()
	{return this.maxHP;}

	public int getcurrentHP()
	{return this.currentHP;}
	public void setcurrentHP(int newHP)
	{this.currentHP=newHP;}

	public int getdamagePoint()
	{return this.damagePoint;}

	public int getdefensePoint()
	{return this.defensePoint;}
}