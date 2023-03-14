package jeux_RPG;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{	
		//
		//menu
		//
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
		{
			System.out.println("\n############\nrunning game\n############\n");
			Game game = new Game();
			game.RunGame();
			System.out.println("\n#########\ngame stop\n#########");
		}
		input.close();
	}
}