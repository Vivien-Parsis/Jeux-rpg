package game;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Save {
    public static void CreateSave(GameEngine myGame, int SaveNumber)
    {
        try {
            PrintWriter savefile = new PrintWriter("Java_RPG/src/game/save/save"+SaveNumber+".txt"); 
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
            }
            else
            {SaveContent+="empty";}
            SaveContent+="\n#Gold\n"+myGame.getGold()+"\n#dungeon\n";
            for(String key : myGame.getDonjon().getRoomHash().keySet())
            {
                SaveContent+=key+"|"+myGame.getDonjon().getRoomHash().get(key).save()+"\n";
            }  
            SaveContent+="#current_room\n"+myGame.getCurrentRoom().getRoomName()+"\n";
            savefile.print(SaveContent);
            savefile.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("!error when writing to the file");
        }
    }
}
