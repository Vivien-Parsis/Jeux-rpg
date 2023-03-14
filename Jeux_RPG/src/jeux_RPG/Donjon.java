package jeux_RPG;
import java.util.HashMap;

public class Donjon
{
	String DonjonName;
	HashMap<String, Room> RoomHash = new HashMap<String, Room>();
	Item DonjonLoot;
	
	public Donjon(String DonjonName, HashMap<String, Room> RoomHash, Item DonjonLoot)
	{
		this.DonjonName = DonjonName;
		this.RoomHash = RoomHash;
		this.DonjonLoot = DonjonLoot;
	}
	/**
 	* check if their still at least one alive bosss
 	*/
	public boolean checkStillAliveBoss()
	{
		boolean check = false;
		for(Room room : RoomHash.values())
		{
			if(room.RoomBoss != null)
			{
				check = true;
				break;
			}
		}
		return check;
	}
}
