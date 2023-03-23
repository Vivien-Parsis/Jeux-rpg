package game;

import java.util.Scanner;
import java.util.Stack;
import tool.Rand;
import java.util.ArrayList;
import java.util.Arrays;
/**
* Class that handle engine of the game
* @author VivienP
*/
public class GameEngine{
    private ArrayList<Item> HeroBag = new ArrayList<Item>();
    private Hero[] HeroTab;
    private int gold;
	final public Scanner command = new Scanner(System.in);	
    final private Dungeon GameDonjon;
    private Room CurrentRoom;
    private Stack<Room> LastRoom;
    
    public GameEngine(Hero[] heroTab, Dungeon GameDonjon, Room CurrentRoom, Scanner command) 
    {
        this.HeroTab = new Hero[3];
        if(heroTab.length!=3)
        {heroTab[0]=new Hero();heroTab[1]=new Hero();heroTab[2]=new Hero();}
        else
        {this.HeroTab = heroTab;}
        this.GameDonjon = GameDonjon;
        this.CurrentRoom = CurrentRoom;
        this.HeroBag = new ArrayList<Item>();
        this.LastRoom = new Stack<Room>();
        gold = 0;
    }

    /**
 	* Run the game
 	*/
    protected void Run()
    {
        this.iniative();
        System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));
        boolean win = false;
        boolean noAliveHero = false;
        while(true)
        {
			String resultcommand = Command.RunCommand(this);
        	if(resultcommand.equals("0"))
        	{break;}
        	if(resultcommand.equals("-2"))
        	{System.out.println(":unknown command !");}
            
            //combat mod
            if(resultcommand.equals("2"))
            {
                String resultFightCommand = "";
                boolean successfulleave = false;
                boolean winoncurrentboss = false;
                int remainStunRound = 0;
                int SpellCombatRes = 0;
                boolean enoughmana = true;
                while(true)
                {
                    this.iniative();
                    for(Hero currentHero : HeroTab)
                    {
                        //skip death hero
                        if(currentHero==null)
                        {continue;}
                        System.out.println(info(stringCurrentCombat(currentHero),stringCombatCommandList()));
                        while(true)
                        {
                            resultFightCommand = Command.RunCombatCommand(this, currentHero);
                            if(resultFightCommand.equals("-2"))
        	                {System.out.println(":unknown command !");}
                            
                            //spell case (?)
                            if(resultFightCommand.substring(0,1).equals("1"))
                            {
                                String[] tabResult = resultFightCommand.split(" ");
                                //spell case
                                if(tabResult[0].equals("1"))
                                {
                                    Spell currentSpell = currentHero.getHeroSpell()[Integer.parseInt(tabResult[1])];
                                    String spelltype = currentSpell.getSpellType();
                                    enoughmana = currentHero.getcurrentmana() >= currentSpell.getManacost();
                                    //spell heal case
                                    if(spelltype.equals(Spell.allSpellType[0]) && enoughmana)
                                    {
                                        System.out.println("using a heal spell");
                                        while(true)
                                        {
                                            System.out.println("which hero ?");
                                            System.out.print(">");
                                            String herotoheal = command.nextLine();
                                            if(this.getHero(herotoheal)!=null)
                                            {
                                                this.getHero(herotoheal).heal(currentSpell.getSpellValue());
                                                break;
                                            }
                                        }
                                        currentHero.removecurrentmana(currentSpell.getManacost());
                                    }
                                    //spell offensive case
                                    if(spelltype.equals(Spell.allSpellType[1]) && enoughmana)
                                    {
                                        System.out.println("using a offensive spell");
                                        this.hurtBoss(currentSpell.getSpellValue());
                                        currentHero.removecurrentmana(currentSpell.getManacost());
                                    }
                                    //spell defensive case
                                    if(spelltype.equals(Spell.allSpellType[2]) && enoughmana)
                                    {
                                        System.out.println("using a defensive spell");
                                        SpellCombatRes = currentSpell.getSpellValue();
                                        currentHero.removecurrentmana(currentSpell.getManacost());
                                    }
                                    //spell stun case
                                    if(spelltype.equals(Spell.allSpellType[3]) && enoughmana)
                                    {
                                        System.out.println("using a stun spell");
                                        remainStunRound = currentSpell.getSpellValue();
                                        currentHero.removecurrentmana(currentSpell.getManacost());
                                    }
                                    //case of not enough mana for casting choosen spell
                                    if(!enoughmana)
                                    {System.out.println(":Not enough mana to cast the spell !");}
                                }
                            }
                            //leave case
                            if(resultFightCommand.equals("3"))
                            {
                                //successfull leave (?)
                                successfulleave = Rand.randint(1, 3)==1;
                                System.out.println(":attempting to leave the combat");
                                //successfull leave
                                if(successfulleave)
                                {
                                    this.CurrentRoom.getRoomBoss().currentHP = this.CurrentRoom.getRoomBoss().maxHP;
                                    System.out.println(":successfull to leave");
                                }
                                //not successfull leave
                                else
                                {System.out.println(":fail to leave");}
                                break;
                            }
                            //case of successfull spell or weapon
                            if((enoughmana && (resultFightCommand.substring(0,1).equals("1")) || 
                            resultFightCommand.equals("2")))
                            {
                                //defeat on current boss case
                                if(this.CurrentRoom.getRoomBoss().currentHP<=0)
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
                        }
                        //boss attack here only if not stun
                        if(!winoncurrentboss && remainStunRound==0)
                        {this.hurtHero(this.CurrentRoom.getRoomBoss().getdamagePoint()-SpellCombatRes);}
                        //calculate remeain stun round of the boss
                        remainStunRound-=1;
                        if(remainStunRound<0)
                        {remainStunRound=0;}
                        SpellCombatRes = 0;

                        //check if an hero is dead
                        for(int i=0; i<HeroTab.length; i++)
                        {
                            if(HeroTab[i]==null)
                            {continue;}
                            if(HeroTab[i].currentHP<=0)
                            {HeroTab[i]=null;}
                        }
                        //check if all hero are dead
                        noAliveHero = true;
                        for(Hero hero : HeroTab)
                        {
                            if(hero!=null)
                            {
                                noAliveHero=false;
                                break;
                            }
                        }
                        //stop combat in case of successfull leave or win on boss or no alive hero
                        if((resultFightCommand.equals("3") && successfulleave) || winoncurrentboss || noAliveHero)
                        {break;}
                    }
                    //stop combat in case of successfull leave or win on boss or no alive hero
                    if((resultFightCommand.equals("3") && successfulleave) || winoncurrentboss || noAliveHero)
                    {break;}
                    for(Hero currentHero : HeroTab)
                    {
                        if(currentHero==null)
                        {continue;}
                        currentHero.addcurrentmana(currentHero.getcurrentmana()+currentHero.getmanaregen());
                        if(currentHero.getcurrentmana()>currentHero.getmaxmana())
                        {currentHero.setcurrentmana(currentHero.getmaxmana());}
                    }
                }
                //display info in case of leave of lose
                if(!winoncurrentboss)
                {System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));}
                //win (?)
                if(winoncurrentboss && !win)
                {
                    win = !this.GameDonjon.checkStillAliveBoss();
                    //win
                    if(win)
                    {
                        //this.HeroBag.add(this.GameDonjon.getDonjonLoot());
                        //this.GameDonjon.clearDonjonLoot();
                        System.out.println(":YOU WIN !\n");
                    }
                }
                //lose
                if(noAliveHero)
                {System.out.println(":You lose...");}
            }
            //end the game
            if(win || noAliveHero)
            {break;}
        }
        System.out.println("\n#########\ngame stop\n#########");
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
        damage-=HeroTab[AliveHero.get(choose)].getdefensePoint();
        if(damage<0)
        {damage=0;}
        HeroTab[AliveHero.get(choose)].hurtHero(damage);
    }
    /**
     * launch drop of the item of the current room
     */
    public void drop()
    {
        Item CurrentItem = this.CurrentRoom.getRoomItem();
        if(Rand.randint(1,101)<=CurrentItem.getchance())
        {
            HeroBag.add(CurrentItem);
            System.out.println(":drop a new item");
        }
    } 
    /**
    * calculate iniative between heros
    */
    public void iniative()
    {
        Arrays.sort(this.HeroTab);
        Hero[] reverse = new Hero[3];
        for(int i = 0; i<3; i++)
        {reverse[i]=this.HeroTab[2-i];}
        this.HeroTab = reverse;
    }
    /**
    * open closed rooms only in certain condition
    */
    public void checkCloseDoor()
    {
        //check for merchant room
        for(Item item : this.HeroBag)
        {
            if(item.toString().equals("key") && this.GameDonjon.getRoomHash().get("12").getExit("down")==null)
            {
                System.out.println(":Unlocked a new door !");
                this.GameDonjon.getRoomHash().get("12").setExit("down", this.GameDonjon.getRoomHash().get("cave"));
            }
        }
        //check for final room
        if(!this.GameDonjon.getRoomHash().get("11").hasExit("up"))
       {
            boolean cleanDungeonExceptFinal = false;
            for(Room room : GameDonjon.getRoomHash().values())
            {
                if(room.toString().equals("final"))
                {cleanDungeonExceptFinal=true;}
                if(cleanDungeonExceptFinal && room.hasBoss() && !room.toString().equals("final"))
                {
                    cleanDungeonExceptFinal=false;
                    break;
                }
            }
            if(cleanDungeonExceptFinal)
            {
                System.out.println(":Unlocked a new door !");
                this.GameDonjon.getRoomHash().get("11").setExit("up", this.GameDonjon.getRoomHash().get("final"));
            }
        }
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
				this.CurrentRoom.info()+
    			this.stringCharacter()+
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
            this.stringCharacter()+
            this.stringHeroList()+
            this.stringBag();
    }
    /**
 	* @return the alive hero list
 	*/
    public String stringHeroList()
    {
    	String herolist = "\n~~~~~heroes~~~~~\n";
    	for(Hero hero : HeroTab)
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
        if(HeroBag.size()==0)
        {return "\n~~~~~~bag~~~~~~~\n"+"gold:"+this.gold+"\nempty";}
        String bag =  "\n~~~~~~bag~~~~~~~\n"+"gold:"+this.gold+"\n";
        for(Item item : HeroBag)
        {bag += item + " ";}
        return bag.substring(0,bag.length()-1).replaceAll(" ",", ");
    }
    /**
 	*	@return all commands	
 	*/
	public String stringCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(String command : CommandList.commandHash.keySet())
        {
            if(!CurrentRoom.hasBoss() && command.equals("/attack"))
            list+=command+" "+CommandList.commandHash.get(command)[0];
        }
    	return list.replaceAll(" /", ", /");
    }
	/**
 	*	@return all commands of the combat phase	
 	*/
	public static String stringCombatCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(String command : CommandList.AttackcommandHash.keySet())
        {
            list+=command+" "+CommandList.AttackcommandHash.get(command)[0];
        }
    	return list.replaceAll(" /", ", /");
    }
    public String stringCharacter()
    {
        if(this.CurrentRoom.hasBoss())
        {return "\n~~~~~~boss~~~~~~\n"+this.CurrentRoom.getCharacter();}
        else if(this.CurrentRoom.hasMerchant())
        {return "\n~~~~Character~~~~\n"+this.CurrentRoom.getCharacter()+this.CurrentRoom.getRoomMerchant().info();}
        else
        {return "";}
    }
    
    public Dungeon getDonjon()
    {return this.GameDonjon;}

    public Stack<Room> getLastRoom()
    {return this.LastRoom;}
    public void pushLastRoom(Room room)
    {this.LastRoom.push(room);}
    public Room popLastRoom()
    {return this.LastRoom.pop();}

    public Hero[] getHeroTab()
    {return this.HeroTab;}

    public Room getCurrentRoom()
    {return this.CurrentRoom;}
    public void setCurrentRoom(Room newRoom)
    {this.CurrentRoom=newRoom;}

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
    /**
     * @param HeroBag the HeroBag to set
     */
    public void setHeroBag(ArrayList<Item> HeroBag)
    {this.HeroBag = HeroBag;}
    /**
     * @param HeroTab the HeroTab to set
     */
    public void setHeroTab(Hero[] HeroTab)
    {this.HeroTab = HeroTab;}
    
    /**
     * @param LastRoom the LastRoom to set
     */
    public void setLastRoom(Stack<Room> LastRoom) 
    {this.LastRoom = LastRoom;}

    public int getGold()
    {return this.gold;}

    public void addGold(int plus)
    {this.gold+=plus;}
}