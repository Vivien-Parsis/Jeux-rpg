package game;

import java.util.HashMap;

/**
* Implement multiple lists of commands for the game
* @author VivienP
*/
public class CommandList {
    /**
     *	List of all commands in explore phase
     */
	static protected HashMap<String, String[]> commandHash = new HashMap<String, String[]>();
	/**
     *	List of all commands in combat phase
     */
	static protected HashMap<String, String[]> AttackcommandHash = new HashMap<String, String[]>();
	
	static
	{
		commandHash.put("/quit",  new String[]{"", "quit the game"});
		commandHash.put("/attack",  new String[]{"", "launch combat on current boss"});
		commandHash.put("/move",  new String[]{"{direction} ", "move the player into a direction"});
		commandHash.put("/info",  new String[]{"{hero/boss/item/none} ","display current info about the current boss, a hero, a item or current situation"});
		commandHash.put("/help",  new String[]{"{command} ", "display how work a command"});
		commandHash.put("/back",  new String[]{"", "go to the previous visited room"});
		commandHash.put("/equip", new String[]{"{weapon} {hero} ", "equip choosen weapon on a hero"});
		commandHash.put("/buy",   new String[]{"{item} ", "buy a item from a merchant"});
		commandHash.put("/sell",  new String[]{"{item} ", "sell a item to a merchant"});

		AttackcommandHash.put("/help",   new String[]{"{command} ", "display how work a command"});
		AttackcommandHash.put("/spell",  new String[]{"{spell} ", "use one spell of your current hero"});
		AttackcommandHash.put("/weapon", new String[]{"", "attack with weapon of your current hero"});
		AttackcommandHash.put("/leave",  new String[]{"", "leave combat"});
		AttackcommandHash.put("/info",   new String[]{"{hero/boss/item/none} ","display current info about the current boss, a hero or a item"});
	}

	/**
	 * check is a known command for explore phase
	 */
	public static boolean knownCommand(String commandtocheck)
	{
		boolean check = false;
		for(String command : commandHash.keySet())
		{
			if(command.equals(commandtocheck))
			{check = true;break;}
		}
		return check;
	}
	/**
	 * check is a known command for combat phase
	 */
	public static boolean knownAttackCommand(String commandtocheck)
	{
		boolean check = false;
		for(String command : AttackcommandHash.keySet())
		{
			if(command.equals(commandtocheck))
			{check = true;break;}
		}
		return check;
	}
}