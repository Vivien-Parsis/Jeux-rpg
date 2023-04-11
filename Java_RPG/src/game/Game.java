package game;
import java.util.Scanner;
/**
* Class that launch the game
* @author VivienP
*/
public abstract class Game {
    /**
    * Launch a game
	* @author VivienP
    */
    public static void Launch()
	{
		//menu
		Scanner input = new Scanner(System.in);
		System.out.println(CommandList.getcommandHash().get("s"));
        String Stringinput;
		System.out.println("Welcome to the java RPG made by @VivienP !");
		while(true)
		{
			System.out.println("~~choose an option~~\n1.Launch a new game\n2.Load a save\n3.quit");
			System.out.print(">");
			Stringinput = input.next();
			if(Stringinput.equals("1"))
			{GameConfig.Run(GameConfig.defaultGameConfig());}
			if(Stringinput.equals("2"))
			{
				int savefile = 0;
				while(true)
				{
					System.out.println("choose a save\n1.Save1\n2.Save2\n3.Save3");
					System.out.print(">");
					Stringinput = input.next();
					if(Stringinput.equals("1") || Stringinput.equals("2") || Stringinput.equals("3"))
					{
						savefile = Integer.parseInt(Stringinput);
						break;
					}
				}
				GameConfig.Run(GameConfig.saveGameConfig(savefile));
			}
			if(Stringinput.equals("3"))
			{break;}
		}
		input.close();
	}
}