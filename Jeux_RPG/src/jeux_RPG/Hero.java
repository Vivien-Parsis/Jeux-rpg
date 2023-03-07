package jeux_RPG;

public class Hero extends Person{
	int currentmana;
	int maxmana;
	Weapon HeroWeapon;
	Spell HeroSpell1;
	Spell HeroSpell2;
	
	public Hero(String name, int HP, int damagePoint, int defensePoint, int currentmana, int maxmana, Weapon HeroWeapon, Spell HeroSpell1, Spell HeroSpell2)
	{
		this.name = name;
		this.HP = HP;
		this.damagePoint = damagePoint;
		this.defensePoint = defensePoint;
		this.currentmana = currentmana;
		this.maxmana = maxmana;
		this.HeroWeapon = HeroWeapon;
		this.HeroSpell1 = HeroSpell1;
		this.HeroSpell2 = HeroSpell2;
	}
	public Hero()
	{this("Herotest",50,5,5,10,5, new Weapon(), new Spell(), new Spell());}
}
