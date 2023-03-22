package game;

public class Hero extends Person implements Comparable<Hero>{
	private int currentmana;
	final private int maxmana;
	final private int manaregen;
	private Weapon HeroWeapon;
	private Spell[] HeroSpell;
	private int iniative;
	private String WeaponType;
	public Hero(String name, int HP, int damagePoint, int defensePoint, int currentmana, int maxmana, int manaregen, int iniative, Weapon HeroWeapon, Spell[] HeroSpell, String WeaponType)
	{
		super(name, HP, damagePoint, defensePoint);
		this.currentmana = currentmana;
		this.maxmana = maxmana;
		this.manaregen = manaregen;
		this.iniative = iniative;
		this.HeroWeapon = HeroWeapon;
		if(HeroSpell.length==2)
		{this.HeroSpell = HeroSpell;}
		this.WeaponType = WeaponType;
	}
	public Hero()
	{
		super("Herotest", 50, 5, 5);
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
			"\nMana:"+this.currentmana+"/"+this.maxmana+", mana regen:"+this.manaregen+
			"\nWeapon:"+this.HeroWeapon.info()+"\n"+
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
			list += "Spell "+(i+1)+":"+this.HeroSpell[i].info()+"\n";
		}
		return list.substring(0,list.length()-1);
	}
	/**
 	* check if is a known spell for the hero
 	*/
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
	/**
 	* hurt the hero
 	*/
	public void hurtHero(int damage)
	{this.currentHP-=damage;}
	public void heal(int heal)
	{
		this.currentHP+=heal;
		if(this.currentHP>this.maxHP)
		{this.currentHP=this.maxHP;}
	}

	public int getcurrentmana()
	{return currentmana;}
	public void setcurrentmana(int newmana)
	{this.currentmana=newmana;}
	public void addcurrentmana(int plusmana)
	{this.currentmana+=plusmana;}
	public void removecurrentmana(int minusmana)
	{this.currentmana-=minusmana;}
	
	public int getmaxmana()
	{return maxmana;}

	public int getmanaregen()
	{return manaregen;}

	public Weapon getHeroWeapon()
	{return HeroWeapon;}

	public String getWeaponType()
	{return WeaponType;}

	public Spell[] getHeroSpell()
	{return HeroSpell;}

	public int getiniative()
	{return iniative;}

	public int compareTo(Hero hero)
	{
		int compare=0;
		if(this.iniative>hero.iniative)
		{compare = 1;}
		else
		{compare = -1;}
		return compare;
	}

	public void setWeapon(Weapon newItem)
	{this.HeroWeapon = newItem;}
}