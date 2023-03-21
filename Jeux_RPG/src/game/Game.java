package game;
import java.util.Scanner;
/**
* Class that launch the game
* @author VivienP
*/
public class Game {
    /**
    * Launch a game
    */
    public static void Launch()
	{	
		//menu
		boolean Start = false;
		Scanner input = new Scanner(System.in);
        String Stringinput;
		System.out.println("Welcome to the java RPG made by @VivienP !\nStart the game ? (y/n)");
		while(true)
		{
			System.out.print(">");
			Stringinput = input.next();
			if(Stringinput.equals("y"))
			{Start = true;}
            if(Stringinput.equals("n"))
			{Start = false;}
			if(Stringinput.equals("y") || (Stringinput.equals("n")))
			{break;}
			System.out.println("");
		}
		if(Start)
		{
            GameConfig.Run();
            while(true)
            {
                if(!Stringinput.equals("y")||!Stringinput.equals("n"))
                {System.out.println("Restart the game ? (y/n)");}
                System.out.print(">");
                Stringinput = input.next();
                if(Stringinput.equals("y"))
			    {GameConfig.Run();}
                if(Stringinput.equals("n"))
			    {break;} 
            }
        }
		input.close();
	}
}