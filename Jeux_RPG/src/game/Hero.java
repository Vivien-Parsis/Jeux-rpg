package game;

public class Hero extends Person
{
	private int currentmana;
	private int maxmana;
	private int manaregen;
	private Weapon HeroWeapon;
	private Spell[] HeroSpell;
	
	public Hero(String name, int HP, int damagePoint, int defensePoint, int currentmana, int maxmana, int manaregen, Weapon HeroWeapon, Spell[] HeroSpell)
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
		if(HeroSpell.length==2)
		{this.HeroSpell = HeroSpell;}
	}
	public Hero()
	{
		this.name = "Herotest";
		this.currentHP = 50;
		this.maxHP = 50;
		this.damagePoint = 5;
		this.defensePoint = 5;
		this.currentmana = 10;
		this.maxmana = 10;
		this.manaregen = 2;
		this.HeroWeapon = new Weapon();
		Spell[] heroSpell = {new Spell(), new Spell()};
		this.HeroSpell = heroSpell;
	}

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
			",\nWeapon:"+this.HeroWeapon.info()+",\n"+
			StringSpellList();
	}
	/**
 	* Return all spells of the hero
 	*/
	public String StringSpellList()
	{
		String list = "";
		for(int i = 0; i<this.HeroSpell.length; i++)
		{
			list += "Spell "+(i+1)+":"+this.HeroSpell[i].info()+",\n";
		}
		return list.substring(0,list.length()-2);
	}

	public boolean knownSpell(String spell)
	{
		boolean knownSpell = false;
		for(Spell herospell : this.HeroSpell)
		{
			if(spell.equals(herospell.getSpellName()))
			{
				knownSpell = true;
				break;
			}
		}
		return knownSpell;
	}
	public int getcurrentmana()
	{return currentmana;}
	public void setcurrentmana(int newmana)
	{this.currentmana=newmana;}
	public void addcurrentmana(int plusmana)
	{this.currentmana+=plusmana;}
	
	public int getmaxmana()
	{return maxmana;}

	public int getmanaregen()
	{return manaregen;}

	public Weapon getHeroWeapon()
	{return HeroWeapon;}

	public Spell[] getHeroSpell()
	{return HeroSpell;}
}