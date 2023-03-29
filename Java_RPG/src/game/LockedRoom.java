package game;

public class LockedRoom extends Room{
    private Item KeyItem;
    private String ExitName;
    private String ExitDirection; 
    public LockedRoom(final String RoomName,final Person character,final Item RoomItem,final boolean canGoBack,final Item KeyItem,final String ExitName,final String ExitDirection)
	{
        super(RoomName,character,RoomItem,canGoBack);
        this.KeyItem = KeyItem;
        this.ExitName = ExitName;
        this.ExitDirection = ExitDirection;
	}

    public Item getKeyItem() 
    {return KeyItem;}
    
    public String getExitName() 
    {return ExitName;}

    public String getExitDirection()
    {return ExitDirection;}
    public void setKeyItem(Item keyItem) 
    {KeyItem = keyItem;}

    public String save()
    {return super.save()+"|"+this.KeyItem.save()+"|"+this.ExitName+"|"+this.ExitDirection+"|LOCKED";}
}
