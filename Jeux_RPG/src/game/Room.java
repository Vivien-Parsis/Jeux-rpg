package game;

import java.util.HashMap;

public class Room {
	final private String RoomName;
	private Boss RoomBoss;
	private Item RoomItem;
	private HashMap<String, Room> HashExit;
	/**
     *	List of all possible direction
     */
	final static String[] DirectionList = {"north", "south", "east", "west", "up", "down"};
	public Room(final String RoomName,final Boss RoomBoss,final Item RoomItem)
	{
		this.RoomName = RoomName;
		this.RoomBoss = RoomBoss;
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
	{return this.RoomBoss != null;}
	
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

	public Boss getRoomBoss()
	{return this.RoomBoss;}
	public void killRoomBoss()
	{this.RoomBoss=null;}

	public Item getRoomItem()
	{return this.RoomItem;}

	public HashMap<String, Room> getHashExit()
	{return this.HashExit;}
}