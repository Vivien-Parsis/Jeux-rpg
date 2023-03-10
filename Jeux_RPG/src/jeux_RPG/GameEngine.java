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
                System.out.println(":launch combat on current boss\n");
                System.out.println(stringCurrentCombat());
                while(true)
                {
                    int resultFightCommand = Command.RunCombatCommand(this);
                    if(resultFightCommand==-2)
        	        {System.out.println(":unknown command !");}

                    if(resultFightCommand==4)
                    {
                        System.out.println(":leaving combat\n");
                        System.out.println(this.info());
                        break;
                    }
                }
            }
        }
    }

	public String info()
    {return "\n" + this.stringCurrentSituation() + "\n" + Command.stringCommandList() + "\n";}

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
    			this.stringHeroList())
    			.replaceAll("null", "none");
    }
    public String stringCurrentCombat()
    {
        return 
            "\n~~~~~~boss~~~~~~\n"+
            this.CurrentRoom.RoomBoss+
            "\n~~~~~heroes~~~~~\n"+
            this.stringHeroList()+"\n"+
            Command.stringCombatCommandList()+"\n";
    }
    
    public String stringHeroList()
    {
    	String herolist = "";
    	for(Hero i : HeroHash.values())
    	{herolist += i + " ";}
    	return herolist;
    }
}