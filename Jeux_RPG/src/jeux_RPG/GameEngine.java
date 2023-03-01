package jeux_RPG;
import java.util.HashMap;

public class GameEngine {
	HashMap<String, Hero> HeroHash = new HashMap<String, Hero>();
	Donjon GameDonjon;
	Salle CurrentRoom;
	public GameEngine(HashMap<String, Hero> HeroHash, Donjon GameDonjon, Salle CurrentRoom) 
	{
		this.HeroHash = HeroHash;
		this.GameDonjon = GameDonjon;
		this.CurrentRoom = CurrentRoom;
	}
	
	public void RunGame()
	{
		System.out.println("running game");
	}
}
