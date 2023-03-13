package jeux_RPG;

public class Rand
{
	/**
 	* generate a random integer between [min(number1, number2), max(number1, number2)[
 	*/
	public static int randint(int number1, int number2)
	{
		return (int) (Math.random()*(Math.max(number1,number2)-Math.min(number1,number2))+Math.min(number1,number2));
	}
	
	/**
 	* generate a random float between [min(number1, number2), max(number1, number2)[
 	*/
	public static float randfloat(float number1, float number2)
	{
		return (float) (Math.random()*(Math.max(number1,number2)-Math.min(number1,number2))+Math.min(number1,number2));
	}
}
