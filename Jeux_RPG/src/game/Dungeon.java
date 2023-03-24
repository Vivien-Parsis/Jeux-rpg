package game;
import java.util.HashMap;

public class Dungeon {
	private HashMap<String, Room> RoomHash = new HashMap<String, Room>();
	public Dungeon(final HashMap<String, Room> RoomHash)
	{
		this.RoomHash = RoomHash;
	}
	/**
 	* check if their still at least one alive boss
 	*/
	public boolean checkStillAliveBoss()
	{
		boolean check = false;
		for(Room room : RoomHash.values())
		{
			if(room.getRoomBoss()!=null)
			{
				check = true;
				break;
			}
		}
		return check;
	}
	public HashMap<String, Room> getRoomHash()
	{return this.RoomHash;}
}