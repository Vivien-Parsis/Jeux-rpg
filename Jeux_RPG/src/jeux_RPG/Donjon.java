package jeux_RPG;
import java.util.HashMap;

public class Donjon
{
	String DonjonName;
	HashMap<String, Room> RoomHash = new HashMap<String, Room>();
	Item DonjonLoot;
	
	public Donjon(String DonjonName, HashMap<String, Room> RoomHash , Item DonjonLoot)
	{
		this.DonjonName = DonjonName;
		this.RoomHash = RoomHash;
		this.DonjonLoot = DonjonLoot;
	}
}
