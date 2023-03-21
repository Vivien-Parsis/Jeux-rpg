package game;

import java.util.HashMap;

public class Room {
	final private String RoomName;
	private Person character;
	private Item RoomItem;
	private HashMap<String, Room> HashExit;
	/**
     *	List of all possible direction
     */
	final static String[] DirectionList = {"north", "south", "east", "west", "up", "down"};
	public Room(final String RoomName,final Person character, final Item RoomItem)
	{
		this.RoomName = RoomName;
		this.character = character;
		this.RoomItem = RoomItem;
		HashExit = new HashMap<String, Room>();
		for(String Direction : DirectionList)
		{this.HashExit.put(Direction, null);}
	}
	public Room()
	{this("RoomTest", new Boss(), new Item());}
	/**
 	*	Set up exit for a one direction
 	*/
	public void setExit(final String direction, final Room exit)
	{
		if(knownDirection(direction))
		{this.HashExit.replace(direction, exit);}
	}

	public String info()
	{
		return "~~~~~~room~~~~~~\n"+
				this+
    			"\n~~~~~~exit~~~~~~\n"+
    			this.stringExit();
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
		if(this.character==null)
		{return false;}
		if(this.character instanceof Boss)
		{return true;}
		else
		{return false;}
	}
	public boolean hasMerchant()
	{
		if(this.character==null)
		{return false;}
		if(this.character instanceof Merchant)
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
	
	public Person getCharacter()
	{return this.character;}
	public Boss getRoomBoss()
	{
		if(this.character instanceof Boss)
		{return (Boss) this.character;}
		else
		{return null;}
	}
	public Merchant getRoomMerchant()
	{
		if(this.character instanceof Merchant)
		{return (Merchant) this.character;}
		else
		{return null;}
	}
	public void kill()
	{this.character=null;}

	public Item getRoomItem()
	{return this.RoomItem;}

	public HashMap<String, Room> getHashExit()
	{return this.HashExit;}
}