package game;
/**
* Implement multiple lists of commands for the game
* @author VivienP
*/
public interface CommandList {
    /**
     *	List of all commands in explore phase
     */
    String[][] CommandList = 
	{
		{"/quit", "", "quit the game"},
		{"/move", "{direction} ", "move the player into a direction"},
		{"/attack","", "launch attack mode on current boss"},
		{"/info","{hero/boss/item/none} ","display current info about the current boss, a hero, a item or current situation"},
		{"/help", "{command} ", "display how work a command"},
		{"/back", "", "go to the previous visited room"},
		{"/equip", "{weapon} {hero} ", "equip choosen weapon on a hero"},
		{"/buy", "{item} ", "buy a item from a merchant"},
		{"/sell", "{item} ", "sell a item to a merchant"}
	};
	/**
     *	List of all commands in combat phase
     */
	String[][] AttackCommand = 
	{
		{"/help", "{command} ", "display how work a command"},
		{"/spell", "{spell} ", "use one spell of your current hero"},
		{"/weapon", "", "attack with weapon of your current hero"},
		{"/leave", "", "leave combat"},
		{"/info","{hero/boss/item/none} ","display current info about the current boss, a hero or a item"}
	};
}