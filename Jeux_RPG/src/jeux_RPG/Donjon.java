package jeux_RPG;
import java.util.HashMap;

public class Donjon {
	String DonjonName;
	HashMap<String, Salle> RoomHash = new HashMap<String, Salle>();
	Objet DonjonLoot;
	public Donjon(String DonjonName, HashMap<String, Salle> RoomHash , Objet DonjonLoot)
	{
		this.DonjonName = DonjonName;
		this.RoomHash = RoomHash;
		this.DonjonLoot = DonjonLoot;
	}
}
