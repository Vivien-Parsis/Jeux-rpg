package jeux_RPG;

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
/**
*	Class that handle engine of the game
*/
public class GameEngine
{
    ArrayList<Item> HeroBag = new ArrayList<Item>();
    ArrayList<Hero> HeroList = new ArrayList<Hero>();
	Scanner command = new Scanner(System.in);	
    Donjon GameDonjon;
    Room CurrentRoom;
    int HeroMaxWeight;
    int HeroCurrentWeight;
    Stack<Room> LastRoom;
 
    public GameEngine(ArrayList<Hero> HeroList, Donjon GameDonjon, Room CurrentRoom, int HeroMaxWeight, int HeroCurrentWeight) 
    {
        this.HeroList = HeroList;
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
        this.getCurrentWeight();
        System.out.println(this.info());
        while(true)
        {
            this.getCurrentWeight();
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
                while(true)
                {
                    for(Hero currentHero : HeroList)
                    {
                        System.out.println(stringCurrentCombat(currentHero));
                        while(true)
                        {
                            resultFightCommand = Command.RunCombatCommand(this, currentHero);
                            if(resultFightCommand==-2)
        	                {System.out.println(":unknown command !");}
                            if(resultFightCommand==1 || resultFightCommand==2 || resultFightCommand==3)
                            {break;}
                            if(resultFightCommand==4)
                            {
                                System.out.println(":attempting to leave the combat");
                                if(Rand.randint(1, 3)==1)
                                {
                                    successfulleave=true;
                                    System.out.println(":successfull to leave\n");
                                    System.out.println(this.info());
                                }
                                else
                                {
                                    successfulleave=false;
                                    System.out.println(":fail to leave\n");
                                }
                                break;
                            }
                        }
                        if(resultFightCommand==4 && successfulleave)
                        {break;}
                    }
                    if(resultFightCommand==4 && successfulleave)
                    {break;}
                    for(Hero currentHero : HeroList)
                    {
                        currentHero.currentmana+=currentHero.manaregen;
                        if(currentHero.currentmana>currentHero.maxmana)
                        {currentHero.currentmana=currentHero.maxmana;}
                    }
                }
            }
        }
    }
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
    			this.CurrentRoom.RoomBoss+
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
            this.CurrentRoom.RoomBoss+
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
    	for(Hero hero : HeroList)
    	{herolist += hero + " \n";}
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
     public void getCurrentWeight()
     {
        this.HeroCurrentWeight = 0;
        for(Item e : HeroBag)
        {this.HeroCurrentWeight+=e.weight;}
     }
}