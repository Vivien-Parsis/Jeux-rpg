package game;

public class Weapon extends Item {
	final private int attackpoint;
	final protected static String[] ListWeaponType = {"glaive", "staff", "sword"};
	String WeaponType;
	public Weapon(final String nameItem,final int attackpoint, int goldvalue, String WeaponType)
	{
		super(nameItem,100, goldvalue);
		this.attackpoint = attackpoint;
		
		boolean knownWeaponType = false;
		for(String type : ListWeaponType)
		{if(type.equals(WeaponType)){knownWeaponType=true;break;}}
		if(knownWeaponType)
		{this.WeaponType = WeaponType;}
		else
		{this.WeaponType=ListWeaponType[0];}
	}
	public Weapon()
	{this("testweapon",1,0, "sword");}
	/**
 	*	return info of the weapon 
 	*/
	public String info()
	{return this+"(damage point:"+this.attackpoint+")";}

	public int getattackpoint()
	{return this.attackpoint;}

}