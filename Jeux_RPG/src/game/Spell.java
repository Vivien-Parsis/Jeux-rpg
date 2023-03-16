package game;

public class Spell
{
	final private String SpellName;
	final private String SpellType;
	protected final static String[] allSpellType = {"heal", "offensive", "defensive", "stun"};
	final private int Manacost;
	final private int SpellValue;			
	
	public Spell(final String SpellName,final  String SpellType,final int Manacost,final int SpellValue)
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