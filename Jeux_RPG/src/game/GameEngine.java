package game;

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
/**
*	Class that handle engine of the game
*/
public class GameEngine
{
    private ArrayList<Item> HeroBag = new ArrayList<Item>();
    private Hero[] HeroTab; 
	public Scanner command = new Scanner(System.in);	
    private Donjon GameDonjon;
    private Room CurrentRoom;
    private int HeroMaxWeight;
    private int HeroCurrentWeight;
    private Stack<Room> LastRoom;
    
    public GameEngine(Hero[] HeroTab, Donjon GameDonjon, Room CurrentRoom, int HeroMaxWeight, int HeroCurrentWeight, Scanner command) 
    {
        this.HeroTab = new Hero[3];
        this.HeroTab = HeroTab;
        this.GameDonjon = GameDonjon;
        this.CurrentRoom = CurrentRoom;
        this.HeroCurrentWeight = HeroCurrentWeight;
        this.HeroMaxWeight = HeroMaxWeight;
        this.HeroBag = new ArrayList<Item>();
        this.LastRoom = new Stack<Room>();
        //this.HeroBag.add(new Item());
        //this.HeroBag.add(new Item());
    }
    /**
 	* Run the game
 	*/
    public void RunGame()
    {
        this.calculateCurrentWeight();
        System.out.println(this.info());
        while(true)
        {
            this.calculateCurrentWeight();
			int resultcommand = Command.RunCommand(this);
        	if(resultcommand==0)
        	{
        		this.command.close();
        		break;
        	}
        	if(resultcommand==-2)
        	{System.out.println(":unknown command !");}
            
            //combat mod
            if(resultcommand==2)
            {
                int resultFightCommand = 0;
                boolean successfulleave = false;
                boolean winoncurrentboss = false;
                while(true)
                {
                    for(Hero currentHero : HeroTab)
                    {
                        if(currentHero==null)
                        {continue;}
                        System.out.println(stringCurrentCombat(currentHero));
                        while(true)
                        {
                            resultFightCommand = Command.RunCombatCommand(this, currentHero);
                            if(resultFightCommand==-2)
        	                {System.out.println(":unknown command !");}
                            if(resultFightCommand==1 || resultFightCommand==2 || resultFightCommand==3)
                            {
                                if(this.CurrentRoom.getRoomBoss().currentHP<=0)
                                {
                                    winoncurrentboss = true;
                                    this.CurrentRoom.killRoomBoss();
                                    System.out.println("win on the current boss !\n\n"+this.info());
                                    //
                                    //drop
                                    //
                                }
                                break;
                            }
                            if(resultFightCommand==4)
                            {
                                System.out.println(":attempting to leave the combat");
                                if(Rand.randint(1, 3)==1)
                                {
                                    successfulleave=true;
                                    this.CurrentRoom.getRoomBoss().currentHP = this.CurrentRoom.getRoomBoss().maxHP;
                                    System.out.println(":successfull to leave");
                                    System.out.println(this.info());
                                }
                                else
                                {
                                    successfulleave=false;
                                    System.out.println(":fail to leave");
                                }
                                break;
                            }
                        }
                        //
                        //boss attack here
                        //

                        //test if dead hero work
                        //if(HeroTab[1]!=null)
                        //{HeroTab[1].currentHP=0;}

                        //check if an hero is dead
                        for(int i=0; i<HeroTab.length; i++)
                        {
                            if(HeroTab[i]==null)
                            {continue;}
                            if(HeroTab[i].currentHP<=0)
                            {HeroTab[i]=null;}
                        }
                        if((resultFightCommand==4 && successfulleave) || winoncurrentboss)
                        {break;}
                    }
                    if((resultFightCommand==4 && successfulleave) || winoncurrentboss)
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
            }
        }
    }
    public void hurtBoss(int damage)
    {this.CurrentRoom.getRoomBoss().hurtBoss(damage);}
    /**
 	* Return info of the current room of the player and the commands
 	*/
	public String info()
    {return "\n" + this.stringCurrentSituation() + "\n" + Command.stringCommandList() + "\n";}
    /**
 	* Return info of the current room of the player
 	*/
    public String stringCurrentSituation()
    {
    	return
    			("~~current room~~\n"+
				this.CurrentRoom+
    			"\n~~~~~~exit~~~~~~\n"+
    			this.CurrentRoom.stringExit()+
    			"\n~~~~~~boss~~~~~~\n"+
    			this.CurrentRoom.getRoomBoss()+
    			"\n~~~~~heroes~~~~~\n"+
    			this.stringHeroList()+
                "\n~~~~~~bag~~~~~~~\n"+
                this.stringBag())
    			.replaceAll("null", "none");
    }
    /**
 	* Return info of the current combat of the player
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
            this.stringBag()+"\n"+
            Command.stringCombatCommandList()+"\n";
    }
    /**
 	* Return the alive hero list
 	*/
    public String stringHeroList()
    {
    	String herolist = "";
    	for(Hero hero : HeroTab)
    	{
            if(hero!=null)
            {herolist += hero + " \n";}
        }
    	return herolist.substring(0,herolist.length()-1);
    }
    /**
 	* Return all item of the bag
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

}