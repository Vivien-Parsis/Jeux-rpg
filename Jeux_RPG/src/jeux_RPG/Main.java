package jeux_RPG;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("##########\nrunning game\n###########\n");
		
		Game game = new Game();
		game.gameEngine.RunGame();
	}
}