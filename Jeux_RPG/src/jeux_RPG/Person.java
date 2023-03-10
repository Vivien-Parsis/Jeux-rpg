package jeux_RPG;

public class Person
{
	String name;
	int HP;
	int damagePoint;
	int defensePoint;
	
	public Person(String name,int HP, int damagePoint, int defensePoint)
	{
		this.name = name;
		this.HP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
	}
	
	public Person() 
	{this("test",50,5,5);}
	
	final public String toString()
	{return this.name;}
}