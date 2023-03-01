package jeux_RPG;

public class Weapon extends Objet {
	int attackpoint;
	public Weapon(String nameItem, int weight)
	{
		this.nameItem = nameItem;
		this.weight = weight;
	}
	public Weapon()
	{
		this("testweapon",1);
		this.attackpoint = 1;
	}
}
