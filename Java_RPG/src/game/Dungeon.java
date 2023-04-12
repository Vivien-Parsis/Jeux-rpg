package game;
import java.util.HashMap;

public class Dungeon {
	private HashMap<String, Room> RoomHash = new HashMap<String, Room>();
	private Item finalKey; 
	
	public Dungeon(final HashMap<String, Room> RoomHash)
	{
		this.RoomHash = RoomHash;
		finalKey = new Item("final key",0);
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
	/**
	 * return the key of Room from the roomHash
	 */
	public String getKeyofRoomHash(Room room)
	{
		for(String key : this.RoomHash.keySet())
		{
			if(this.RoomHash.get(key).equals(room))
			{return key;}
		}
		return null;
	}

	public HashMap<String, Room> getRoomHash()
	{return this.RoomHash;}
	public Item getFinalKey()
	{return finalKey;}
	public void setFinalKey(Item finalKey)
	{this.finalKey = finalKey;}
}