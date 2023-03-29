package game;

public class UsableItem extends Item{
    final private int Itemvalue;
    private String ItemType;
    /**
	 * List of all possible item type for the game. <br><br>
	 * heal_potion : heal one choosen hero <br><br>
	 * mana_potion : regenerate mana of one choosen hero<br><br>
     * damage_potion : damage the boss<br><br>
	 */
    final protected static String[] ListofItemType = {"heal_potion", "mana_potion", "damage_potion"};
    
    public UsableItem(String nameItem, int chance, int goldvalue, int Itemvalue, String ItemType)
    {
        super(nameItem, chance, goldvalue);
        this.Itemvalue = Itemvalue;
        if(isKnownItemType(ItemType))
        {this.ItemType=ItemType;}
        else
        {this.ItemType=ListofItemType[0];}
    }

    public static boolean isKnownItemType(String type)
    {
        boolean check = false;
        for(String knowType : ListofItemType)
        {
            if(knowType.equals(type))
            {check=true;break;}
        }
        return check;
    }
    public String info()
    {return this+"(gold value:"+goldvalue+", usable type:"+this.ItemType+", value:"+this.Itemvalue+")";}

    public String save()
    {return super.save()+";"+this.ItemType+";"+this.Itemvalue;}

    public String getItemType() 
    {return ItemType;}
    public int getItemvalue() 
    {return Itemvalue;}
}