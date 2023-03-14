package jeux_RPG;

public class Spell
{
	String SpellName;
	String SpellType;
	final static String[] allSpellType = {"heal", "offensive", "defensive", "stun"};
	int Manacost;
	int SpellValue;			
	
	public Spell(String SpellName, String SpellType,int Manacost, int SpellValue)
	{
		this.SpellName = SpellName;
		this.SpellType = SpellType;
		this.Manacost = Manacost;
		this.SpellValue = SpellValue;
	}
	public Spell()
	{this("spelltest","offensive",5,5);}

	public String toString()
	{return this.SpellName;}
	/**
 	* 	return info of the spell
 	*/
	public String info()
	{return this.SpellName+"(type:"+SpellType+", mana cost:"+this.Manacost+", value:"+this.SpellValue+")";}
}