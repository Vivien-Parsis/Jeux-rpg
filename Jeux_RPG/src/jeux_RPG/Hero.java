package jeux_RPG;

public class Hero extends Person
{
	int currentmana;
	int maxmana;
	int manaregen;
	Weapon HeroWeapon;
	Spell HeroSpell1;
	Spell HeroSpell2;
	
	public Hero(String name, int HP, int damagePoint, int defensePoint, int currentmana, int maxmana, int manaregen, Weapon HeroWeapon, Spell HeroSpell1, Spell HeroSpell2)
	{
		this.name = name;
		this.currentHP = HP;
		this.maxHP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
		this.currentmana = currentmana;
		this.maxmana = maxmana;
		this.manaregen = manaregen;
		this.HeroWeapon = HeroWeapon;
		this.HeroSpell1 = HeroSpell1;
		this.HeroSpell2 = HeroSpell2;
	}
	public Hero()
	{this("Herotest",50,5,5,10,10,2, new Weapon(), new Spell(), new Spell());}

	public String toString()
	{return this.name +"(HP:"+this.currentHP+"/"+this.maxHP+", Mana:"+this.currentmana+"/"+this.maxmana+")";}

	/**
 	* Return all info of the hero 
 	*/
	public String info()
	{
		return 
			this.name +"\nHP:"+this.currentHP+"/"+this.maxHP+", damage point:"+this.damagePoint+
			",\nMana:"+this.currentmana+"/"+this.maxmana+", mana regen:"+this.manaregen+
			",\nWeapon:"+this.HeroWeapon.info()+
			",\nSpell 1:"+this.HeroSpell1.info()+
			",\nSpell 2:"+this.HeroSpell2.info();
	}
}