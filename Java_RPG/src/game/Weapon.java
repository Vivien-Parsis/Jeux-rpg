package game;

public class Weapon extends Item implements GameData{
	final private int attackpoint;
	
	private String WeaponType;
	public Weapon(final String nameItem,final int attackpoint, int goldvalue, String WeaponType)
	{
		super(nameItem,100,goldvalue);
		this.attackpoint = attackpoint;
		if(isKnownWeaponType(WeaponType))
		{this.WeaponType=WeaponType;}
		else
		{this.WeaponType=ListWeaponType[0];}
	}
	public Weapon()
	{this("testweapon",1,0, "sword");}
	/**
 	*	return info of the weapon 
 	*/
	public String info()
	{return this+"(damage point:"+this.attackpoint+", weapon type:"+this.WeaponType+", gold value:"+this.getgoldValue()+")";}
	
	public String save()
	{return super.save()+";"+this.attackpoint+";"+this.WeaponType+";WEAPON";}
	
	public int getattackpoint()
	{return this.attackpoint;}
	
	public String getWeaponType()
	{return this.WeaponType;}
	
	public static boolean isKnownWeaponType(String type)
    {
        boolean check = false;
        for(String knowType : ListWeaponType)
        {
            if(knowType.equals(type))
            {check=true;break;}
        }
        return check;
    }

	public boolean equals(Weapon toCompare)
	{
		return this.toString().equals(toCompare.toString())&&
			this.WeaponType.equals(toCompare.WeaponType)&&
			this.attackpoint==toCompare.attackpoint&&
			this.getgoldValue()==toCompare.getgoldValue();
	}
}