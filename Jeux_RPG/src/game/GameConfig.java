package game;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
/**
* Class that create the config of gameEngine
* @author VivienP
*/
public class GameConfig{
	final private GameEngine gameEngine;
	final static private Scanner myinput = new Scanner(System.in);
	private GameConfig()
	{	
		Hero[] HeroTab = new Hero[3];
		//creat all heroes	
		Spell[] WarriorSpell = {new Spell("reinforce_weapon","offensive",7,15), new Spell("flash","stun",10,1)};
		HeroTab[0] = new Hero("warrior",100,15,7,10,10,1, 100,new Weapon("sword",1,0,"sword"), WarriorSpell,"sword");
		
		Spell[] TankSpell = {new Spell("shield","defensive",5,1), new Spell("shield_bash","stun",10,2)};
		HeroTab[1] = new Hero("tank",125,10,9,10,10,1, 25,new Weapon("glaive",1,0,"glaive"), TankSpell,"glaive");

		Spell[] WizardSpell = {new Spell("fire_ball","offensive",5,15), new Spell("heal","heal",10,15)};
		HeroTab[2] = new Hero("wizard",80,15,5,10,10,2, 50,new Weapon("staff",1,0,"staff"), WizardSpell,"staff");
		
		//create all rooms
		HashMap<String, Room> RoomHash = new HashMap<String, Room>();
		
		RoomHash.put("start", new Room("gate", null, null));
		
		RoomHash.put("00", new Room("00", new Boss("goblin",60,7,5), new Item("key",100, 0)));
		RoomHash.put("01", new Room("01", new Boss("spider",40,6,5), new Item()));
		RoomHash.put("02", new Room("02", new Boss("skeleton",60,9,5), new Item()));
		
		RoomHash.put("10", new Room("10", new Boss("goblin",60,10,5), new Item()));
		RoomHash.put("11", new Room("11", new Boss("wolf",50,6,5), new Item()));
		RoomHash.put("12", new Room("12", new Boss("goblin",65,8,5), new Item()));
		
		ArrayList<Item> offer = new ArrayList<Item>();
		offer.add(new Weapon("gold_sword",2,2,"sword"));
		offer.add(new Weapon("void_staff",2,2,"staff"));
		offer.add(new Item("coin",100,1));
		RoomHash.put("13", new Room("13", new Merchant("Merchant",  offer), null));
		
		RoomHash.put("20", new Room("20", new Boss("wolf",50,7,5), new Item()));
		RoomHash.put("21", new Room("21", new Boss("skeleton",65,9,5), new Item()));
		RoomHash.put("22", new Room("22", new Boss("spider",45,8,5), new Item()));

		RoomHash.put("final", new Room("final", new Boss("demon",100,14,5), new Item()));
		
		//setup exit for all rooms
		RoomHash.get("cave").setExit("up", RoomHash.get("12"));
		RoomHash.get("final").setExit("down", RoomHash.get("11"));

		RoomHash.get("start").setExit("down",RoomHash.get("01"));

		RoomHash.get("00").setExit("north",RoomHash.get("10"));
		RoomHash.get("00").setExit("east",RoomHash.get("01"));

		RoomHash.get("01").setExit("north",RoomHash.get("11"));
		RoomHash.get("01").setExit("east",RoomHash.get("02"));
		RoomHash.get("01").setExit("west",RoomHash.get("00"));

		RoomHash.get("02").setExit("north",RoomHash.get("12"));
		RoomHash.get("02").setExit("west",RoomHash.get("01"));

		RoomHash.get("10").setExit("south",RoomHash.get("00"));
		RoomHash.get("10").setExit("north",RoomHash.get("20"));
		RoomHash.get("10").setExit("east",RoomHash.get("11"));

		RoomHash.get("11").setExit("south",RoomHash.get("01"));
		RoomHash.get("11").setExit("north",RoomHash.get("21"));
		RoomHash.get("11").setExit("east",RoomHash.get("12"));
		RoomHash.get("11").setExit("west",RoomHash.get("10"));

		RoomHash.get("12").setExit("south",RoomHash.get("02"));
		RoomHash.get("12").setExit("north",RoomHash.get("22"));
		RoomHash.get("12").setExit("west",RoomHash.get("11"));

		RoomHash.get("20").setExit("south",RoomHash.get("10"));
		RoomHash.get("20").setExit("east",RoomHash.get("21"));	

		RoomHash.get("21").setExit("south",RoomHash.get("11"));
		RoomHash.get("21").setExit("east",RoomHash.get("22"));
		RoomHash.get("21").setExit("west",RoomHash.get("20"));

		RoomHash.get("22").setExit("south",RoomHash.get("12"));
		RoomHash.get("22").setExit("east",RoomHash.get("21"));

		Item mainItem = new Item("emerald",100, 0);
		Dungeon GameDonjon = new Dungeon("Main",RoomHash,mainItem);

		this.gameEngine = new GameEngine(HeroTab,GameDonjon,RoomHash.get("start"), myinput);
	}
	/**
 	* Run the game
 	*/
	protected static void Run()
	{
		System.out.println("\n############\nrunning game\n############\n");
		GameConfig game = new GameConfig();
		game.gameEngine.Run();
	}
}