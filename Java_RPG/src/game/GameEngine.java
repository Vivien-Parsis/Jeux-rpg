package game;

import java.util.Scanner;
import java.util.Stack;
import tool.Rand;
import java.util.ArrayList;
/**
* Class that handle engine of the game
* @author VivienP
*/
public class GameEngine implements GameData{
    private ArrayList<Item> HeroBag = new ArrayList<Item>();
    private Hero[] HeroTab;
    private int gold;
	final public Scanner command = new Scanner(System.in);
    private Dungeon GameDungeon;
    private Room CurrentRoom;
    private Stack<String> LastRoom;
    
    
    public GameEngine(Hero[] heroTab, Dungeon GameDungeon, Room CurrentRoom, Scanner command) 
    {
        this.HeroTab = new Hero[3];
        if(heroTab.length!=3)
        {heroTab=new Hero[]{new Hero(),new Hero(),new Hero()};}
        else
        {this.HeroTab = heroTab;}
        this.GameDungeon = GameDungeon;
        this.CurrentRoom = CurrentRoom;
        this.HeroBag = new ArrayList<Item>();
        this.LastRoom = new Stack<String>();
        this.gold = 0;
    }
    /**
 	* Run the game
 	*/
    protected void Run()
    {
		System.out.println("\n############\nrunning game\n############\n");
        System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));
        this.Explore();
    }

    private void Explore()
    {
        boolean stopTheGame = false;
        while(true)
        {
            //execute a command for the explore phase
			String resultcommand = Command.RunCommand(this);
        	if(resultcommand.equals("/quit"))
        	{
                System.out.println("\n#########\ngame stop\n#########");
                break;
            }
        	if(resultcommand.equals("-2"))
        	{System.out.println(":unknown command !");}
            //use case
            if(resultcommand.split(" ")[0].equals("/use"))
            {
                String[] tabResult = resultcommand.split(" ");
                Item currentItem = this.HeroBag.get(Integer.parseInt(tabResult[1]));
                String currentItemType = ((UsableItem) currentItem).getItemType();
                if(currentItemType.equals("damage_potion"))
                {System.out.println(":cannot use this type of item in explore phase");}
                if((currentItemType.equals("heal_potion") || currentItemType.equals("mana_potion")) && tabResult[2].equals("boss"))
                {System.out.println(":cannot use this item on the boss");}
                if(currentItemType.equals("heal_potion") && tabResult[2].substring(0,4).equals("hero"))
                {
                    System.out.println(":using a healing potion");
                    this.HeroTab[Integer.parseInt(tabResult[2].substring(4))].heal(((UsableItem) this.HeroBag.get(Integer.parseInt(tabResult[1]))).getItemvalue());
                    this.HeroBag.remove(Integer.parseInt(tabResult[1]));
                    System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));
                }
                if(currentItemType.equals("mana_potion") && tabResult[2].substring(0,4).equals("hero"))
                {
                    System.out.println(":using a mana potion");
                    this.HeroTab[Integer.parseInt(tabResult[2].substring(4))].addcurrentmana(((UsableItem) this.HeroBag.get(Integer.parseInt(tabResult[1]))).getItemvalue());
                    this.HeroBag.remove(Integer.parseInt(tabResult[1]));
                    System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));
                }
            }
            //combat mod
            if(resultcommand.equals("/attack"))
            {stopTheGame = this.Combat();}
            if(stopTheGame)
            {
                System.out.println("\n#########\ngame stop\n#########");
                break;
            }
        }
    }
    /**
    * Launch combat phase
    */
    private boolean Combat()
    {
        String resultFightCommand = "";
        boolean successfulleave = false;
        boolean winoncurrentboss = false;
        //remain stun round for the boss
        int remainStunRound = 0;
        //temporary resistance of heroes grant by defensive spell
        int SpellCombatRes = 0;
        boolean useDamagePotion = false;
        boolean enoughmana = true;
        while(true)
        {
            for(Hero currentHero : this.HeroTab)
            {
                //skip death hero
                if(currentHero==null)
                {continue;}
                System.out.println(this.info(this.stringCurrentCombat(currentHero),this.stringCombatCommandList()));
                while(true)
                {
                    resultFightCommand = Command.RunCombatCommand(this, currentHero);
                    if(resultFightCommand.equals("-2"))
                    {System.out.println(":unknown command !");}
                    //spell case (?)
                    if(resultFightCommand.split(" ")[0].equals("/spell"))
                    {
                        String[] tabResult = resultFightCommand.split(" ");
                        Spell currentSpell = currentHero.getHeroSpell()[Integer.parseInt(tabResult[1])];
                        String spelltype = currentSpell.getSpellType();
                        enoughmana = currentHero.getcurrentmana() >= currentSpell.getManacost();
                        //case of not enough mana for casting choosen spell
                        if(!enoughmana)
                        {System.out.println(":Not enough mana to cast the spell !");}
                        //spell heal case
                        if(spelltype.equals(allSpellType[0]) && enoughmana)
                        {
                            System.out.println(":using a heal spell");
                            while(true)
                            {
                                System.out.println("which hero ?");
                                System.out.print(">");
                                String herotoheal = this.command.nextLine();
                                if(this.getHero(herotoheal)!=null)
                                {
                                    this.getHero(herotoheal).heal(currentSpell.getSpellValue());
                                    break;
                                }
                            }
                            currentHero.removecurrentmana(currentSpell.getManacost());
                        }
                        //spell offensive case
                        if(spelltype.equals(allSpellType[1]) && enoughmana)
                        {
                            System.out.println(":using a offensive spell");
                            this.hurtBoss(currentSpell.getSpellValue());
                            currentHero.removecurrentmana(currentSpell.getManacost());
                        }
                        //spell defensive case
                        if(spelltype.equals(allSpellType[2]) && enoughmana)
                        {
                            System.out.println(":using a defensive spell");
                            SpellCombatRes = currentSpell.getSpellValue();
                            currentHero.removecurrentmana(currentSpell.getManacost());
                        }
                        //spell stun case
                        if(spelltype.equals(allSpellType[3]) && enoughmana)
                        {
                            System.out.println(":using a stun spell");
                            remainStunRound = currentSpell.getSpellValue();
                            currentHero.removecurrentmana(currentSpell.getManacost());
                        }
                    }
                    //use case
                    if(resultFightCommand.split(" ")[0].equals("/use"))
                    {
                        String[] tabResult = resultFightCommand.split(" ");
                        Item currentItem = this.HeroBag.get(Integer.parseInt(tabResult[1]));
                        String currentItemType = ((UsableItem) currentItem).getItemType();
                        
                        if((currentItemType.equals("heal_potion") || currentItemType.equals("mana_potion")) && tabResult[2].equals("boss"))
                        {System.out.println(":cannot use this item on the boss");}
                        if(currentItemType.equals("heal_potion") && tabResult[2].substring(0,4).equals("hero"))
                        {
                            System.out.println(":using a healing potion");
                            this.HeroTab[Integer.parseInt(tabResult[2].substring(4))].heal(((UsableItem) this.HeroBag.get(Integer.parseInt(tabResult[1]))).getItemvalue());
                            this.HeroBag.remove(Integer.parseInt(tabResult[1]));
                        }
                        if(currentItemType.equals("mana_potion") && tabResult[2].substring(0,4).equals("hero"))
                        {
                            System.out.println(":using a mana potion");
                            this.HeroTab[Integer.parseInt(tabResult[2].substring(4))].addcurrentmana(((UsableItem) this.HeroBag.get(Integer.parseInt(tabResult[1]))).getItemvalue());
                            this.HeroBag.remove(Integer.parseInt(tabResult[1]));
                        }
                        if(currentItemType.equals("damage_potion") && tabResult[2].equals("boss"))
                        {
                            System.out.println(":using a damage potion");
                            this.CurrentRoom.getRoomBoss().hurtBoss(((UsableItem) this.HeroBag.get(Integer.parseInt(tabResult[1]))).getItemvalue());
                            this.HeroBag.remove(Integer.parseInt(tabResult[1]));
                            useDamagePotion = true;
                        }
                    }
                    //leave case
                    if(resultFightCommand.equals("/leave"))
                    {
                        successfulleave = leave();
                        break;
                    }
                    //case of successfull spell or weapon
                    if((enoughmana && (resultFightCommand.split(" ")[0].equals("/spell")) || resultFightCommand.equals("/weapon") || (useDamagePotion && resultFightCommand.split(" ")[0].equals("/use"))))
                    {
                        //defeat on current boss case
                        if(this.CurrentRoom.getRoomBoss().getcurrentHP()<=0)
                        {
                            winoncurrentboss = true;
                            this.CurrentRoom.kill();
                            System.out.println("win on the current boss !");
                            this.drop();
                            this.checkCloseDoor();
                            if(!winoncurrentboss)
                            {System.out.println("\n"+this.info(stringCurrentCombat(currentHero),stringCombatCommandList()));}
                            else
                            {System.out.println("\n"+this.info(this.stringCurrentSituation(),stringCommandList()));}
                        }
                        break;
                    }
                    enoughmana = true;
                    useDamagePotion = false;
                }
                //boss attack here only if not stun
                if(!winoncurrentboss && remainStunRound==0)
                {this.hurtHero(this.CurrentRoom.getRoomBoss().getdamagePoint()-SpellCombatRes);}
                
                //calculate remain stun round of the boss
                remainStunRound-=1;
                if(remainStunRound<0)
                {remainStunRound=0;}
                //reset temporary resistance of heroes
                SpellCombatRes=0;
                //check if an hero is dead
                for(int i=0; i<this.HeroTab.length; i++)
                {
                    if(this.HeroTab[i]==null)
                    {continue;}
                    if(this.HeroTab[i].getcurrentHP()<=0)
                    {this.HeroTab[i]=null;}
                }
                //stop combat in case of successfull leave or win on boss or no alive hero
                if((resultFightCommand.equals("/leave") && successfulleave) || winoncurrentboss || this.checkAllAliveHero())
                {break;}
            }
            //stop combat in case of successfull leave or win on boss or no alive hero
            if((resultFightCommand.equals("/leave") && successfulleave) || winoncurrentboss || this.checkAllAliveHero())
            {break;}
            
            this.RegenMana();
        }
        //display info in case of leave or lose
        if(!winoncurrentboss)
        {System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));}
        //win (?)
        boolean win = false;
        if(winoncurrentboss)
        {
            win = !this.GameDungeon.checkStillAliveBoss();
            //win
            if(win)
            {System.out.println(":YOU WIN !\n");}
        }
        //lose
        if(this.checkAllAliveHero())
        {System.out.println(":You lose...");}

        if(this.checkAllAliveHero() || win)
        {return true;}
        else
        {return false;}
    }
    /**
     * damage current boss
     */
    public void hurtBoss(int damage)
    {
        if(damage<0)
        {damage=0;}
        this.CurrentRoom.getRoomBoss().hurtBoss(damage);
    }
    /**
     * damage one hero, random choose
     */
    public void hurtHero(int damage)
    {
        ArrayList<Integer> AliveHero = new ArrayList<Integer>();
        for(int i = 0; i<this.HeroTab.length; i++)
        {
            if(this.HeroTab[i]!=null)
            {AliveHero.add(i);}
        }
        int choose = Rand.randint(0,AliveHero.size());
        damage-=this.HeroTab[AliveHero.get(choose)].getdefensePoint();
        if(damage<0)
        {damage=0;}
        this.HeroTab[AliveHero.get(choose)].hurtHero(damage);
    }
    /**
     * launch drop of the item of the current room
     */
    public void drop()
    {
        Item CurrentItem = this.CurrentRoom.getRoomItem();
        if(CurrentItem==null)
        {return;}
        if(Rand.randint(1,101)<=CurrentItem.getchance())
        {
            this.HeroBag.add(CurrentItem);
            System.out.println(":drop a new item");
        }
        //Drop final key
        if(GameDungeon.getFinalKey()!=null)
        {
            boolean cleanDungeonExceptFinal = true;
            for(Room room : this.GameDungeon.getRoomHash().values())
            {
                if(room.getRoomBoss()==null)
                {continue;}
                if(room.getRoomBoss().getisFinalBoss())
                {continue;}
                if(room.hasBoss())
                {
                    cleanDungeonExceptFinal=false;
                    break;
                }
            }
            if(cleanDungeonExceptFinal)
            {
                System.out.println(":drop a new item");
                this.HeroBag.add(this.GameDungeon.getFinalKey());
                GameDungeon.setFinalKey(null);
            }
        }
    } 
    /**
    * regenerate mana of all alive heroes
    */
    public void RegenMana()
    {
        for(Hero currentHero : this.HeroTab)
        {
            if(currentHero==null)
            {continue;}
            currentHero.addcurrentmana(currentHero.getcurrentmana()+currentHero.getmanaregen());
            if(currentHero.getcurrentmana()>currentHero.getmaxmana())
            {currentHero.setcurrentmana(currentHero.getmaxmana());}
        }
    }
    /**
    * return if leaving attemping is successfull or not
    */
    public boolean leave()
    {
        boolean leave = Rand.randint(1, 3)==1;
        System.out.println(":attempting to leave the combat");
        //successfull leave
        if(leave)
        {
            this.CurrentRoom.getRoomBoss().setcurrentHP(this.CurrentRoom.getRoomBoss().getmaxHP());
            System.out.println(":successfull to leave");
        }
        //not successfull leave
        else
        {System.out.println(":fail to leave");}
        return leave;
    }
    /**
    * open locked rooms
    */
    public void checkCloseDoor()
    {
        if(HeroBag.size()==0)
        {return;}
        for(Room lockedRoom : this.GameDungeon.getRoomHash().values())
        {
            if(lockedRoom instanceof LockedRoom)
            {
                if(((LockedRoom) lockedRoom).getKeyItem()==null)
                {continue;}
                for(Item item : this.HeroBag)
                {
                    if(((LockedRoom) lockedRoom).getKeyItem()==null)
                    {continue;}
                    if(item.equals(((LockedRoom) lockedRoom).getKeyItem()))
                    {
                        ((LockedRoom) lockedRoom).setKeyItem(null);
                        try{
                            this.GameDungeon.getRoomHash().get(((LockedRoom) lockedRoom).getExitName()).setExit(((LockedRoom) lockedRoom).getExitDirection(), this.GameDungeon.getKeyofRoomHash(lockedRoom));;
                            System.out.println(":unlocked a new room");
                        }catch (NullPointerException e){
                            System.out.println("!cannot asign exit for a locked room");
                        }finally{}
                    }
                }
            }
        }
    }
    /**
     * @return true if all heroes are dead, false if still remain alive hero
     */
    public boolean checkAllAliveHero()
    {
        //check if all hero are dead
        boolean noAliveHero = true;
        for(Hero hero : this.HeroTab)
        {
            if(hero!=null)
            {noAliveHero=false;break;}
        }
        return noAliveHero;
    }
    /**
 	* @return info of the current room of the player and the commands
 	*/
    public String info(String situation, String command)
    {return ("\n" + situation + "\n" + command + "\n").replaceAll("null", "none");}
    /**
 	* @return info of the current room of the player
 	*/
    public String stringCurrentSituation()
    {
    	return
				this.CurrentRoom.info()+this.stringCurrentExit()+
    			this.stringRoomPerson()+
    			this.stringHeroList()+
                this.stringBag();
    }
    /**
 	* @return info of the current combat of the player
 	*/
    public String stringCurrentCombat(Hero currenthero)
    {
        return 
            "\n~~current hero~~\n"+
            currenthero.info()+
            this.stringRoomPerson()+
            this.stringHeroList()+
            this.stringBag();
    }
    /**
 	*	@return all exits of the room
 	*/
	public String stringCurrentExit()
	{	
		String exit = "\n~~~~~~exit~~~~~~\n";
		for(String direction : DirectionList)
		{
			if(CurrentRoom.getHashExit().get(direction)==null)
			{continue;}
			exit += direction+ ":"+this.GameDungeon.getRoomHash().get(CurrentRoom.getHashExit().get(direction)).getRoomName()+"\n";
		}
		if(exit.equals(""))
		{return exit;}
		return exit.substring(0,exit.length()-1).replaceAll("null", "no exit");
	}
    /**
 	* @return the alive hero list
 	*/
    public String stringHeroList()
    {
    	String herolist = "\n~~~~~heroes~~~~~\n";
    	for(Hero hero : this.HeroTab)
    	{
            if(hero!=null)
            {herolist += hero + " \n";}
        }
        if(herolist.equals("\n~~~~~heroes~~~~~\n"))
        {return herolist.concat("none");}
    	return herolist.substring(0,herolist.length()-1);
    }
    /**
 	* @return all item of the bag
 	*/
    public String stringBag()
    {
        if(this.HeroBag.size()==0)
        {return "\n~~~~~~bag~~~~~~~\n"+"gold:"+this.gold+"\nempty";}
        String bag =  "\n~~~~~~bag~~~~~~~\n"+"gold:"+this.gold+"\n";
        for(Item item : this.HeroBag)
        {bag += item + " ";}
        return bag.substring(0,bag.length()-1).replaceAll(" ",", ");
    }
    /**
 	* @return all commands	
 	*/
	public String stringCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(String command : CommandList.getcommandHash().keySet())
        {
            if((!CurrentRoom.hasBoss() && command.equals("/attack")) ||
            !CurrentRoom.hasMerchant() && (command.equals("/sell") || command.equals("/buy")))
            {continue;}
            list+=command+" "+CommandList.getcommandHash().get(command)[0];
        }
    	return list.replaceAll(" /", ", /");
    }
	/**
 	*	@return all commands of the combat phase	
 	*/
	public String stringCombatCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(String command : CommandList.getAttackcommandHash().keySet())
        {
            list+=command+" "+CommandList.getAttackcommandHash().get(command)[0];
        }
    	return list.replaceAll(" /", ", /");
    }
    public String stringRoomPerson()
    {
        if(this.CurrentRoom.hasBoss())
        {return "\n~~~~~~boss~~~~~~\n"+this.CurrentRoom.getRoomPerson();}
        else if(this.CurrentRoom.hasMerchant())
        {return "\n~~~~~Person~~~~~\n"+this.CurrentRoom.getRoomPerson().getname()+this.CurrentRoom.getRoomMerchant().info();}
        else
        {return "";}
    }
    
    public Dungeon getDungeon()
    {return this.GameDungeon;}

    public Stack<String> getLastRoom()
    {return this.LastRoom;}
    public void setLastRoom(Stack<String> lastRoom)
    {this.LastRoom = lastRoom;}
    public void pushLastRoom(String RoomHashKey)
    {this.LastRoom.push(RoomHashKey);}
    public String popLastRoom()
    {return this.LastRoom.pop();}

    public Hero[] getHeroTab()
    {return this.HeroTab;}

    public Room getCurrentRoom()
    {return this.CurrentRoom;}
    public void setCurrentRoom(Room newRoom)
    {this.CurrentRoom=newRoom;}
    
    public int getGold()
    {return this.gold;}

    public ArrayList<Item> getHeroBag()
    {return this.HeroBag;}

    public Hero getHero(final String name)
    {
        Hero hero = null;
        for(Hero e : HeroTab)
        {
            if(e != null)
            {
                if(name.equals(e.getname()))
                {
                    hero = e;
                    break;
                }
            }
        }
        return hero;
    }
    
    public void addGold(int plus)
    {this.gold+=plus;}

    public void setHeroBag(ArrayList<Item> heroBag) 
    {HeroBag = heroBag;}

}