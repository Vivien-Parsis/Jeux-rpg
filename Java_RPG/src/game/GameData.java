package game;
/**
* Interface contain data for the game
* @author VivienP
*/
public interface GameData {
    /**
     *	List of all possible direction
     */
	final static String[] DirectionList = {"north", "south", "east", "west", "up", "down"};
    /**
     *	List of all possible weapon type
     */
    final static String[] ListWeaponType = {"spear", "staff", "sword"};
    /**
	 * List of all possible spell type for the game. <br><br>
	 * heal : heal one choosen hero <br><br>
	 * offensive : deal damage boss, bypass resistance of the boss <br><br>
	 * defensive : give additionnal resistance for all hero <br><br>
	 * stun : stun the boss for certain number of round
	 */
	final static String[] allSpellType = {"heal", "offensive", "defensive", "stun"};
    /**
	 * List of all possible item type for the game. <br><br>
	 * heal_potion : heal one choosen hero <br><br>
	 * mana_potion : regenerate mana of one choosen hero<br><br>
     * damage_potion : damage the boss<br><br>
	 */
    final static String[] ListofItemType = {"heal_potion", "mana_potion", "damage_potion"};
}
