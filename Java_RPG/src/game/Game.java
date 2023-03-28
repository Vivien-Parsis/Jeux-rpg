package game;
import java.util.Scanner;
/**
* Class that launch the game
* @author VivienP
*/
public class Game {
    /**
    * Launch a game
	* @author VivienP
    */
    public static void Launch()
	{
		//menu
		Scanner input = new Scanner(System.in);
        String Stringinput;
		System.out.println("Welcome to the java RPG made by @VivienP !\nStart the game ? (y/n)");
		//ask if the player want to (re)run the game
		//y for yes and n for no, else unknown and ask again
		while(true)
		{
			System.out.print(">");
			Stringinput = input.next();
			if(Stringinput.equals("y"))
			{GameConfig.Run();System.out.println("\n:Do you want to restart the game ? (y/n)");}
            if(Stringinput.equals("n"))
			{break;}
			if(!Stringinput.equals("n") && !Stringinput.equals("y"))
			{System.out.println("!unknown");}
		}
		input.close();
	}
}