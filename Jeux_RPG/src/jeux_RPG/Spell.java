package jeux_RPG;

public class Spell
{
	String SpellName;
	int Manacost;
	//positive = attack spell, negative = health spell ?
	int DamagePoint;
	
	public Spell(String SpellName, int Manacost, int DamagePoint)
	{
		this.SpellName = SpellName;
		this.Manacost = Manacost;
		this.DamagePoint = DamagePoint;
	}
	public Spell()
	{this("spelltest",5,5);}

	public String toString()
	{
		return this.SpellName;
	}
}