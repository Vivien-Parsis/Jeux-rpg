package game;
import java.util.HashMap;

public class Donjon {
	final private String DonjonName;
	private HashMap<String, Room> RoomHash = new HashMap<String, Room>();
	private Item DonjonLoot;
	
	public Donjon(final String DonjonName,final HashMap<String, Room> RoomHash,final Item DonjonLoot)
	{
		this.DonjonName = DonjonName;
		this.RoomHash = RoomHash;
		this.DonjonLoot = DonjonLoot;
	}
	/**
 	* check if their still at least one alive boss
 	*/
	public boolean checkStillAliveBoss()
	{
		boolean check = false;
		for(Room room : RoomHash.values())
		{
			if(room.getRoomBoss() != null)
			{
				check = true;
				break;
			}
		}
		return check;
	}
	public HashMap<String, Room> getRoomHash()
	{return this.RoomHash;}
	public String getDonjonName()
	{return this.DonjonName;}
	public Item getDonjonLoot()
	{return this.DonjonLoot;}
	public void clearDonjonLoot()
	{this.DonjonLoot=null;}
}