package jeux_RPG;

//import java.util.HashMap;

public class Room
{
	String RoomName;
	Boss RoomBoss;
	Item RoomItem;
	Room NorthRoom;
	Room SouthRoom;
	Room EastRoom;
	Room WestRoom;
	//HashMap<String, Room> HashExit;
	/**
     *	List of all possible direction
     */
	final static String[] DirectionList = {"north", "south", "east", "west"};
	public Room(String RoomName, Boss RoomBoss, Item RoomItem)
	{
		this.RoomName = RoomName;
		this.RoomBoss = RoomBoss;
		this.RoomItem = RoomItem;
	}
	public Room()
	{this("RoomTest", new Boss(), new Item());}
	/**
 	*	Set up all exit
 	*/
	public void setExit(Room NorthRoom, Room SouthRoom, Room EastRoom, Room WestRoom)
	{
		this.NorthRoom = NorthRoom;
		this.SouthRoom = SouthRoom;
		this.EastRoom = EastRoom;
		this.WestRoom = WestRoom;
	}
	
	public String toString() 
	{return this.RoomName;}
	/**
 	*	Return all exits of the room
 	*/
	public String stringExit()
	{	
		String exit =("North : "+this.NorthRoom+"\n"
				+"South : "+this.SouthRoom+"\n"
				+"East : "+this.EastRoom+"\n"
				+"West : "+this.WestRoom)
    			.replaceAll("null", "no exit");
		return exit;
	}
	/**
 	*	check if their is a exit in the selected direction
 	*/
	public boolean hasExit(String direction)
	{
		if(direction.equals(DirectionList[0]) && this.NorthRoom != null)
		{return true;}
		if(direction.equals(DirectionList[1]) && this.SouthRoom != null)
		{return true;}
		if(direction.equals(DirectionList[2]) && this.EastRoom != null)
		{return true;}
		if(direction.equals(DirectionList[3]) && this.WestRoom != null)
		{return true;}
		
		return false;
	}
	/**
 	*	Return if possible, the exit selected by the direction 
 	*/
	public Room getExit(String direction)
	{
		if(hasExit(direction)==false)
		{return null;}
		
		if(direction.equals(DirectionList[0]))
		{return this.NorthRoom;}
		if(direction.equals(DirectionList[1]))
		{return this.SouthRoom;}
		if(direction.equals(DirectionList[2]))
		{return this.EastRoom;}
		if(direction.equals(DirectionList[3]))
		{return this.WestRoom;}
		
		return null;
	}
	/**
 	*	Return if their is an alive boss in the room
 	*/
	public boolean hasBoss()
	{return this.RoomBoss != null;}
}