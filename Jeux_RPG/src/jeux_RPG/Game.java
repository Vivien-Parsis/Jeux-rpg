package jeux_RPG;
import java.util.HashMap;
import java.util.ArrayList;
/**
*	Class that create the gameEngine and his config
*/
public class Game
{
	GameEngine gameEngine;

	public Game()
	{
		ArrayList<Hero> HeroList = new ArrayList<Hero>();
		HeroList.add(new Hero("Wizard",80,10,10,10,10,2, new Weapon(), new Spell(), new Spell()));
		HeroList.add(new Hero("Warrior",100,15,5,10,10,1, new Weapon(), new Spell(), new Spell()));
		HeroList.add(new Hero("Tank",125,5,15,10,10,1, new Weapon(), new Spell(), new Spell()));
		
		HashMap<String, Room> RoomHash = new HashMap<String, Room>();
		//20  21  22
		//10  11  12
		//00  01  02
		//xx start xx  	
		RoomHash.put( "start", new Room("gate", null, null));
		
		RoomHash.put( "00", new Room("noname", new Boss(), new Item()));
		RoomHash.put( "01", new Room("noname", new Boss(), new Item()));
		RoomHash.put( "02", new Room("noname", new Boss(), new Item()));
		
		RoomHash.put( "10", new Room("noname", new Boss(), new Item()));
		RoomHash.put( "11", new Room("noname", new Boss(), new Item()));
		RoomHash.put( "12", new Room("noname", new Boss(), new Item()));
	
		RoomHash.put( "20", new Room("noname", new Boss(), new Item()));
		RoomHash.put( "21", new Room("noname", new Boss(), new Item()));
		RoomHash.put( "22", new Room("noname", new Boss(), new Item()));
		
		RoomHash.get("start").setExit(RoomHash.get("01"), null, null, null);
				
		RoomHash.get("00").setExit(RoomHash.get("10"), null, RoomHash.get("01"), null);
		RoomHash.get("01").setExit(RoomHash.get("11"), RoomHash.get("start"), RoomHash.get("02"), RoomHash.get("00"));
		RoomHash.get("02").setExit(RoomHash.get("12"), null, null, RoomHash.get("01"));
		
		RoomHash.get("10").setExit(RoomHash.get("20"), RoomHash.get("00"), RoomHash.get("11"), null);
		RoomHash.get("11").setExit(RoomHash.get("21"), RoomHash.get("01"), RoomHash.get("12"), RoomHash.get("10"));
		RoomHash.get("12").setExit(RoomHash.get("22"), RoomHash.get("02"), null, RoomHash.get("11"));
		
		RoomHash.get("20").setExit(null, RoomHash.get("10"), RoomHash.get("21"), null);
		RoomHash.get("21").setExit(null, RoomHash.get("11"), RoomHash.get("22"), RoomHash.get("20"));
		RoomHash.get("22").setExit(null, RoomHash.get("12"), null, RoomHash.get("21"));
		
		Item mainItem = new Item("emerald",0,"");
		Donjon GameDonjon = new Donjon("Main",RoomHash,mainItem);

		this.gameEngine = new GameEngine(HeroList,GameDonjon,RoomHash.get("start"),10,0);
	}
	/**
 	* Run the game
 	*/
	public void RunGame()
	{this.gameEngine.RunGame();}
}