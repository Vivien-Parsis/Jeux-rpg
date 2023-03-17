package game;
import java.util.Scanner;

public class Game {
    /**
    * Launch a game
    */
    public static void LaunchGame()
	{	
		//menu
		boolean Start = false;
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the java RPG made by @VivienP !\nStart the game ? (y/n)");
		while(true)
		{
			System.out.print(">");
			String Stringinput = input.next();
			if(Stringinput.equals("y"))
			{Start = true;}
			if(Stringinput.equals("y") || (Stringinput.equals("n")))
			{break;}
			System.out.println("");
		}
		if(Start)
		{GameConfig.RunGame();}
		input.close();
	}
}
