package game;

import java.util.HashMap;

public class Room implements GameData{
	final private String RoomName;
	private Person RoomPerson;
	private Item RoomItem;
	private HashMap<String, String> HashExit;
	private final boolean canGoBack;
	

	public Room(final String RoomName,final Person RoomPerson, final Item RoomItem,final boolean canGoBack)
	{
		this.RoomName = RoomName.replaceAll(" ","_");
		this.RoomPerson = RoomPerson;
		this.RoomItem = RoomItem;
		HashExit = new HashMap<String, String>();
		for(String Direction : DirectionList)
		{this.HashExit.put(Direction, null);}
		this.canGoBack = canGoBack;
	}
	public Room(final String RoomName,final Person RoomPerson, final Item RoomItem)
	{this(RoomName, RoomPerson, RoomItem, true);}
	public Room()
	{this("RoomTest", new Boss(), new Item(), true);}
	/**
 	*	Set up exit for a one direction
 	*/
	public void setExit(final String direction, final String exit)
	{
		if(knownDirection(direction))
		{this.HashExit.replace(direction, exit);}
		else
		{System.out.println("!unknown direction : "+direction);}
	}

	public String info()
	{
		return "~~~~~~room~~~~~~\n"+this;
	}
	public String save()
	{
		String content ="";
		content +=this.RoomName+"&"+this.canGoBack+"&";
		if(this.RoomPerson!=null)
		{content +=this.RoomPerson.save()+"&";}
		else
		{content +="null&";}
		if(this.RoomItem!=null)
		{content +=this.RoomItem.save()+"&";}
		else
		{content +="null&";}
		for(String direction : DirectionList)
		{
			if(this.hasExit(direction))
			{content+=direction+":"+this.getExit(direction)+";";}
		}
		return content.substring(0,content.length()-1);
	}
	public String toString() 
	{return this.RoomName;}
	
	/**
 	*	check if their is a exit in the selected direction
 	*/
	public boolean hasExit(String direction)
	{
		if(knownDirection(direction)==false)
		{return false;}
		if(this.HashExit.get(direction)!=null)
		{return true;}

		return false;
	}
	/**
 	*	@return if possible, the exit selected by the direction 
 	*/
	public String getExit(String direction)
	{
		if(knownDirection(direction)==false)
		{return null;}
		if(hasExit(direction)==false)
		{return null;}
		else
		{return this.HashExit.get(direction);}
	}
	/**
 	*	@return if their is an alive boss in the room
 	*/
	public boolean hasBoss()
	{
		if(this.RoomPerson==null)
		{return false;}
		if(this.RoomPerson instanceof Boss)
		{return true;}
		else
		{return false;}
	}
	public boolean hasMerchant()
	{
		if(this.RoomPerson==null)
		{return false;}
		if(this.RoomPerson instanceof Merchant)
		{return true;}
		else
		{return false;}
	}
	/**
 	*check is a known direction
 	*/
	public static boolean knownDirection(String directiontocheck)
	{
		boolean knownDirection = false;
		for(String direction : DirectionList)
		{
			if(direction.equals(directiontocheck))
			{
				knownDirection = true;
				break;
			}
		}
		return knownDirection;
	}
	public String getRoomName()
	{return this.RoomName;}
	
	public Person getRoomPerson()
	{return this.RoomPerson;}
	public Boss getRoomBoss()
	{
		if(this.RoomPerson instanceof Boss)
		{return (Boss) this.RoomPerson;}
		else
		{return null;}
	}
	public Merchant getRoomMerchant()
	{
		if(this.RoomPerson instanceof Merchant)
		{return (Merchant) this.RoomPerson;}
		else
		{return null;}
	}
	public void kill()
	{this.RoomPerson=null;}

	public Item getRoomItem()
	{return this.RoomItem;}

	public boolean equals(Room toCompare)
	{
		boolean checkitem=false;
		if(this.RoomItem==null && toCompare.RoomItem==null)
		{checkitem=true;}
		else if(this.RoomItem==null || toCompare.RoomItem==null)
		{checkitem=false;}
		else
		{checkitem=this.RoomItem.equals(toCompare.RoomItem);}

		return this.RoomName.equals(toCompare.RoomName)&&canGoBack==toCompare.canGoBack&&checkitem;
	}

	public HashMap<String, String> getHashExit()
	{return this.HashExit;}
	public boolean getcanGoBack()
	{return this.canGoBack;}
}