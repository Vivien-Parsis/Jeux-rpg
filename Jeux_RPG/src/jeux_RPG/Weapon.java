package jeux_RPG;

public class Weapon extends Item {
	int attackpoint;
	
	public Weapon(String nameItem, int weight, int attackpoint)
	{
		this.nameItem = nameItem;
		this.weight = weight;
		this.attackpoint = attackpoint;
	}
	public Weapon()
	{this("testweapon",1,1);}
}
