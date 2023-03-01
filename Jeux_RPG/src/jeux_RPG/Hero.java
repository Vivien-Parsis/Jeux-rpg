package jeux_RPG;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Personnage{
	int currentmana;
	int maxmana;
	List<Objet> HeroItemList = new ArrayList<Objet>();
	int weight;
	public Hero(String name, int HP, int damagePoint, int defensePoint, int currentmana, int maxmana)
	{
		this.name = name;
		this.HP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
		this.currentmana = currentmana;
		this.maxmana = maxmana;
		this.HeroItemList = new ArrayList<Objet>();
		this.weight = 0;
	}
	public Hero()
	{
		this("Herotest",50,5,5,10,5);
	}
}
