package game;

import java.nio.file.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Save {
    public static void CreateSave(GameEngine myGame, int SaveNumber)
    {
        try {
            PrintWriter savefile = new PrintWriter("game/save/save"+SaveNumber+".txt"); 
            String SaveContent = "";
            //hero
            for(int i = 0; i<myGame.getHeroTab().length;i++)
            {
                SaveContent+="#Hero"+i+"\n";
                if(myGame.getHeroTab()[i]==null)
                {SaveContent+="null\n";}
                else
                {SaveContent+=myGame.getHeroTab()[i].save()+"\n";}
            }
            //bag
            SaveContent+="#bag\n";
            if(myGame.getHeroBag().size()>0)
            {
                for(int i = 0; i<myGame.getHeroBag().size();i++)
                {SaveContent+=myGame.getHeroBag().get(i).save()+"|";}
                SaveContent=SaveContent.substring(0,SaveContent.length()-1);
            }
            else
            {SaveContent+="empty";}
            SaveContent+="\n#Gold\n"+myGame.getGold()+"\n#dungeon\n";
            //list of room
            for(String key : myGame.getDonjon().getRoomHash().keySet())
            {
                SaveContent+=myGame.getDonjon().getRoomHash().get(key).save()+"\n";
            }  
            SaveContent+="#current_room\n"+myGame.getCurrentRoom().getRoomName()+"\n#LastRoom\n";
            
            if(myGame.getLastRoom().size()>0)
            {
                for(int i=0; i<myGame.getLastRoom().size();i++)
                {
                    SaveContent+=myGame.getLastRoom().get(i).save()+"||";
                }
                SaveContent=SaveContent.substring(0, SaveContent.length()-2);
            }
            else
            {SaveContent+="Empty\n";}
            
            System.out.println(":successfully save the game");
            savefile.print(SaveContent);
            savefile.close();
        } catch (IOException e)
        {
            System.out.println("!error when writing to the file");
        }
    }
    public static ArrayList<String> ReadSave(int saveNumber)
    {
        List<String> SaveData = new ArrayList<String>();
        try{
            SaveData = Files.readAllLines(Paths.get("game/save/save"+saveNumber+".txt"));
        }catch (IOException e)
        {System.out.println("!error when writing to the file");}
        return (ArrayList<String>) SaveData;
    }
}
