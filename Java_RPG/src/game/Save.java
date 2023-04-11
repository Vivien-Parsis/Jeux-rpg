package game;

import java.nio.file.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * create and read game savefiles
 */
public abstract class Save {
    /**
     * create a save from a game
     * @param myGame 
     * @param SaveNumber the choosen number of one file
     */
    public static void CreateSave(GameEngine myGame, int SaveNumber)
    {
        try {
            PrintWriter savefile = new PrintWriter("./game/save/save"+SaveNumber+".txt"); 
            String SaveContent = "";
            //hero
            for(int i = 0; i<myGame.getHeroTab().length;i++)
            {
                SaveContent+="Hero"+i+"#";
                if(myGame.getHeroTab()[i]==null)
                {SaveContent+="null\n";}
                else
                {SaveContent+=myGame.getHeroTab()[i].save()+"\n";}
            }
            //bag
            SaveContent+="bag_hero#";
            if(myGame.getHeroBag().size()>0)
            {
                for(int i = 0; i<myGame.getHeroBag().size();i++)
                {SaveContent+=myGame.getHeroBag().get(i).save()+"&";}
                SaveContent=SaveContent.substring(0,SaveContent.length()-1);
            }
            else
            {SaveContent+="empty";}
            SaveContent+="\nGold#"+myGame.getGold()+"\n";
            //list of room
            for(String key : myGame.getDungeon().getRoomHash().keySet())
            {SaveContent+="room_dungeon#"+key+"&"+myGame.getDungeon().getRoomHash().get(key).save()+"\n";}  
            //final key
            SaveContent+="final_key#";
            if(myGame.getDungeon().getFinalKey()==null)
            {SaveContent+="null\n";}
            else
            {SaveContent+=myGame.getDungeon().getFinalKey().save()+"\n";}
            //current room
            SaveContent+="current_room#"+myGame.getDungeon().getKeyofRoomHash(myGame.getCurrentRoom())+"\nLastRoom#";
            //last room(s)
            if(myGame.getLastRoom().size()>0)
            {
                for(int i=0; i<myGame.getLastRoom().size();i++)
                {
                    if(i==myGame.getLastRoom().size()-1)
                    {SaveContent+=myGame.getLastRoom().get(i);}
                    else
                    {SaveContent+=myGame.getLastRoom().get(i)+"&";}
                }
                
            }
            else
            {SaveContent+="Empty\n";}
            
            System.out.println(":successfully save the game");
            savefile.print(SaveContent);
            savefile.close();
        } catch (IOException e)
        {System.out.println("!error when writing to the file");}
    }
    /**
     * read all lines of one choosen savefile
     * @param saveNumber the choosen number of one savefile
     * @return an arrayList of String which contains all lines of the choosen savefile
     */
    public static ArrayList<String> ReadSave(int saveNumber)
    {
        List<String> SaveData = new ArrayList<String>();
        try
        {SaveData = Files.readAllLines(Paths.get("game/save/save"+saveNumber+".txt"));}
        catch (IOException e)
        {System.out.println("!error when writing to the file");}
        return (ArrayList<String>) SaveData;
    }
}
