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
		
		Weapon staff = new Weapon("staff",1,1);
		Spell[] WizardSpell = {
			new Spell("fire ball","offensive",5,1),
			new Spell("heal","heal",10,1)};
		HeroList.add(new Hero("Wizard",80,15,10,10,10,2, staff, WizardSpell));
		
		Weapon sword = new Weapon("sword",1,1);
		Spell[] WarriorSpell = {
			new Spell("reinforce weapon","offensive",7,1),
			new Spell("flash","stun",10,1)};
		HeroList.add(new Hero("Warrior",100,15,5,10,10,1, sword, WarriorSpell));
		
		Weapon glaive = new Weapon("glaive",1,1);
		Spell[] TankSpell = {
			new Spell("shield","defensive",5,1),
			new Spell("shield bash","stun",10,1)};
		HeroList.add(new Hero("Tank",125,10,15,10,10,1, glaive, TankSpell));
		
		HashMap<String, Room> RoomHash = new HashMap<String, Room>();
		
		RoomHash.put("start", new Room("gate", null, null));
		
		RoomHash.put("00", new Room("00", new Boss(), new Item()));
		RoomHash.put("01", new Room("01", new Boss(), new Item()));
		RoomHash.put("02", new Room("02", new Boss(), new Item()));
		
		RoomHash.put("10", new Room("10", new Boss(), new Item()));
		RoomHash.put("11", new Room("11", new Boss(), new Item()));
		RoomHash.put("12", new Room("12", new Boss(), new Item()));
	
		RoomHash.put("20", new Room("20", new Boss(), new Item()));
		RoomHash.put("21", new Room("21", new Boss(), new Item()));
		RoomHash.put("22", new Room("22", new Boss(), new Item()));
		
		RoomHash.get("start").setExit("north",RoomHash.get("01"));

		RoomHash.get("00").setExit("north",RoomHash.get("10"));
		RoomHash.get("00").setExit("east",RoomHash.get("01"));

		RoomHash.get("01").setExit("south",RoomHash.get("start"));
		RoomHash.get("01").setExit("north",RoomHash.get("11"));
		RoomHash.get("01").setExit("east",RoomHash.get("00"));
		RoomHash.get("01").setExit("west",RoomHash.get("02"));

		RoomHash.get("02").setExit("north",RoomHash.get("12"));
		RoomHash.get("02").setExit("west",RoomHash.get("01"));

		RoomHash.get("10").setExit("south",RoomHash.get("00"));
		RoomHash.get("10").setExit("north",RoomHash.get("20"));
		RoomHash.get("10").setExit("east",RoomHash.get("11"));

		RoomHash.get("11").setExit("south",RoomHash.get("01"));
		RoomHash.get("11").setExit("north",RoomHash.get("21"));
		RoomHash.get("11").setExit("east",RoomHash.get("10"));
		RoomHash.get("11").setExit("west",RoomHash.get("12"));

		RoomHash.get("12").setExit("south",RoomHash.get("02"));
		RoomHash.get("12").setExit("north",RoomHash.get("22"));
		RoomHash.get("12").setExit("east",RoomHash.get("11"));

		RoomHash.get("20").setExit("south",RoomHash.get("10"));
		RoomHash.get("20").setExit("east",RoomHash.get("21"));	

		RoomHash.get("21").setExit("south",RoomHash.get("11"));
		RoomHash.get("21").setExit("east",RoomHash.get("22"));
		RoomHash.get("21").setExit("west",RoomHash.get("20"));

		RoomHash.get("22").setExit("south",RoomHash.get("12"));
		RoomHash.get("22").setExit("east",RoomHash.get("21"));

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