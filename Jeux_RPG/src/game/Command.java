package game;
/**
*	Class that handle command
*/
public class Command implements CommandList{
	/**
 	*	read and return command output
 	*/
	private static String[] ReadCommand(GameEngine myGameEngine)
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
			if(myGameEngine.getCurrentRoom().getRoomBoss()!=null)
			{
				isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.getCurrentRoom().getRoomBoss().name);
			}
			
			if(isboss)
			{
				info=myGameEngine.getCurrentRoom().getRoomBoss().info();
			}
			boolean ishero = false;
			for(Hero hero : myGameEngine.getHeroTab())
			{
				if(hero==null)
				{continue;}
				if(hero.name.equals(tabCommand[1]))
				{
					ishero = true;
					info = hero.info();
					break;
				}
			}
			boolean isitem = false;
			for(Item item : myGameEngine.getHeroBag())
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
        	if(!myGameEngine.getCurrentRoom().hasExit(tabCommand[1]))
        	{
        		System.out.println(":can't go that way");
        		return -1;
        	}
        	//case of a successful move
        	else
        	{
				if(!myGameEngine.getCurrentRoom().getRoomName().equals("gate"))
				{
					myGameEngine.pushLastRoom(myGameEngine.getCurrentRoom());
				}
        		myGameEngine.setCurrentRoom(myGameEngine.getCurrentRoom().getExit(tabCommand[1]));
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
			if(myGameEngine.getCurrentRoom().hasBoss())
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
			if(myGameEngine.getLastRoom()==null)
			{
				System.out.println(":no previous romm");
				return -1;
			}
			if(myGameEngine.getLastRoom().isEmpty())
			{
				System.out.println(":no previous romm");
				return -1;
			}
			else
			{
				System.out.println(":go back\n");
				myGameEngine.setCurrentRoom(myGameEngine.popLastRoom());
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
			if(!currentHero.knownSpell(tabCommand[1]))
			{
				System.out.println(":unknown spell");
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
			myGameEngine.hurtBoss((currentHero.getHeroWeapon().getattackpoint()*currentHero.damagePoint)-myGameEngine.getCurrentRoom().getRoomBoss().defensePoint);
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
			boolean isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.getCurrentRoom().getRoomBoss().name);
			if(isboss)
			{
				info=myGameEngine.getCurrentRoom().getRoomBoss().info();
			}
			boolean ishero = false;
			for(Hero hero : myGameEngine.getHeroTab())
			{
				if(hero==null)
				{continue;}
				if(hero.name.equals(tabCommand[1]))
				{
					ishero = true;
					info = hero.info();
					break;
				}
			}
			boolean isitem = false;
			for(Item item : myGameEngine.getHeroBag())
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