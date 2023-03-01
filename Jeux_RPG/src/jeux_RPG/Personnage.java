package jeux_RPG;

public class Personnage {
	String name;
	int HP;
	int damagePoint;
	int defensePoint;
	
	public Personnage(String name,int HP, int damagePoint, int defensePoint)
	{
		this.name = name;
		this.HP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
	}
	public Personnage() {
		this("test",50,5,5);
	}
}
