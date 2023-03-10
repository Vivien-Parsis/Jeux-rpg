package jeux_RPG;

public class Command {
    public static String stringCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(int i = 0; i<Game.CommandList.length;i++)
    	{list += Game.CommandList[i][0]+" "+Game.CommandList[i][1];}
    	return list;
    }

	public static String stringCombatCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(int i = 0; i<Game.AttackCommand.length;i++)
    	{list += Game.AttackCommand[i][0]+" "+Game.AttackCommand[i][1];}
    	return list;
    }

	public static int RunCommand(GameEngine mygame)
    {
        System.out.print(">");
    	String stringCommand = mygame.command.nextLine();
    	String[] tabCommand = stringCommand.split(" ");
        
    	//quit case
    	if(tabCommand[0].equals(Game.CommandList[0][0]))
        {
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+Game.CommandList[0][0]+" "+Game.CommandList[0][1]+"!");
        		return -1;
			}
			return 0;
		}
    	
    	//info case
    	if(tabCommand[0].equals(Game.CommandList[3][0]))
        {
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+Game.CommandList[3][0]+" "+Game.CommandList[3][1]+"!");
        		return -1;
			}
    		System.out.println(mygame.info());
    		return 3;
    	}
    	
		//help case
		if(tabCommand[0].equals(Game.CommandList[4][0]))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+Game.CommandList[4][0]+" "+Game.CommandList[4][1]+"!");
        		return -1;
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+Game.CommandList[4][2]);
				return 4;
			}
			else
			{
				boolean knowncommand = false;
				int index = 0;
				for(int i = 0; i<Game.CommandList.length;i++)
				{
					if(tabCommand[1].equals(Game.CommandList[i][0]) || tabCommand[1].equals(Game.CommandList[i][0].substring(1)))
					{index=i;knowncommand=true;break;}
				}
				if(!knowncommand)
				{
					System.out.println(":must be "+Game.CommandList[4][0]+" "+Game.CommandList[4][1]+"!");
        			return -1;
				}
				else
				{
					System.out.println(":"+Game.CommandList[index][2]);
					return 4;
				}
			}
		}
        //move case
        if(tabCommand[0].equals(Game.CommandList[1][0]))
        {
        	//case of wrong format
        	if(tabCommand.length!=2)
        	{
        		System.out.println(":must be "+Game.CommandList[1][0]+" "+Game.CommandList[1][1]+"!");
        		return -1;
        	}
        	boolean knowdirection = false;
        	for(int i = 0; i<Game.DirectionList.length;i++)
        	{
        		if(tabCommand[1].equals(Game.DirectionList[i]))
        		{
                    knowdirection = true;
                    break;
                }
        	}
        	//case of unknown possible direction
        	if(!knowdirection)
        	{
        		System.out.println(":no such direction");
        		return -1;
        	}
        	//case of no exit
        	if(!mygame.CurrentRoom.hasExit(tabCommand[1]))
        	{
        		System.out.println(":can't go that way");
        		return -1;
        	}
        	//case of a successful move
        	else
        	{
				mygame.LastRoom.push(mygame.CurrentRoom);
        		mygame.CurrentRoom = mygame.CurrentRoom.getExit(tabCommand[1]);
        		System.out.println(":moving to "+tabCommand[1]+"\n");
        		System.out.println(mygame.info());
        		return 1;
        	}
        }
		//attack case
		if(tabCommand[0].equals(Game.CommandList[2][0]))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+Game.CommandList[2][0]+" "+Game.CommandList[2][1]+"!");
        		return -1;
			}
			if(mygame.CurrentRoom.hasBoss())
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
		if(tabCommand[0].equals(Game.CommandList[5][0]))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+Game.CommandList[5][0]+" "+Game.CommandList[5][1]+"!");
        		return -1;
			}
			if(mygame.LastRoom==null)
			{
				System.out.println(":no previous romm");
				return -1;
			}
			if(mygame.LastRoom.isEmpty())
			{
				System.out.println(":no previous romm");
				return -1;
			}
			else
			{
				System.out.println(":go back\n");
				mygame.CurrentRoom = mygame.LastRoom.pop();
				System.out.println(mygame.info());
				return 5;
			}
		}
        //case of unknown command
        return -2;
    }

	public static int RunCombatCommand(GameEngine mygame)
	{
		System.out.print(">");
    	String stringCommand = mygame.command.nextLine();
    	String[] tabCommand = stringCommand.split(" ");
		//leave case
		if(tabCommand[0].equals(Game.AttackCommand[4][0]))
		{
			if(tabCommand.length>1)
        	{
        		System.out.println(":must be "+Game.AttackCommand[4][0]+" "+Game.AttackCommand[4][1]+"!");
        		return -1;
        	}
			return 4;
		}
		//help case
		if(tabCommand[0].equals(Game.AttackCommand[0][0]))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+Game.AttackCommand[0][0]+" "+Game.AttackCommand[0][1]+"!");
        		return -1;
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+Game.AttackCommand[0][2]);
				return 0;
			}
			else
			{
				boolean knowncommand = false;
				int index = 0;
				for(int i = 0; i<Game.AttackCommand.length;i++)
				{
					if(tabCommand[1].equals(Game.AttackCommand[i][0]) || tabCommand[1].equals(Game.AttackCommand[i][0].substring(1)))
					{index=i;knowncommand=true;break;}
				}
				if(!knowncommand)
				{
					System.out.println(":must be "+Game.AttackCommand[0][0]+" "+Game.AttackCommand[0][1]+"!");
        			return -1;
				}
				else
				{
					System.out.println(":"+Game.AttackCommand[index][2]);
					return 0;
				}
			}
		}
		return -2;
	}
}