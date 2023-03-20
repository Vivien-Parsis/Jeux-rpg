package game;

import java.util.Scanner;
import java.util.Stack;
import tool.Rand;
import java.util.ArrayList;
/**
* Class that handle engine of the game
* @author VivienP
*/
public class GameEngine implements CommandList{
    private ArrayList<Item> HeroBag = new ArrayList<Item>();
    private Hero[] HeroTab; 
	final public Scanner command = new Scanner(System.in);	
    final private Donjon GameDonjon;
    private Room CurrentRoom;
    final private int HeroMaxWeight;
    private int HeroCurrentWeight;
    private Stack<Room> LastRoom;
    
    public GameEngine(Hero[] heroTab, Donjon GameDonjon, Room CurrentRoom, int HeroMaxWeight, int HeroCurrentWeight, Scanner command) 
    {
        this.HeroTab = new Hero[3];
        if(heroTab.length!=3)
        {heroTab[0]=new Hero();heroTab[1]=new Hero();heroTab[2]=new Hero();}
        else
        {this.HeroTab = heroTab;}
        this.GameDonjon = GameDonjon;
        this.CurrentRoom = CurrentRoom;
        this.HeroCurrentWeight = HeroCurrentWeight;
        this.HeroMaxWeight = HeroMaxWeight;
        this.HeroBag = new ArrayList<Item>();
        this.LastRoom = new Stack<Room>();
    }

    /**
 	* Run the game
 	*/
    protected void RunGame()
    {
        this.calculateCurrentWeight();
        System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));
        boolean win = false;
        boolean noAliveHero = false;
        while(true)
        {
            this.calculateCurrentWeight();
			String resultcommand = Command.RunCommand(this);
        	if(resultcommand.equals("0"))
        	{
        		break;
        	}
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
                    for(Hero currentHero : HeroTab)
                    {
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
                                    if(!enoughmana)
                                    {
                                        System.out.println(":Not enough mana to cast the spell !");
                                    }
                                }
                            }
                            if(resultFightCommand.equals("3"))
                            {
                                successfulleave = Rand.randint(1, 3)==1;
                                System.out.println(":attempting to leave the combat");
                                if(successfulleave)
                                {
                                    this.CurrentRoom.getRoomBoss().currentHP = this.CurrentRoom.getRoomBoss().maxHP;
                                    System.out.println(":successfull to leave");
                                }
                                else
                                {
                                    System.out.println(":fail to leave");
                                }
                                break;
                            }
                            if(enoughmana && (resultFightCommand.substring(0,1).equals("1") || 
                            resultFightCommand.equals("2") || 
                            resultFightCommand.equals("3")))
                            {
                                if(this.CurrentRoom.getRoomBoss().currentHP<=0)
                                {
                                    winoncurrentboss = true;
                                    this.CurrentRoom.killRoomBoss();
                                    System.out.println("win on the current boss !");
                                    
                                    this.drop();
                                    
                                    if(!winoncurrentboss)
                                    {System.out.println("\n"+this.info(stringCurrentCombat(currentHero),stringCombatCommandList()));}
                                    else
                                    {System.out.println("\n"+this.info(this.stringCurrentSituation(),stringCommandList()));}
                                }
                                break;
                            }
                            enoughmana = true;
                        }
                        //boss attack here
                        if(!winoncurrentboss && remainStunRound==0)
                        {
                            this.hurtHero(this.CurrentRoom.getRoomBoss().getdamagePoint()-SpellCombatRes);
                        }
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
                        
                        noAliveHero = true;
                        for(Hero hero : HeroTab)
                        {
                            if(hero!=null)
                            {
                                noAliveHero=false;
                                break;
                            }
                        }
                        if((resultFightCommand.equals("3") && successfulleave) || winoncurrentboss || noAliveHero)
                        {break;}
                    }
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
                if(!winoncurrentboss)
                {System.out.println(this.info(this.stringCurrentSituation(),stringCommandList()));}
                if(winoncurrentboss && !win)
                {
                    win = !this.GameDonjon.checkStillAliveBoss();
                    if(win)
                    {
                        //this.HeroBag.add(this.GameDonjon.getDonjonLoot());
                        //this.GameDonjon.clearDonjonLoot();
                        System.out.println(":YOU WIN !\n");
                    }
                }
                if(noAliveHero)
                {System.out.println(":You lose...");}
            }
            if(win || noAliveHero)
            {break;}
        }
        System.out.println("\n#########\ngame stop\n#########");
    }

    public void hurtBoss(int damage)
    {
        if(damage<0)
        {damage=0;}
        this.CurrentRoom.getRoomBoss().hurtBoss(damage);
    }
    public void hurtHero(int damage)
    {
        ArrayList<Integer> AliveHero = new ArrayList<Integer>();
        for(int i = 0; i<this.HeroTab.length; i++)
        {
            if(this.HeroTab[i]!=null)
            {
                AliveHero.add(i);
            }
        }
        int choose = Rand.randint(0,AliveHero.size());
        damage-=HeroTab[AliveHero.get(choose)].getdefensePoint();
        if(damage<0)
        {damage=0;}
        HeroTab[AliveHero.get(choose)].hurtHero(damage);
    }

    public void drop()
    {
        Item CurrentItem = this.CurrentRoom.getRoomItem();
        if(Rand.randint(1,101)<=CurrentItem.getchance())
        {
            HeroBag.add(CurrentItem);
        }
        else
        {
            HeroBag.add(new Item());
        }
        this.calculateCurrentWeight();
        System.out.println(":drop a new item");
    }   
    /**
 	* Return info of the current room of the player and the commands
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
    			"\n~~~~~~boss~~~~~~\n"+
    			this.CurrentRoom.getRoomBoss()+
    			"\n~~~~~heroes~~~~~\n"+
    			this.stringHeroList()+
                "\n~~~~~~bag~~~~~~~\n"+
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
            "\n~~~~~~boss~~~~~~\n"+
            this.CurrentRoom.getRoomBoss()+
            "\n~~~~~heroes~~~~~\n"+
            this.stringHeroList()+
            "\n~~~~~~bag~~~~~~~\n"+
            this.stringBag();
    }
    /**
 	* @return the alive hero list
 	*/
    public String stringHeroList()
    {
    	String herolist = "";
    	for(Hero hero : HeroTab)
    	{
            if(hero!=null)
            {herolist += hero + " \n";}
        }
        if(herolist=="")
        {return herolist;}
    	return herolist.substring(0,herolist.length()-1);
    }
    /**
 	* @return all item of the bag
 	*/
    public String stringBag()
    {
        if(HeroBag.size()==0)
        {return "empty";}
        String bag = "weight:"+HeroCurrentWeight+"/"+HeroMaxWeight+"\n";
        for(Item item : HeroBag)
        {bag += item + " ";}
        return bag.substring(0,bag.length()-1).replaceAll(" ",", ");
    }
    /**
 	*	@return all commands	
 	*/
	public static String stringCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(int i = 0; i<CommandList.length;i++)
    	{list += CommandList[i][0]+" "+CommandList[i][1];}
    	return list.replaceAll(" /", ", /");
    }
	/**
 	*	@return all commands of the combat phase	
 	*/
	public static String stringCombatCommandList()
    {
    	String list ="~~~~commands~~~~\n";
    	for(int i = 0; i<AttackCommand.length;i++)
    	{list += AttackCommand[i][0]+" "+AttackCommand[i][1];}
    	return list.replaceAll(" /", ", /");
    }
    /**
 	* calculate the current weight of the player
 	*/
    public void calculateCurrentWeight()
    {
        this.HeroCurrentWeight = 0;
        for(Item e : HeroBag)
        {this.HeroCurrentWeight+=e.weight;}
    }
    public Donjon getDonjon()
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

    public int getHeroMaxWeight()
    {return HeroMaxWeight;}

    public int getHeroCurrentWeight()
    {return HeroCurrentWeight;}

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
}