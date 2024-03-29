package game;
/**
* Class that handle command for game engine. List of command are define inside CommandList.java
* @author VivienP
*/
public abstract class Command {
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
	protected static String RunCommand(GameEngine myGameEngine)
    {
    	String[] tabCommand = ReadCommand(myGameEngine);
		String[] infoCommand;
    	
		if(CommandList.knownCommand(tabCommand[0]))
		{infoCommand = CommandList.getcommandHash().get(tabCommand[0]);}
		else
		{return "-2";}

		//quit case
    	if(tabCommand[0].equals("/quit"))
        {
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
        		return "-1";
			}
			return "/quit";
		}
		//info case
		if(tabCommand[0].equals("/info"))
        {
    		if(tabCommand.length>2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
				return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
				return "/info";
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
				if(hero.getname().equals(tabCommand[1]))
				{
					ishero = true;
					info = hero.info();
					break;
				}
			}
			boolean isitem = false;
			for(Item item : myGameEngine.getHeroBag())
			{
				if(item.toString().equals(tabCommand[1]))
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
			return "/info";
		}	
		//help case
		if(tabCommand[0].equals("/help"))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
        		return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+infoCommand[1]);
				return "/help";
			}
			else
			{
				String infoHelp = "";
				for(String command : CommandList.getcommandHash().keySet())
				{
					if(tabCommand[1].equals(command) || tabCommand[1].equals(command.substring(1)))
					{
						infoHelp = CommandList.getcommandHash().get(command)[1];
						break;
					}
				}
				if(infoHelp.equals(""))
				{
					System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
        			return "-1";
				}
				else
				{
					System.out.println(":"+infoHelp);
					return "/help";
				}
			}
		}
        //move case
        if(tabCommand[0].equals("/move"))
        {
        	//case of wrong format
        	if(tabCommand.length!=2)
        	{
        		System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
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
				if(myGameEngine.getCurrentRoom().getcanGoBack())
				{myGameEngine.pushLastRoom(myGameEngine.getDungeon().getKeyofRoomHash(myGameEngine.getCurrentRoom()));}
				else
				{myGameEngine.getLastRoom().clear();}
        		myGameEngine.setCurrentRoom(myGameEngine.getDungeon().getRoomHash().get(myGameEngine.getCurrentRoom().getExit(tabCommand[1])));
        		System.out.println(":moving "+tabCommand[1]+"\n");
        		System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
        		return "/move";
        	}
        }
		//attack case
		if(tabCommand[0].equals("/attack"))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
        		return "-1";
			}
			if(myGameEngine.getCurrentRoom().hasBoss())
			{
				System.out.println(":launch attack on the current boss");
				return "/attack";
			}
			else
			{
				System.out.println(":no boss in here !");
				return "-1";
			}
		}
		//show case
		if(tabCommand[0].equals("/show"))
		{
			if(tabCommand.length!=1)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
        		return "-1";
			}
			else
			{
				System.out.println(myGameEngine.stringCommandList()+"\n");
				return "/show";
			}
		}
		//back case
		if(tabCommand[0].equals("/back"))
		{
			if(tabCommand.length>1)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
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
				myGameEngine.setCurrentRoom(myGameEngine.getDungeon().getRoomHash().get(myGameEngine.popLastRoom()));
				System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
				return "/back";
			}
		}
		//buy case
		if(tabCommand[0].equals("/buy"))
		{
			if(!myGameEngine.getCurrentRoom().hasMerchant())
			{
				System.out.println(":No merchant here !");
        		return "-1";
			}
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
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
			myGameEngine.getCurrentRoom().getRoomMerchant().RemoveaOffer(indexoffer);
			System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
			System.out.println(":item bought successfully");
			return "/buy";
		}
		//sell case
		if(tabCommand[0].equals("/sell"))
		{
			if(!myGameEngine.getCurrentRoom().hasMerchant())
			{
				System.out.println(":No merchant here !");
        		return "-1";
			}
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
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
				if(tabCommand[1].equals(myGameEngine.getHeroBag().get(i).toString()))
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
			myGameEngine.getCurrentRoom().getRoomMerchant().AddaOffer(myGameEngine.getHeroBag().get(index));
			myGameEngine.getHeroBag().remove(index);
			System.out.println(myGameEngine.info(myGameEngine.stringCurrentSituation(),myGameEngine.stringCommandList()));
			return "/sell";
		}
		//equip case
		if(tabCommand[0].equals("/equip"))
		{
			if(tabCommand.length!=3)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
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
			return "/equip";
		}
		//use case
		if(tabCommand[0].equals("/use"))
		{
			if(tabCommand.length!=3)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
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
			if(indexBag==-1)
			{
				System.out.println(":unknown item");
        		return "-1";
			}
			if(!(myGameEngine.getHeroBag().get(indexBag) instanceof UsableItem))
			{
				System.out.println(":unusable item");
        		return "-1";
			}
			String indextarget = "-1";
			for(int i =0; i<myGameEngine.getHeroTab().length;i++)
			{
				if(myGameEngine.getHeroTab()[i]!=null)
				{if(tabCommand[2].equals(myGameEngine.getHeroTab()[i].getname()))
					{
						indextarget ="hero"+i;
						break;
					}
				}
			}
			if(myGameEngine.getCurrentRoom().getRoomBoss()!=null)
			{
				if(indextarget.equals("-1")&&(tabCommand[2].equals(myGameEngine.getCurrentRoom().getRoomBoss().getname())||tabCommand[2].equals("boss")))
				{
					indextarget="boss";
				}
			}
			if(indextarget.equals("-1"))
			{System.out.println(":unknown target");}
			return "/use "+indexBag+" "+indextarget;
		}
		//save case
		if(tabCommand[0].equals("/save"))
		{
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoCommand[0]+"!");
        		return "-1";
			}
			if(!(tabCommand[1].equals("1")||tabCommand[1].equals("2")||tabCommand[1].equals("3")))
			{
				System.out.println(":unknown save number");
				return "-1";
			}
			boolean wantToSave = false;
			while(true)
			{
				System.out.println(":do you want to overwrite the choosen save file ? (y/n)");
				System.out.print(">");
    			String stringCommand = myGameEngine.command.nextLine().toLowerCase();
				if(stringCommand.equals("y"))
				{wantToSave=true;break;}
				if(stringCommand.equals("n"))
				{break;}
			}
			if(wantToSave)
			{
				Save.CreateSave(myGameEngine, Integer.parseInt(tabCommand[1]));
			}
			return "/save";
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
		String[] infoAttackCommand;
		if(CommandList.knownAttackCommand(tabCommand[0]))
		{infoAttackCommand = CommandList.getAttackcommandHash().get(tabCommand[0]);}
		else
		{return "-2";}
		//leave case
		if(tabCommand[0].equals("/leave"))
		{
			if(tabCommand.length>1)
        	{
        		System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
        		return "-1";
        	}
			return "/leave";
		}
		//help case
		if(tabCommand[0].equals("/help"))
        {
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
        		return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(":"+infoAttackCommand[1]);
				return "/help";
			}
			else
			{
				String infoHelp = "";
				
				for(String command : CommandList.getAttackcommandHash().keySet())
				{
					if(tabCommand[1].equals(command) || tabCommand[1].equals(command.substring(1)))
					{
						infoHelp = CommandList.getAttackcommandHash().get(command)[1];
						break;
					}
				}
				if(infoHelp.equals(""))
				{
					System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
        			return "-1";
				}
				else
				{
					System.out.println(":"+infoHelp);
					return "/help";
				}
			}
		}
		//spell case
		if(tabCommand[0].equals("/spell"))
		{
			if(tabCommand.length!=2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
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
				spell+="/spell ";
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
		if(tabCommand[0].equals("/weapon"))
		{
			if(tabCommand.length!=1)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
        		return "-1";
			}
			System.out.println(":attacking with a weapon");
			myGameEngine.hurtBoss((currentHero.getHeroWeapon().getattackpoint()*currentHero.getdamagePoint())-myGameEngine.getCurrentRoom().getRoomBoss().getdefensePoint());
			return "/weapon";
		}
		//info case
		if(tabCommand[0].equals("/info"))
		{
			if(tabCommand.length>2)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
        		return "-1";
			}
			if(tabCommand.length==1)
			{
				System.out.println(myGameEngine.info(myGameEngine.stringCurrentCombat(currentHero),myGameEngine.stringCombatCommandList()));
				return "/info";
			}
			String info = "";
			boolean isboss = tabCommand[1].equals("boss") || tabCommand[1].equals(myGameEngine.getCurrentRoom().getRoomBoss().getname());
			if(isboss)
			{info=myGameEngine.getCurrentRoom().getRoomBoss().info();}
			boolean ishero = false;
			for(Hero hero : myGameEngine.getHeroTab())
			{
				if(hero==null)
				{continue;}
				if(hero.getname().equals(tabCommand[1]))
				{
					ishero = true;
					info = hero.info();
					break;
				}
			}
			boolean isitem = false;
			for(Item item : myGameEngine.getHeroBag())
			{
				if(item.toString().equals(tabCommand[1]))
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
			return "/info";
		}
		//show case
		if(tabCommand[0].equals("/show"))
		{
			if(tabCommand.length!=1)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
        		return "-1";
			}
			else
			{
				System.out.println(myGameEngine.stringCombatCommandList()+"\n");
				return "/show";
			}
		}
		//use case
		if(tabCommand[0].equals("/use"))
		{
			if(tabCommand.length!=3)
			{
				System.out.println(":must be "+tabCommand[0]+" "+infoAttackCommand[0]+"!");
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
			if(indexBag==-1)
			{
				System.out.println(":unknown item");
        		return "-1";
			}
			if(!(myGameEngine.getHeroBag().get(indexBag) instanceof UsableItem))
			{
				System.out.println(":unusable item");
        		return "-1";
			}
			String indextarget = "-1";
			for(int i =0; i<myGameEngine.getHeroTab().length;i++)
			{
				if(myGameEngine.getHeroTab()[i]!=null)
				{if(tabCommand[2].equals(myGameEngine.getHeroTab()[i].getname()))
					{
						indextarget ="hero"+i;
						break;
					}
				}
			}
			if(myGameEngine.getCurrentRoom().getRoomBoss()!=null)
			{
				if(indextarget.equals("-1")&&(tabCommand[2].equals(myGameEngine.getCurrentRoom().getRoomBoss().getname())||tabCommand[2].equals("boss")))
				{
					indextarget="boss";
				}
			}

			if(indextarget.equals("-1"))
			{System.out.println(":unknown target");}
			
			return "/use "+indexBag+" "+indextarget;
		}
		//case of unknown command
		return "-2";
	}
}