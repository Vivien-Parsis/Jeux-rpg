package game;

import java.util.HashMap;

public class Room {
	final private String RoomName;
	private Person RoomPerson;
	private Item RoomItem;
	private HashMap<String, Room> HashExit;
	private final boolean canGoBack;
	/**
     *	List of all possible direction
     */
	final static String[] DirectionList = {"north", "south", "east", "west", "up", "down"};

	public Room(final String RoomName,final Person RoomPerson, final Item RoomItem,final boolean canGoBack)
	{
		this.RoomName = RoomName.replaceAll(" ","_");
		//person must a merchant or a boss
		if(RoomPerson instanceof Boss || RoomPerson instanceof Merchant)
		{this.RoomPerson = RoomPerson;}
		else
		{this.RoomPerson=null;}
		this.RoomItem = RoomItem;
		HashExit = new HashMap<String, Room>();
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
	public void setExit(final String direction, final Room exit)
	{
		if(knownDirection(direction))
		{this.HashExit.replace(direction, exit);}
		else
		{System.out.println("!unknown direction : "+direction);}
	}

	public String info()
	{
		return "~~~~~~room~~~~~~\n"+
				this+
    			"\n~~~~~~exit~~~~~~\n"+
    			this.stringExit();
	}
	public String save()
	{
		String content ="";
		content +=this.RoomName+";"+this.canGoBack+"|";
		if(this.RoomPerson!=null)
		{content +=this.RoomPerson.save()+"|";}
		else
		{content +="null|";}
		if(this.RoomItem!=null)
		{content +=this.RoomItem.save()+"|";}
		else
		{content +="null|";}
		for(String direction : DirectionList)
		{
			if(this.hasExit(direction))
			{content+=direction+":"+this.getExit(direction).getRoomName()+";";}
		}
		return content;
	}
	public String toString() 
	{return this.RoomName;}
	/**
 	*	@return all exits of the room
 	*/
	public String stringExit()
	{	
		String exit = "";
		for(String direction : DirectionList)
		{
			if(this.HashExit.get(direction)==null)
			{continue;}
			exit += direction+ ":"+this.HashExit.get(direction)+"\n";
		}
		return exit.substring(0,exit.length()-1).replaceAll("null", "no exit");
	}
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
	public Room getExit(String direction)
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

	public HashMap<String, Room> getHashExit()
	{return this.HashExit;}
	public boolean getcanGoBack()
	{return this.canGoBack;}
}