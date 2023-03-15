package jeux_RPG;
/**
*	Class that handle command
*/
public class Command {
    /**
     *	List of all commands in explore phase
     */
    final static String[][] CommandList = 
	{{"/quit", "", "quit the game"},
	{"/move", "{direction} ", "move the player into a direction"},
	{"/attack","", "launch attack mode on current boss"},
	{"/info","{hero/boss/item/none} ","display current info about the current boss, a hero, a item or current situation"},
	{"/help", "{command} ", "display how work a command"},
	{"/back", "", "go to the previous visited room"}};
	/**
     *	List of all commands in combat phase
     */
	final static String[][] AttackCommand = 
	{{"/help", "{command} ", "display how work a command"},
	{"/spell", "{spell} ", "use one spell of your current hero"},
	{"/use", "{item} ", "use one item"},
	{"/weapon", "", "attack with weapon of your current hero"},
	{"/leave", "", "leave combat"},
	{"/info","{hero/boss/item} ","display current info about the current boss, a hero or a item"}};
	/**
 	*	return all commands	
 	*/
	public static String stringCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(int i = 0; i<CommandList.length;i++)
    	{list += CommandList[i][0]+" "+CommandList[i][1];}
    	return list.replaceAll(" /", ", /");
    }
	/**
 	*	return all commands of the combat phase	
 	*/
	public static String stringCombatCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(int i = 0; i<AttackCommand.length;i++)
    	{list += AttackCommand[i][0]+" "+AttackCommand[i][1];}
    	return list.replaceAll(" /", ", /");
    }
	/**
 	*	read and return command output
 	*/
	public static String[] ReadCommand(GameEngine myGameEngine)
	{
		System.out.print(">");
    	String stringCommand = myGameEngine.command.nextLine();
    	return stringCommand.split(" ");
	}

	/**
 	*	execute a command
 	*/
	public static int RunCommand(GameEngine myGameEngine)
    {
    	String[] tabCommand = ReadCommand(myGameEngine);
    	//quit case
    	if(tabCommand[0].equals(CommandList[0][0]))
        {
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+CommandList[0][0]+" "+CommandList[0][1]+"!");
        		return -1;
			}
			return 0;
		}
		//info case
		if(tabCommand[0].equals(CommandList[3][0]))
        {
    		if(tabCommand.length>2)
			{
				System.out.println(":must be "+CommandList[3][0]+" "+CommandList[3][1]+"!");
				return -1;
			}
			if(tabCommand.length==1)
			{
				System.out.println(myGameEngine.info());
				return 3;
			}
			String info = "";
			boolean isboss = false;
			if(myGameEngine.CurrentRoom.RoomBoss!=null)
			{
				isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.CurrentRoom.RoomBoss.name);
			}
			
			if(isboss)
			{
				info=myGameEngine.CurrentRoom.RoomBoss.info();
			}
			boolean ishero = false;
			for(Hero hero : myGameEngine.HeroList)
			{
				if(hero.name.equals(tabCommand[1]))
				{
					ishero = true;
					info = hero.info();
					break;
				}
			}
			boolean isitem = false;
			for(Item item : myGameEngine.HeroBag)
			{
				if(item.nameItem.equals(tabCommand[1]))
				{
					isitem = true;
					info = item.info();
					break;
				}
			}
			if(!isboss && !ishero && !isitem)
			{
				System.out.println(":unknown");
				return -1;
			}
			System.out.println(":"+info);
				
			return 3;
		}	
		//help case
		if(tabCommand[0].equals(CommandList[4][0]))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+CommandList[4][0]+" "+CommandList[4][1]+"!");
        		return -1;
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+CommandList[4][2]);
				return 4;
			}
			else
			{
				boolean knowncommand = false;
				int index = 0;
				for(int i = 0; i<CommandList.length;i++)
				{
					if(tabCommand[1].equals(CommandList[i][0]) || tabCommand[1].equals(CommandList[i][0].substring(1)))
					{index=i;knowncommand=true;break;}
				}
				if(!knowncommand)
				{
					System.out.println(":must be "+CommandList[4][0]+" "+CommandList[4][1]+"!");
        			return -1;
				}
				else
				{
					System.out.println(":"+CommandList[index][2]);
					return 4;
				}
			}
		}
        //move case
        if(tabCommand[0].equals(CommandList[1][0]))
        {
        	//case of wrong format
        	if(tabCommand.length!=2)
        	{
        		System.out.println(":must be "+CommandList[1][0]+" "+CommandList[1][1]+"!");
        		return -1;
        	}
        	boolean knowdirection = Room.knownDirection(tabCommand[1]);
        	
        	//case of unknown possible direction
        	if(!knowdirection)
        	{
        		System.out.println(":no such direction");
        		return -1;
        	}
        	//case of no exit
        	if(!myGameEngine.CurrentRoom.hasExit(tabCommand[1]))
        	{
        		System.out.println(":can't go that way");
        		return -1;
        	}
        	//case of a successful move
        	else
        	{
				if(!myGameEngine.CurrentRoom.RoomName.equals("gate"))
				{myGameEngine.LastRoom.push(myGameEngine.CurrentRoom);}
        		myGameEngine.CurrentRoom = myGameEngine.CurrentRoom.getExit(tabCommand[1]);
        		System.out.println(":moving to "+tabCommand[1]+"\n");
        		System.out.println(myGameEngine.info());
        		return 1;
        	}
        }
		//attack case
		if(tabCommand[0].equals(CommandList[2][0]))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+CommandList[2][0]+" "+CommandList[2][1]+"!");
        		return -1;
			}
			if(myGameEngine.CurrentRoom.hasBoss())
			{
				System.out.println(":launch attack on the current boss");
				return 2;
			}
			else
			{
				System.out.println(":no boss in here !");
				return -1;
			}
		}
		//back case
		if(tabCommand[0].equals(CommandList[5][0]))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+CommandList[5][0]+" "+CommandList[5][1]+"!");
        		return -1;
			}
			if(myGameEngine.LastRoom==null)
			{
				System.out.println(":no previous romm");
				return -1;
			}
			if(myGameEngine.LastRoom.isEmpty())
			{
				System.out.println(":no previous romm");
				return -1;
			}
			else
			{
				System.out.println(":go back\n");
				myGameEngine.CurrentRoom = myGameEngine.LastRoom.pop();
				System.out.println(myGameEngine.info());
				return 5;
			}
		}
        //case of unknown command
        return -2;
    }
	/**
 	*	execute a combat command	
 	*/
	public static int RunCombatCommand(GameEngine myGameEngine, Hero currentHero)
	{
		String[] tabCommand = ReadCommand(myGameEngine);
		//leave case
		if(tabCommand[0].equals(AttackCommand[4][0]))
		{
			if(tabCommand.length>1)
        	{
        		System.out.println(":must be "+AttackCommand[4][0]+" "+AttackCommand[4][1]+"!");
        		return -1;
        	}
			return 4;
		}
		//help case
		if(tabCommand[0].equals(AttackCommand[0][0]))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+AttackCommand[0][0]+" "+AttackCommand[0][1]+"!");
        		return -1;
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+AttackCommand[0][2]);
				return 0;
			}
			else
			{
				boolean knowncommand = false;
				int index = 0;
				for(int i = 0; i<AttackCommand.length;i++)
				{
					if(tabCommand[1].equals(AttackCommand[i][0]) || tabCommand[1].equals(AttackCommand[i][0].substring(1)))
					{index=i;knowncommand=true;break;}
				}
				if(!knowncommand)
				{
					System.out.println(":must be "+AttackCommand[0][0]+" "+AttackCommand[0][1]+"!");
        			return -1;
				}
				else
				{
					System.out.println(":"+AttackCommand[index][2]);
					return 0;
				}
			}
		}
		//spell case
		if(tabCommand[0].equals(AttackCommand[1][0]))
		{
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+AttackCommand[1][0]+" "+AttackCommand[1][1]+"!");
        		return -1;
			}
			return 1;
		}
		//use case
		if(tabCommand[0].equals(AttackCommand[2][0]))
		{
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+AttackCommand[2][0]+" "+AttackCommand[2][1]+"!");
        		return -1;
			}
			return 2;
		}
		//weapon case
		if(tabCommand[0].equals(AttackCommand[3][0]))
		{
			if(tabCommand.length!=1)
			{
				System.out.println(":must be "+AttackCommand[3][0]+" "+AttackCommand[3][1]+"!");
        		return -1;
			}
			System.out.println(":attacking with a weapon");
			myGameEngine.hurtBoss((currentHero.HeroWeapon.attackpoint*currentHero.damagePoint)-myGameEngine.CurrentRoom.RoomBoss.defensePoint);
			return 3;
		}
		//info case
		if(tabCommand[0].equals(AttackCommand[5][0]))
		{
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+AttackCommand[5][0]+" "+AttackCommand[5][1]+"!");
        		return -1;
			}
			String info = "";
			boolean isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.CurrentRoom.RoomBoss.name);
			if(isboss)
			{
				info=myGameEngine.CurrentRoom.RoomBoss.info();
			}
			boolean ishero = false;
			for(Hero hero : myGameEngine.HeroList)
			{
				if(hero.name.equals(tabCommand[1]))
				{
					ishero = true;
					info = hero.info();
					break;
				}
			}
			boolean isitem = false;
			for(Item item : myGameEngine.HeroBag)
			{
				if(item.nameItem.equals(tabCommand[1]))
				{
					isitem = true;
					info = item.info();
					break;
				}
			}
			if(!isboss && !ishero && !isitem)
			{
				System.out.println(":unknown");
				return -1;
			}
			System.out.println(":"+info);
			
			return 5;
		}
		//case of unknown command
		return -2;
	}
}