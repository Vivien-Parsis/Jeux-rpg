package jeux_RPG;

public class Room
{
	String RoomName;
	Person RoomBoss;
	Item RoomItem;
	Room NorthRoom;
	Room SouthRoom;
	Room EastRoom;
	Room WestRoom;
	
	public Room(String RoomName,Person RoomBoss,Item RoomItem)
	{
		this.RoomName = RoomName;
		this.RoomBoss = RoomBoss;
		this.RoomItem = RoomItem;
	}
	public void setExit(Room NorthRoom, Room SouthRoom, Room EastRoom, Room WestRoom)
	{
		this.NorthRoom = NorthRoom;
		this.SouthRoom = SouthRoom;
		this.EastRoom = EastRoom;
		this.WestRoom = WestRoom;
	}
	
	public String toString() 
	{return this.RoomName;}
	
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
	public Room getExit(String exit)
	{
		if(hasExit(exit)==false)
		{return null;}
		
		if(exit.equals("north"))
		{return this.NorthRoom;}
		if(exit.equals("south"))
		{return this.SouthRoom;}
		if(exit.equals("east"))
		{return this.EastRoom;}
		if(exit.equals("west"))
		{return this.WestRoom;}
		
		return null;
	}
}