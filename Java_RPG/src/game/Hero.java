package game;

public class Hero extends Person{
	private int currentmana;
	final private int maxmana;
	final private int manaregen;
	private Weapon HeroWeapon;
	private Spell[] HeroSpell;
	private String WeaponType;

	public Hero(String name, int HP, int damagePoint, int defensePoint, int maxmana, int manaregen, Weapon HeroWeapon, Spell[] HeroSpell)
	{
		super(name, HP, damagePoint, defensePoint);
		this.currentmana = maxmana;
		this.maxmana = maxmana;
		this.manaregen = manaregen;
		this.HeroWeapon = HeroWeapon;
		if(HeroSpell.length==2)
		{this.HeroSpell = HeroSpell;}
		this.WeaponType = HeroWeapon.getWeaponType();
	}
	public Hero()
	{this("DefaultHero",50,5,5,10,1,new Weapon(),new Spell[]{new Spell(), new Spell()});}

	public Hero(String name, int maxHP, int currentHP, int damagePoint, int defensePoint,int currentmana, int maxmana, int manaregen, Weapon HeroWeapon, Spell[] HeroSpell)
	{
		super(name, maxHP, damagePoint, defensePoint);
		this.currentHP = currentHP;
		this.currentmana = currentmana;
		this.maxmana = maxmana;
		this.manaregen = manaregen;
		this.HeroWeapon = HeroWeapon;
		if(HeroSpell.length==2)
		{this.HeroSpell = HeroSpell;}
		this.WeaponType = HeroWeapon.getWeaponType();
	}

	public String toString()
	{return this.name +"(HP:"+this.currentHP+"/"+this.maxHP+", Mana:"+this.currentmana+"/"+this.maxmana+")";}
	/**
 	* Return all info of the hero 
 	*/
	public String save()
	{return super.save()+";"+this.currentmana+";"+this.maxmana+";"+this.manaregen+"&"+HeroWeapon.save()+"&"+this.HeroSpell[0].save()+"&"+this.HeroSpell[1].save();}
	public String info()
	{
		return 
			this.name+
			"\nHP:"+this.currentHP+"/"+this.maxHP+", damage point:"+this.damagePoint+
			"\nMana:"+this.currentmana+"/"+this.maxmana+", mana regen:"+this.manaregen+
			"\nWeapon type:"+this.WeaponType+", weapon:"+this.HeroWeapon.info()+
			"\n"+StringSpellList();
	}
	/**
 	* Return all spells of the hero
 	*/
	public String StringSpellList()
	{
		String list = "";
		for(int i = 0; i<this.HeroSpell.length; i++)
		{list += "Spell "+(i+1)+":"+this.HeroSpell[i].info()+"\n";}
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

	public void setWeapon(Weapon newItem)
	{this.HeroWeapon = newItem;}
}