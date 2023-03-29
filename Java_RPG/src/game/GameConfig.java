package game;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
* Class that create the config of gameEngine
* @author VivienP
*/
public class GameConfig{
	final private GameEngine gameEngine;
	final static private Scanner myinput = new Scanner(System.in);

	public GameConfig(GameEngine mygame)
	{this.gameEngine=mygame;}

	protected static GameEngine defaultGameConfig()
	{	
		//creat all heroes	
		Hero[] HeroTab = new Hero[]{
			new Hero("warrior",160,15,5,10,1,new Weapon("sword",1,0,"sword"),new Spell[]{new Spell("reinforce_weapon","offensive",7,15),new Spell("flash","stun",10,2)}),
			new Hero("tank",190,10,6,10,1,new Weapon("spear",1,0,"spear"),new Spell[]{new Spell("shield","defensive",5,1),new Spell("shield_bash","stun",10,2)}),
			new Hero("wizard",135,15,4,10,2,new Weapon("staff",1,0,"staff"),new Spell[]{new Spell("fire_ball","offensive",5,15),new Spell("heal","heal",10,15)})
		};
		
		//create all rooms
		HashMap<String,Room> RoomHash = new HashMap<String,Room>();
		//start room
		RoomHash.put("start",new Room("gate",null,null,false));
		//final room
		RoomHash.put("final",new LockedRoom("final",new Boss("demon",100,14,5,true),null,true,new Item("final key",0),"11","up"));
		//merchant room
		RoomHash.put("cave",new LockedRoom("cave",new Merchant("Merchant",
		new ArrayList<Item>(Arrays.asList(new Item[]{new Weapon("gold sword",2,2,"sword"),new Weapon("void staff",2,2,"staff"),new Item("coin",1)}))),
		null,true,new Item("key",0),"12","down"));
		
		RoomHash.put("00",new Room("00",new Boss("goblin",60,7,5),new Item("key",0)));
		RoomHash.put("01",new Room("01",new Boss("spider",40,6,5),new Item()));
		RoomHash.put("02",new Room("02",new Boss("skeleton",60,9,5),new UsableItem("blue flask", 100, 0, 10, "mana_potion")));
		
		RoomHash.put("10",new Room("10",new Boss("goblin",60,10,5),new Item()));
		RoomHash.put("11",new Room("11",new Boss("wolf",50,6,5),new UsableItem("red flask", 100, 0, 10, "heal_potion")));
		RoomHash.put("12",new Room("12",new Boss("goblin",65,8,5),new Item()));
		
		RoomHash.put("20",new Room("20",new Boss("wolf",50,7,5),new UsableItem("black flask", 75, 0, 10, "damage_potion"),false));
		RoomHash.put("21",new Room("21",new Boss("skeleton",65,9,5),new Item()));
		RoomHash.put("22",new Room("22",new Boss("spider",45,8,5),new Item()));
		//setup exit for all rooms
		RoomHash.get("cave").setExit("up",RoomHash.get("12"));
		RoomHash.get("final").setExit("down",RoomHash.get("11"));

		RoomHash.get("start").setExit("down",RoomHash.get("01"));

		RoomHash.get("00").setExit("north",RoomHash.get("10"));
		RoomHash.get("00").setExit("east",RoomHash.get("01"));

		RoomHash.get("01").setExit("north",RoomHash.get("11"));
		RoomHash.get("01").setExit("east",RoomHash.get("02"));
		RoomHash.get("01").setExit("west",RoomHash.get("00"));

		RoomHash.get("02").setExit("north",RoomHash.get("12"));
		RoomHash.get("02").setExit("west",RoomHash.get("01"));

		RoomHash.get("10").setExit("south",RoomHash.get("00"));
		RoomHash.get("10").setExit("up",RoomHash.get("20"));
		RoomHash.get("10").setExit("east",RoomHash.get("11"));

		RoomHash.get("11").setExit("south",RoomHash.get("01"));
		RoomHash.get("11").setExit("north",RoomHash.get("21"));
		RoomHash.get("11").setExit("east",RoomHash.get("12"));
		RoomHash.get("11").setExit("west",RoomHash.get("10"));

		RoomHash.get("12").setExit("south",RoomHash.get("02"));
		RoomHash.get("12").setExit("north",RoomHash.get("22"));
		RoomHash.get("12").setExit("west",RoomHash.get("11"));

		RoomHash.get("20").setExit("south",RoomHash.get("10"));
		RoomHash.get("20").setExit("down",RoomHash.get("21"));	

		RoomHash.get("21").setExit("south",RoomHash.get("11"));
		RoomHash.get("21").setExit("east",RoomHash.get("22"));

		RoomHash.get("22").setExit("south",RoomHash.get("12"));
		RoomHash.get("22").setExit("east",RoomHash.get("21"));

		return new GameEngine(HeroTab,new Dungeon(RoomHash),RoomHash.get("start"),myinput);
	}
	protected static GameEngine saveGameConfig(int SaveNumber)
	{
		ArrayList<String>  SaveData = Save.ReadSave(SaveNumber);
		
		return new GameEngine(null, null, null, myinput);
	}
	
	/**
 	* Run the game
 	*/
	protected static void Run(GameEngine myGameEngine)
	{
		GameConfig game = new GameConfig(myGameEngine);
		game.gameEngine.Run();
	}
}