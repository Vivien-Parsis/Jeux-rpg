package jeux_RPG;

public class Rand {
	//generate a random integer between [borne1,borne2[
	public static int randint(int borne1, int borne2)
	{
		return (int) (Math.random()*(Math.max(borne1,borne2)-Math.min(borne1,borne2))+Math.min(borne1,borne2));
	}
	
	//generate a random float between [borne1,borne2[
	public static float randfloat(float borne1, float borne2)
	{
		return (float) (Math.random()*(Math.max(borne1,borne2)-Math.min(borne1,borne2))+Math.min(borne1,borne2));
	}
	
	//generate a random character between [borne1,borne2[
	public static char randchar(char borne1, char borne2)
	{
		return Character.toChars(randint((int)borne1,(int)borne2))[0];
	}
}
