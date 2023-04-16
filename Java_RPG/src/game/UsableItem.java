package game;

public class UsableItem extends Item implements GameData{
    final private int Itemvalue;
    private String ItemType;
    
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
    {return this+"(gold value:"+this.getgoldValue()+", usable type:"+this.ItemType+", value:"+this.Itemvalue+")";}

    public String save()
    {return super.save()+";"+this.ItemType+";"+this.Itemvalue+";USABLE";}

    public String getItemType() 
    {return ItemType;}
    public int getItemvalue() 
    {return Itemvalue;}
}