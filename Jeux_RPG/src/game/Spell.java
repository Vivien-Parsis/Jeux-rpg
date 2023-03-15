package game;

public class Spell
{
	private String SpellName;
	private String SpellType;
	protected final static String[] allSpellType = {"heal", "offensive", "defensive", "stun"};
	private int Manacost;
	private int SpellValue;			
	
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

	public String getSpellName()
	{return this.SpellName;}
	public String getSpellType()
	{return this.SpellType;}
	public int getManacost()
	{return this.Manacost;}
	public int getSpellValue()
	{return this.SpellValue;}
}