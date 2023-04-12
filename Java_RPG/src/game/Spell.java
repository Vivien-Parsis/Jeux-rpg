package game;

public class Spell implements GameData{
	final private String SpellName;
	final private String SpellType;
	final private int Manacost;
	final private int SpellValue;			
	
	public Spell(final String SpellName,final String SpellType,final int Manacost,final int SpellValue)
	{
		this.SpellName = SpellName.replaceAll(" ","_");
		this.Manacost = Manacost;
		this.SpellValue = SpellValue;
		if(isKnownSpellType(SpellType))
		{this.SpellType = SpellType;}
		else
		{this.SpellType = allSpellType[0];}
	}
	public Spell()
	{this("spelltest","offensive",5,5);}

	public String toString()
	{return this.SpellName;}
	/**
 	* 	@return info of the spell
 	*/
	public String info()
	{return this.SpellName+"(type:"+SpellType+", mana cost:"+this.Manacost+", value:"+this.SpellValue+")";}

	public String save()
	{return this.SpellName+";"+this.SpellType+";"+this.Manacost+";"+this.SpellValue;}

	public static boolean isKnownSpellType(String type)
	{
		boolean check = false;
        for(String knowType : allSpellType)
        {
            if(knowType.equals(type))
            {check=true;break;}
        }
        return check;
	}

	public String getSpellName()
	{return this.SpellName;}
	public String getSpellType()
	{return this.SpellType;}
	public int getManacost()
	{return this.Manacost;}
	public int getSpellValue()
	{return this.SpellValue;}
}