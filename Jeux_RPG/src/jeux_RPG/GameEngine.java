package jeux_RPG;

import java.util.Scanner;
import java.util.HashMap;

public class GameEngine
{
    HashMap<String, Hero> HeroHash = new HashMap<String, Hero>();
    HashMap<String, Item> HeroBag = new HashMap<String, Item>();
	Scanner command = new Scanner(System.in);	
    Donjon GameDonjon;
    Room CurrentRoom;
    int HeroMaxWeight;
    int HeroCurrentWeight;
 
    public GameEngine(HashMap<String, Hero> HeroHash, Donjon GameDonjon, Room CurrentRoom, int HeroMaxWeight, int HeroCurrentWeight) 
    {
        this.HeroHash = HeroHash;
        this.GameDonjon = GameDonjon;
        this.CurrentRoom = CurrentRoom;
        this.HeroCurrentWeight = HeroCurrentWeight;
        this.HeroMaxWeight = HeroMaxWeight;
        this.HeroBag = new HashMap<String, Item>();
    }
    
    public void RunGame()
    {
        System.out.println(this.info());
        while(true)
        {
        	int resultcommand = this.Command();
        	if(resultcommand==0)
        	{
        		System.out.println("\n#########");
        		System.out.println("game stop");
        		System.out.println("#########");
        		this.command.close();
        		break;
        	}
        	if(resultcommand==-2)
        	{System.out.println("unknown command !");}
        }
    }
    
    public String stringCurrentSituation()
    {
    	return
    			("~~current room~~\n"+this.CurrentRoom+
    			"\n~~~~~exits~~~~~\n"+
    			this.CurrentRoom.stringExit()+
    			"\n~~~~~boss~~~~~\n"+
    			this.CurrentRoom.RoomBoss+
    			"\n~~~~~hero~~~~~\n"+
    			this.stringHeroList())
    			.replaceAll("null", "none");
    }
    
    public static String stringCommandList()
    {
    	String list ="~~~commands~~~\n";
    	for(int i = 0; i<Game.CommandList.length;i++)
    	{list += Game.CommandList[i][0]+" "+Game.CommandList[i][1];}
    	return list;
    }
    
    public String info()
    {return "\n" + this.stringCurrentSituation() + "\n" + stringCommandList() + "\n";}
    
    public String stringHeroList()
    {
    	String herolist = "";
    	for(Hero i : HeroHash.values())
    	{herolist += i + " ";}
    	return herolist;
    }
    
    public int Command()
    {
    	System.out.print(">");
    	String stringCommand = this.command.nextLine();
    	String[] tabCommand = stringCommand.split(" ");
        
    	//quit case
    	if(tabCommand[0].equals(Game.CommandList[0][0]))
        {return 0;}
    	
    	//info case
    	if(tabCommand[0].equals(Game.CommandList[3][0]))
        {
    		System.out.println(this.info());
    		return 3;
    	}
    	
        //move case
        if(tabCommand[0].equals(Game.CommandList[1][0]))
        {
        	//case of wrong format
        	if(tabCommand.length!=2)
        	{
        		System.out.println("must be /move {direction} !");
        		return -1;
        	}
        	boolean knowdirection = false;
        	for(int i = 0; i<Game.DirectionList.length;i++)
        	{
        		if(tabCommand[1].equals(Game.DirectionList[i]))
        		{knowdirection = true; break;}
        	}
        	//case of unknown possible direction
        	if(!knowdirection)
        	{
        		System.out.println("no such direction");
        		return -1;
        	}
        	//case of no exit
        	if(!this.CurrentRoom.hasExit(tabCommand[1]))
        	{
        		System.out.println("can't go that way");
        		return -1;
        	}
        	//case of a successful move
        	else
        	{
        		this.CurrentRoom = this.CurrentRoom.getExit(tabCommand[1]);
        		System.out.println();
        		System.out.println(this.info());
        		return 1;
        	}
        }
        //case of unknown command
        return -2;
    }
}