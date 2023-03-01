package jeux_RPG;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		HashMap<String, Hero> HeroHash = new HashMap<String, Hero>();
		HeroHash.put("1", new Hero("mage",100,10,10,10,10));
		HeroHash.put("2", new Hero("guerrier",100,15,5,10,10));
		HeroHash.put("3", new Hero("tank",100,5,15,10,10));
		
		HashMap<String, Salle> RoomHash = new HashMap<String, Salle>();
		//20 21 22
		//10 11 12
		//00 01 02
		RoomHash.put( "00", new Salle("gate", new Boss(), new Objet()));
		RoomHash.put( "01", new Salle("noname", new Boss(), new Objet()));
		RoomHash.put( "02", new Salle("noname", new Boss(), new Objet()));
		
		RoomHash.put( "10", new Salle("noname", new Boss(), new Objet()));
		RoomHash.put( "11", new Salle("noname", new Boss(), new Objet()));
		RoomHash.put( "12", new Salle("noname", new Boss(), new Objet()));

		RoomHash.put( "20", new Salle("noname", new Boss(), new Objet()));
		RoomHash.put( "21", new Salle("noname", new Boss(), new Objet()));
		RoomHash.put( "22", new Salle("noname", new Boss(), new Objet()));
		
		RoomHash.get("00").setExit(RoomHash.get("10"), null, RoomHash.get("01"), null);
		RoomHash.get("01").setExit(null, null, null, null);
		RoomHash.get("02").setExit(null, null, null, null);
		
		RoomHash.get("10").setExit(null, null, null, null);
		RoomHash.get("11").setExit(null, null, null, null);
		RoomHash.get("12").setExit(null, null, null, null);
		
		RoomHash.get("20").setExit(null, null, null, null);
		RoomHash.get("21").setExit(null, null, null, null);
		RoomHash.get("22").setExit(null, null, null, null);
		
	
		Objet mainItem = new Objet("emerald",0);
		
		Donjon GameDonjon = new Donjon("Main",RoomHash,mainItem);
		System.out.println(RoomHash.get("00").NorthRoom.RoomName);
		GameEngine Game = new GameEngine(HeroHash,GameDonjon,RoomHash.get("00"));
		Game.RunGame();
	}

}
