package game;

public abstract class Person{
	final private String name;
	final private int maxHP;
	private int currentHP;
	final private int damagePoint;
	private int defensePoint;
	
	public Person(String name, int HP, int damagePoint, int defensePoint)
	{
		this.name = name.replaceAll(" ","_");
		this.maxHP = HP;
		this.currentHP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
	}

	public abstract String info();
	public abstract String save();
	
	public String getname()
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