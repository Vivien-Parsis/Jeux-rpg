package jeux_RPG;

public class Room
{
	String RoomName;
	Boss RoomBoss;
	Item RoomItem;
	Room NorthRoom;
	Room SouthRoom;
	Room EastRoom;
	Room WestRoom;
	
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
		String exit ="North : "+this.NorthRoom+"\n"
				+"South : "+this.SouthRoom+"\n"
				+"East : "+this.EastRoom+"\n"
				+"West : "+this.WestRoom;
		return exit;
	}
	public boolean hasExit(String exit)
	{
		if(exit.equals("north") && this.NorthRoom != null)
		{return true;}
		if(exit.equals("south") && this.SouthRoom != null)
		{return true;}
		if(exit.equals("east") && this.EastRoom != null)
		{return true;}
		if(exit.equals("west") && this.WestRoom != null)
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
		
		if(direction.equals("north"))
		{return this.NorthRoom;}
		if(direction.equals("south"))
		{return this.SouthRoom;}
		if(direction.equals("east"))
		{return this.EastRoom;}
		if(direction.equals("west"))
		{return this.WestRoom;}
		
		return null;
	}
	/**
 	*	Return if their is an alive boss in the room
 	*/
	public boolean hasBoss()
	{return this.RoomBoss != null;}
}