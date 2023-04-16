package game;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
/**
* Class that create the config of gameEngine
* @author VivienP
*/
public class GameConfig{
	final private GameEngine gameEngine;
	final static private Scanner myinput = new Scanner(System.in);
	public GameConfig(GameEngine mygame)
	{this.gameEngine=mygame;}
	/**
	 * load default game config
	 */
	protected static GameEngine defaultGameConfig()
	{	
		GameEngine defaultGame; 
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
		RoomHash.put("merchant",new LockedRoom("cave",new Merchant("Merchant",
		new ArrayList<Item>(Arrays.asList(new Item[]{new Weapon("gold sword",2,2,"sword"),new Weapon("void staff",2,2,"staff"),new Item("coin",1)}))),
		null,true,new Item("key",0),"12","down"));
		
		RoomHash.put("00",new Room("hallway",new Boss("goblin",60,7,5),new Item("key",0)));
		RoomHash.put("01",new Room("hallway",new Boss("spider",40,6,5),new Item()));
		RoomHash.put("02",new Room("hallway",new Boss("skeleton",60,9,5),new UsableItem("blue flask", 100, 0, 10, "mana_potion")));
		
		RoomHash.put("10",new Room("hallway",new Boss("goblin",60,10,5),new Item()));
		RoomHash.put("11",new Room("intersection",new Boss("wolf",50,6,5),new UsableItem("red flask", 100, 0, 10, "heal_potion")));
		RoomHash.put("12",new Room("hallway",new Boss("goblin",65,8,5),new Item()));
		
		RoomHash.put("20",new Room("hallway",new Boss("wolf",50,7,5),new UsableItem("black flask", 75, 0, 10, "damage_potion"),false));
		RoomHash.put("21",new Room("hallway",new Boss("skeleton",65,9,5),new Item()));
		RoomHash.put("22",new Room("hallway",new Boss("spider",45,8,5),new Item()));
		//setup exit for all rooms
		RoomHash.get("merchant").setExit("up","12");
		RoomHash.get("final").setExit("down","11");
		RoomHash.get("start").setExit("down","01");

		RoomHash.get("00").setExit("north","10");
		RoomHash.get("00").setExit("east","01");

		RoomHash.get("01").setExit("north","11");
		RoomHash.get("01").setExit("east","02");
		RoomHash.get("01").setExit("west","00");

		RoomHash.get("02").setExit("north","12");
		RoomHash.get("02").setExit("west","01");

		RoomHash.get("10").setExit("south","00");
		RoomHash.get("10").setExit("up","20");
		RoomHash.get("10").setExit("east","11");

		RoomHash.get("11").setExit("south","01");
		RoomHash.get("11").setExit("north","21");
		RoomHash.get("11").setExit("east","12");
		RoomHash.get("11").setExit("west","10");

		RoomHash.get("12").setExit("south","02");
		RoomHash.get("12").setExit("north","22");
		RoomHash.get("12").setExit("west","11");

		RoomHash.get("20").setExit("down","21");	

		RoomHash.get("21").setExit("south","11");
		RoomHash.get("21").setExit("east","22");

		RoomHash.get("22").setExit("south","12");
		RoomHash.get("22").setExit("east","21");

		defaultGame = new GameEngine(HeroTab,new Dungeon(RoomHash),RoomHash.get("start"),myinput);
		defaultGame.getHeroBag().add(new UsableItem("red flask", 100, 0, 10, "heal_potion"));
		defaultGame.getHeroBag().add(new Item());
		return defaultGame;
	}
	/**
	 * load a game config from a save file
	 */
	protected static GameEngine saveGameConfig(int SaveNumber)
	{
		GameEngine mySaveGame;
		ArrayList<String> SaveData = Save.ReadSave(SaveNumber);
		ArrayList<String[]> SplitedData = new ArrayList<String[]>();
		ArrayList<ArrayList<String>> Exit = new ArrayList<ArrayList<String>>();
		if(SaveData.size()==0)
		{
			System.out.println("!error while loading save file, empty file");
			return null;
		}
		for(String line : SaveData)
		{
			if(line.split("#").length!=2)
			{
				System.out.println("!error while loading save file, unknown format");
				return null;
			}
			SplitedData.add(line.split("#"));
		}
		Hero[] HeroTab = new Hero[3];
		String StartRoom = "";
		HashMap<String, Room> RoomHash = new HashMap<String, Room>();
		ArrayList<Item> bag = new ArrayList<Item>();
		Stack<String> LastRoom = new Stack<String>();
		Item finalkey = null;

		for(String[] data : SplitedData)
		{
			//creation of heroes
			if(data[0].substring(0,4).equals("Hero"))
			{
				//case of death hero
				if(data[1].equals("null"))
				{HeroTab[Integer.parseInt(data[0].substring(4,5))] = null;continue;}
				String[] Param = data[1].split("&");
				String[] primaryparam = Param[0].split(";");
				String[] weaponparam = Param[1].split(";");
				String[] spell1param = Param[2].split(";");
				String[] spell2param = Param[3].split(";");
				Weapon HeroWeapon = new Weapon(weaponparam[0],Integer.parseInt(weaponparam[1]),Integer.parseInt(weaponparam[3]),weaponparam[4]);
				Spell[] heroSpells = {
					new Spell(spell1param[0],spell1param[1],Integer.parseInt(spell1param[2]),Integer.parseInt(spell1param[3])), 
					new Spell(spell2param[0],spell2param[1],Integer.parseInt(spell2param[2]),Integer.parseInt(spell2param[3]))};
				HeroTab[Integer.parseInt(data[0].substring(4,5))] = new Hero(primaryparam[0],Integer.parseInt(primaryparam[1]),
				Integer.parseInt(primaryparam[2]),Integer.parseInt(primaryparam[3]),Integer.parseInt(primaryparam[4]),
				Integer.parseInt(primaryparam[5]),Integer.parseInt(primaryparam[6]),Integer.parseInt(primaryparam[7]),
				HeroWeapon, heroSpells);
			}
			if(data[0].equals("bag_hero"))
			{
				if(!data[1].equals("empty"))
				{
					String[] listItem = data[1].split("&");
					for(String item : listItem)
					{
						String[] paramItem = item.split(";");
						if(paramItem[paramItem.length-1].equals("USABLE"))
						{bag.add(new UsableItem(paramItem[0],Integer.parseInt(paramItem[2]),Integer.parseInt(paramItem[1]),Integer.parseInt(paramItem[4]),paramItem[3]));}
						else if(paramItem[paramItem.length-1].equals("WEAPON"))
						{bag.add(new Weapon(paramItem[0], Integer.parseInt(paramItem[3]), Integer.parseInt(paramItem[0]), paramItem[4]));}
						else
						{bag.add(new Item(paramItem[0], Integer.parseInt(paramItem[2]), Integer.parseInt(paramItem[1])));}
					}
				}
			}
			//creation of dungeon
			if(data[0].equals("room_dungeon"))
			{
				String[] param = data[1].split("&");
				Room currentRoom;
				Person RoomPerson;
				Item RoomItem;
				if(param[3].equals("null"))
				{RoomPerson = null;}
				else if(param[3].split(";")[0].equals("boss"))
				{
					String[] paramboss = param[3].split(";");
					RoomPerson = new Boss(paramboss[1],Integer.parseInt(paramboss[2]),Integer.parseInt(paramboss[4]),Integer.parseInt(paramboss[5]), Boolean.parseBoolean(paramboss[6]));
					RoomPerson.setcurrentHP(Integer.parseInt(paramboss[3]));
				}
				else
				{
					String[] parammerch = param[3].split(":");
					ArrayList<Item> offer = new ArrayList<Item>();
					if(!parammerch[1].equals("empty"))
					{
						for(int i = 1; i<parammerch.length; i++)
						{
							String[] paramitemoffer = parammerch[i].split(";");
							Item currentItem;
							if(paramitemoffer[paramitemoffer.length-1].equals("WEAPON"))
							{currentItem = new Weapon(paramitemoffer[0],Integer.parseInt(paramitemoffer[1]),Integer.parseInt(paramitemoffer[3]),paramitemoffer[4]);}
							else if(paramitemoffer[paramitemoffer.length-1].equals("USABLE"))
							{currentItem = new UsableItem(paramitemoffer[0],Integer.parseInt(paramitemoffer[2]),Integer.parseInt(paramitemoffer[1]),Integer.parseInt(paramitemoffer[4]),paramitemoffer[3]);}
							else
							{currentItem = new Item(paramitemoffer[0],Integer.parseInt(paramitemoffer[2]),Integer.parseInt(paramitemoffer[1]));}
							offer.add(currentItem);
						}
					}
					RoomPerson = new Merchant(parammerch[0].split(";")[0],offer);
				}
				String[] paramItem=param[4].split(";");
				if(param[4].equals("null"))
				{RoomItem=null;}
				else if(paramItem[paramItem.length-1].equals("WEAPON"))
				{RoomItem = new Weapon(paramItem[0],Integer.parseInt(paramItem[1]),Integer.parseInt(paramItem[3]),paramItem[4]);}
				else if(paramItem[paramItem.length-1].equals("USABLE"))
				{RoomItem = new UsableItem(paramItem[0],Integer.parseInt(paramItem[2]),Integer.parseInt(paramItem[1]),Integer.parseInt(paramItem[4]),paramItem[3]);}
				else
				{RoomItem = new Item(paramItem[0],Integer.parseInt(paramItem[2]),Integer.parseInt(paramItem[1]));}
				if(param[param.length-1].equals("LOCKED"))
				{
					Item keyItem;
					if(param[param.length-4].equals("null"))
					{keyItem = null;}
					else
					{
						String[] paramkey = param[param.length-4].split(";");
						keyItem = new Item(paramkey[0],Integer.parseInt(paramkey[2]),Integer.parseInt(paramkey[1]));
					}
					currentRoom = new LockedRoom(param[1],RoomPerson,RoomItem,Boolean.parseBoolean(param[2]),keyItem,param[param.length-3],param[param.length-2]);
				}
				else
				{currentRoom = new Room(param[1],RoomPerson,RoomItem,Boolean.parseBoolean(param[2]));}
				RoomHash.put(param[0],currentRoom);
				if(currentRoom!=null)
				{
					ArrayList<String> currentExit = new ArrayList<String>();
					currentExit.add(param[0]);
					for(int i = 0; i<param[5].split(";").length; i++)
					{currentExit.add(param[5].split(";")[i]);}
					
					Exit.add(currentExit);
				}
			}
			if(data[0].equals("current_room"))
			{StartRoom=data[1];}
			//final key
			if(data[0].equals("final_key"))
			{
				if(!data[1].equals("null"))
				{finalkey = new Item(data[1].split(";")[0],Integer.parseInt(data[1].split(";")[1]));}
			}
			if(data[0].equals("LastRoom"))
			{
				String[] listBackRoom = data[1].split("&");
				if(!data[1].equals("Empty"))
				{
					for(String backRoom : listBackRoom)
					{LastRoom.push(backRoom);}
				}
			}

		}
		//setup exit
		for(ArrayList<String> currentexit: Exit)
		{
			if(currentexit.size()>1)
			{
				for(int i = 1; i<currentexit.size(); i++)
				{
					String[] exits = currentexit.get(i).split(":");
					RoomHash.get(currentexit.get(0)).setExit(exits[0], exits[1]);;
				}
			}
		}
		System.out.println(StartRoom);
		mySaveGame = new GameEngine(HeroTab, new Dungeon(RoomHash),RoomHash.get(StartRoom), myinput);
		mySaveGame.setHeroBag(bag);
		mySaveGame.setLastRoom(LastRoom);
		mySaveGame.getDungeon().setFinalKey(finalkey);
		return mySaveGame;
	}
	/**
 	* Run the game
 	*/
	protected static void Run(GameEngine myGameEngine)
	{
		if(myGameEngine==null)
		{return;}
		GameConfig game = new GameConfig(myGameEngine);
		game.gameEngine.Run();
	}
}