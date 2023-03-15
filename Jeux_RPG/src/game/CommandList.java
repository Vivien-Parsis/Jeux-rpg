package game;

public interface CommandList {
    /**
     *	List of all commands in explore phase
     */
    public final static String[][] CommandList = 
	{{"/quit", "", "quit the game"},
	{"/move", "{direction} ", "move the player into a direction"},
	{"/attack","", "launch attack mode on current boss"},
	{"/info","{hero/boss/item/none} ","display current info about the current boss, a hero, a item or current situation"},
	{"/help", "{command} ", "display how work a command"},
	{"/back", "", "go to the previous visited room"}};
	/**
     *	List of all commands in combat phase
     */
	public final static String[][] AttackCommand = 
	{{"/help", "{command} ", "display how work a command"},
	{"/spell", "{spell} ", "use one spell of your current hero"},
	{"/use", "{item} ", "use one item"},
	{"/weapon", "", "attack with weapon of your current hero"},
	{"/leave", "", "leave combat"},
	{"/info","{hero/boss/item} ","display current info about the current boss, a hero or a item"}};
}
