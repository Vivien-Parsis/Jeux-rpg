package game;
/**
* Class that handle command
* @author VivienP
*/
public class Command implements CommandList {
	/**
 	*	read and return command output
 	*/
	private static String[] ReadCommand(GameEngine myGameEngine)
	{
		System.out.print(">");
    	String stringCommand = myGameEngine.command.nextLine().toLowerCase();
    	return stringCommand.split(" ");
	}
	/**
 	*	execute a command
 	*/
	public static String RunCommand(GameEngine myGameEngine)
    {
    	String[] tabCommand = ReadCommand(myGameEngine);
    	//quit case
    	if(tabCommand[0].equals(CommandList[0][0]))
        {
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+CommandList[0][0]+" "+CommandList[0][1]+"!");
        		return "-1";
			}
			return "0";
		}
		//info case
		if(tabCommand[0].equals(CommandList[3][0]))
        {
    		if(tabCommand.length>2)
			{
				System.out.println(":must be "+CommandList[3][0]+" "+CommandList[3][1]+"!");
				return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
				return "3";
			}
			String info = "";
			boolean isboss = false;
			if(myGameEngine.getCurrentRoom().getRoomBoss()!=null)
			{isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.getCurrentRoom().getRoomBoss().getname());}
			
			if(isboss)
			{info=myGameEngine.getCurrentRoom().getRoomBoss().info();}
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
				return "-1";
			}
			System.out.println(":"+info);
			return "3";
		}	
		//help case
		if(tabCommand[0].equals(CommandList[4][0]))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+CommandList[4][0]+" "+CommandList[4][1]+"!");
        		return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+CommandList[4][2]);
				return "4";
			}
			else
			{
				int index = -1;
				for(int i = 0; i<CommandList.length;i++)
				{
					if(tabCommand[1].equals(CommandList[i][0]) || tabCommand[1].equals(CommandList[i][0].substring(1)))
					{index=i;break;}
				}
				if(index==-1)
				{
					System.out.println(":must be "+CommandList[4][0]+" "+CommandList[4][1]+"!");
        			return "-1";
				}
				else
				{
					System.out.println(":"+CommandList[index][2]);
					return "4";
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
        		return "-1";
        	}
        	boolean knowdirection = Room.knownDirection(tabCommand[1]);
        	
        	//case of unknown possible direction
        	if(!knowdirection)
        	{
        		System.out.println(":no such direction");
        		return "-1";
        	}
        	//case of no exit
        	if(!myGameEngine.getCurrentRoom().hasExit(tabCommand[1]))
        	{
        		System.out.println(":can't go that way");
        		return "-1";
        	}
        	//case of a successful move
        	else
        	{
				if(!myGameEngine.getCurrentRoom().getRoomName().equals("gate"))
				{myGameEngine.pushLastRoom(myGameEngine.getCurrentRoom());}
        		myGameEngine.setCurrentRoom(myGameEngine.getCurrentRoom().getExit(tabCommand[1]));
        		System.out.println(":moving to "+tabCommand[1]+"\n");
        		System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
        		return "1";
        	}
        }
		//attack case
		if(tabCommand[0].equals(CommandList[2][0]))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+CommandList[2][0]+" "+CommandList[2][1]+"!");
        		return "-1";
			}
			if(myGameEngine.getCurrentRoom().hasBoss())
			{
				System.out.println(":launch attack on the current boss");
				return "2";
			}
			else
			{
				System.out.println(":no boss in here !");
				return "-1";
			}
		}
		//back case
		if(tabCommand[0].equals(CommandList[5][0]))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+CommandList[5][0]+" "+CommandList[5][1]+"!");
        		return "-1";
			}
			if(myGameEngine.getLastRoom()==null)
			{
				System.out.println(":no previous romm");
				return "-1";
			}
			if(myGameEngine.getLastRoom().isEmpty())
			{
				System.out.println(":no previous romm");
				return "-1";
			}
			else
			{
				System.out.println(":go back\n");
				myGameEngine.setCurrentRoom(myGameEngine.popLastRoom());
				System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
				return "5";
			}
		}
		//buy case
		if(tabCommand[0].equals(CommandList[7][0]))
		{
			if(!myGameEngine.getCurrentRoom().hasMerchant())
			{
				System.out.println(":No merchant here !");
        		return "-1";
			}
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+CommandList[7][0]+" "+CommandList[7][1]+"!");
        		return "-1";
			}
			if(myGameEngine.getCurrentRoom().getRoomMerchant().getOffer().size()==0)
			{
				System.out.println(":Merchant bag is empty");
        		return "-1";
			}
			int indexoffer = -1;
			for(int i = 0;  i<myGameEngine.getCurrentRoom().getRoomMerchant().getOffer().size(); i++)
			{
				if(tabCommand[1].equals(myGameEngine.getCurrentRoom().getRoomMerchant().getOffer().get(i).toString()))
				{
					indexoffer = i;
					break;
				}
			}
			if(indexoffer==-1)
			{
				System.out.println(":unknown item");
        		return "-1";
			}
			if(myGameEngine.getGold()<myGameEngine.getCurrentRoom().getRoomMerchant().getOffer().get(indexoffer).getgoldValue())
			{
				System.out.println(":not enought gold");
        		return "-1";
			}
			myGameEngine.addGold(-myGameEngine.getCurrentRoom().getRoomMerchant().getOffer().get(indexoffer).getgoldValue());
			myGameEngine.getHeroBag().add(myGameEngine.getCurrentRoom().getRoomMerchant().getOffer().get(indexoffer));
			myGameEngine.getCurrentRoom().getRoomMerchant().RemoveItem(indexoffer);
			System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
			System.out.println(":item bought successfully");
			return "7";
		}
		//sell case
		if(tabCommand[0].equals(CommandList[8][0]))
		{
			if(!myGameEngine.getCurrentRoom().hasMerchant())
			{
				System.out.println(":No merchant here !");
        		return "-1";
			}
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+CommandList[8][0]+" "+CommandList[8][1]+"!");
        		return "-1";
			}
			if(myGameEngine.getHeroBag().size()==0)
			{
				System.out.println(":Your bag is empty");
        		return "-1";
			}
			boolean knowitem = false;
			int index = -1;
			for(int i = 0; i<myGameEngine.getHeroBag().size(); i++)
			{
				if(tabCommand[1].equals(myGameEngine.getHeroBag().get(i).nameItem))
				{
					knowitem = true;
					index  = i;
					break;
				}
			}
			if(!knowitem)
			{
				System.out.println("Unknown item !");
        		return "-1";
			}
			if(myGameEngine.getHeroBag().get(index).getgoldValue()==0)
			{
				System.out.println(":untradeable item");
        		return "-1";
			}
			System.out.println(":item sell successfully");
			myGameEngine.addGold(myGameEngine.getHeroBag().get(index).getgoldValue());
			myGameEngine.getCurrentRoom().getRoomMerchant().AddOffer(myGameEngine.getHeroBag().get(index));
			myGameEngine.getHeroBag().remove(index);
			System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
			return "8";
		}
		//equip case
		if(tabCommand[0].equals(CommandList[6][0]))
		{
			if(tabCommand.length!=3)
			{
				System.out.println(":must be "+CommandList[6][0]+" "+CommandList[6][1]+"!");
        		return "-1";
			}
			if(myGameEngine.getHeroBag().size()==0)
			{
				System.out.println(":empty bag");
        		return "-1";
			}
			int indexBag = -1;
			for(int i =0; i<myGameEngine.getHeroBag().size();i++)
			{
				if(tabCommand[1].equals(myGameEngine.getHeroBag().get(i).toString()))
				{indexBag=i;break;}
			}
			int indexHero = -1;
			for(int i =0; i<myGameEngine.getHeroTab().length;i++)
			{
				if(myGameEngine.getHeroTab()[i]!=null)
				{
					if(tabCommand[2].equals(myGameEngine.getHeroTab()[i].getname()))
					{indexHero=i;break;}
				}
			}
			if(indexHero==-1 || indexBag==-1)
			{
				System.out.println(":unknown");
        		return "-1";
			}
			if(!(myGameEngine.getHeroBag().get(indexBag) instanceof Weapon))
			{
				System.out.println(":not a weapon");
        		return "-1";
			}
			if(!myGameEngine.getHeroTab()[indexHero].getWeaponType().equals(((Weapon) (myGameEngine.getHeroBag().get(indexBag))).getWeaponType()))
			{
				System.out.println(":incompatible weapon type");
        		return "-1";
			}
			Weapon switchweapon = (Weapon) myGameEngine.getHeroBag().remove(indexBag);
			myGameEngine.getHeroBag().add(myGameEngine.getHeroTab()[indexHero].getHeroWeapon());
			myGameEngine.getHeroTab()[indexHero].setWeapon(switchweapon);
			System.out.println(":successfully equip weapon");
			return "6";
		}
        //case of unknown command
        return "-2";
    }
	/**
 	*	execute a combat command	
 	*/
	public static String RunCombatCommand(GameEngine myGameEngine, Hero currentHero)
	{
		String[] tabCommand = ReadCommand(myGameEngine);
		//leave case
		if(tabCommand[0].equals(AttackCommand[3][0]))
		{
			if(tabCommand.length>1)
        	{
        		System.out.println(":must be "+AttackCommand[3][0]+" "+AttackCommand[3][1]+"!");
        		return "-1";
        	}
			return "3";
		}
		//help case
		if(tabCommand[0].equals(AttackCommand[0][0]))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+AttackCommand[0][0]+" "+AttackCommand[0][1]+"!");
        		return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+AttackCommand[0][2]);
				return "0";
			}
			else
			{
				int index = -1;
				for(int i = 0; i<AttackCommand.length;i++)
				{
					if(tabCommand[1].equals(AttackCommand[i][0]) || tabCommand[1].equals(AttackCommand[i][0].substring(1)))
					{index=i;break;}
				}
				if(index==-1)
				{
					System.out.println(":must be "+AttackCommand[0][0]+" "+AttackCommand[0][1]+"!");
        			return "-1";
				}
				else
				{
					System.out.println(":"+AttackCommand[index][2]);
					return "0";
				}
			}
		}
		//spell case
		if(tabCommand[0].equals(AttackCommand[1][0]))
		{
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+AttackCommand[1][0]+" "+AttackCommand[1][1]+"!");
        		return "-1";
			}
			String spell = "";
			if(!currentHero.knownSpell(tabCommand[1]))
			{
				System.out.println(":unknown spell");
				return "-1";
			}
			else
			{
				spell+="1 ";
				for(int i = 0; i<currentHero.getHeroSpell().length; i++)
				{
					if(tabCommand[1].equals(currentHero.getHeroSpell()[i].getSpellName()))
					{
						spell +=i;
						return spell;
					}
				}
			}
			return spell;
		}
		
		//weapon case
		if(tabCommand[0].equals(AttackCommand[2][0]))
		{
			if(tabCommand.length!=1)
			{
				System.out.println(":must be "+AttackCommand[2][0]+" "+AttackCommand[2][1]+"!");
        		return "-1";
			}
			System.out.println(":attacking with a weapon");
			myGameEngine.hurtBoss((currentHero.getHeroWeapon().getattackpoint()*currentHero.damagePoint)-myGameEngine.getCurrentRoom().getRoomBoss().defensePoint);
			return "2";
		}
		//info case
		if(tabCommand[0].equals(AttackCommand[4][0]))
		{
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+AttackCommand[4][0]+" "+AttackCommand[4][1]+"!");
        		return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(myGameEngine.info(myGameEngine.stringCurrentCombat(currentHero),GameEngine.stringCombatCommandList()));
				return "4";
			}
			String info = "";
			boolean isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.getCurrentRoom().getRoomBoss().name);
			if(isboss)
			{info=myGameEngine.getCurrentRoom().getRoomBoss().info();}
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
				if(item.nameItem.equals(tabCommand[21]))
				{
					isitem = true;
					info = item.info();
					break;
				}
			}
			if(!isboss && !ishero && !isitem)
			{
				System.out.println(":unknown");
				return "-1";
			}
			System.out.println(":"+info);
			return "4";
		}
		//case of unknown command
		return "-2";
	}
}